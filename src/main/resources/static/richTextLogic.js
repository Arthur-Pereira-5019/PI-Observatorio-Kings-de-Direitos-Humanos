let selecaoAntiga = null;
const TAGS_REMOVIVEIS = [
    "rt_titulo",
    "rt_citacao"
];

async function iniciarRichText() {
    const textoPublicacao = document.getElementById("textoPublicacao");
    const btnLink = document.getElementById("link");
    const btnBold = document.getElementById("bold");
    const btnUnderlined = document.getElementById("underlined");
    const btnItalic = document.getElementById("italic");
    const btnFont_increase = document.getElementById("font_increase");
    const btnFont_reduce = document.getElementById("font_reduce");
    const pic = document.getElementById("pic");

    pic.addEventListener("click", function () {
        const blur_nova_imagem = document.getElementById("blur_nova_imagem");
        const container_nova_imagem = document.getElementById("container_nova_imagem");
        blur_nova_imagem.style.display = "inline";
        container_nova_imagem.style.display = "flex";
    });

    btnLink.addEventListener("click", function () {
        createLink();
    });
    btnBold.addEventListener("click", function () {
        createBold();
    });
    btnUnderlined.addEventListener("click", function () {
        createUnderline();
    });
    btnItalic.addEventListener("click", function () {
        createItalic();
    });
    btnFont_increase.addEventListener("click", function () {
        titulo();
    });
    btnFont_reduce.addEventListener("click", function () {
        citacao();
    });

    // Pegar o texto
    function getText() {
        return textoPublicacao.innerText;
    }

    // Pegar o HTML formatado
    function getHTML() {
        return textoPublicacao.innerHTML;
    }

    //textoPublicacao.addEventListener("keyup", salvarSelecao);
    //textoPublicacao.addEventListener("mouseup", salvarSelecao);
    //textoPublicacao.addEventListener("focus", salvarSelecao);

    textoPublicacao.addEventListener("keydown", function (e) {
        selecaoAntiga = window.getSelection();

        if (e.key === "Enter") {
            e.preventDefault();
            const selection = window.getSelection();
            const range = selection.getRangeAt(0);
            const br = document.createElement("br");
            range.insertNode(br);
            range.setStartAfter(br);
            range.collapse(true);
            selection.removeAllRanges();
            selection.addRange(range);
        }

        if (e.ctrlKey && e.key === "b") {
            createBold();
            e.preventDefault();
        }

        if (e.ctrlKey && e.key === "i") {
            createItalic();
            e.preventDefault();
        }

        if (e.ctrlKey && e.key === "u") {
            createUnderline();
            e.preventDefault();
        }

        if (e.ctrlKey && e.key === "s") {
            e.preventDefault();
        }

        if (e.ctrlKey && e.key === "l") {
            createLink();
            e.preventDefault();
        }
    });

    textoPublicacao.addEventListener("dragover", e => e.preventDefault());
    textoPublicacao.addEventListener("drop", e => e.preventDefault());

    textoPublicacao.addEventListener("paste", function (e) {
        e.preventDefault();
        const text = (e.clipboardData || window.clipboardData).getData("text/plain");
        const selection = window.getSelection();
        if (!selection.rangeCount) return;
        selection.deleteFromDocument();
        selection.getRangeAt(0).insertNode(document.createTextNode(text));
        selection.collapseToEnd();
    });

    function createLink() {
        document.execCommand("createLink", true, window.getSelection().toString());
    }

    function createItalic() {
        document.execCommand("italic");
    }

    function createBold() {
        document.execCommand("bold");
    }

    function createUnderline() {
        document.execCommand("underline");
    }

    function backspace() { }
}

function titulo() {
    const span = document.createElement("span");
    span.classList.add("rt_titulo");
    span.textContent = "";
    inserirElemento(span, window.getSelection());
}

function citacao() {
    const span = document.createElement("span");
    span.classList.add("rt_citacao");
    span.textContent = "";
    inserirElemento(span, window.getSelection());
}

function getOffsetInNode(range, ancestor) {
    const preRange = document.createRange();
    preRange.selectNodeContents(ancestor);
    preRange.setEnd(range.startContainer, range.startOffset);
    return preRange.toString().length;
}

function limparSpansRedundantes(editor) {
    const spans = [...editor.querySelectorAll("span")];
    for (let i = 0; i < spans.length; i++) {
        const el = spans[i];
        if (!el.textContent.trim() && !el.querySelector("*")) {
            el.remove();
            continue;
        }
        const next = el.nextSibling;
        if (
            next &&
            next.nodeType === Node.ELEMENT_NODE &&
            next.tagName === "SPAN" &&
            next.className === el.className
        ) {
            el.textContent += next.textContent;
            next.remove();
            i--;
        }
        /*Solução no freezer para quebra de texto desfuncional.
        if (next) {
            let node = el.nextSibling;
            let hasBR = false;
            while (node && node.nodeType !== Node.ELEMENT_NODE) {
                node = node.nextSibling;
            }
            if (node && node.tagName === "BR") hasBR = true;

            if (
                !hasBR &&
                node &&
                node.nodeType === Node.ELEMENT_NODE &&
                node.tagName === "SPAN" &&
                node.className === el.className
            ) {
                el.textContent += node.textContent;
                node.remove();
                i--;
            }
        }*/
    }
}

function inserirElemento(elemento, selection) {

    if (!selection.rangeCount) return;
    const range = selection.getRangeAt(0);
    const currentTag = testarTag(selection?.anchorNode);
    const editor = document.querySelector("#textoPublicacao");

    if (currentTag && currentTag.classList.contains(elemento.classList[0]) && range.collapsed) {
        // --- detecção robusta de "caret no começo" e "caret no fim" ---
        const isAtStart = (() => {
    // caret antes de qualquer texto real?
    if (range.startContainer.nodeType === Node.TEXT_NODE) {
        return range.startOffset === 0 && range.startContainer.previousSibling == null;
    }
    return (
        (range.startContainer === currentTag && range.startOffset === 0) ||
        (range.startContainer === currentTag.firstChild && range.startOffset === 0)
    );
})();

const isAtEnd = (() => {
    if (range.startContainer.nodeType === Node.TEXT_NODE) {
        return (
            range.startOffset === range.startContainer.length &&
            range.startContainer.nextSibling == null
        );
    }
    return (
        (range.startContainer === currentTag &&
            range.startOffset === currentTag.childNodes.length) ||
        (range.startContainer === currentTag.lastChild &&
            range.startOffset === currentTag.lastChild.textContent.length)
    );
})();

    console.log("inserirElemento: currentTag", currentTag && currentTag.className, "startContainer:", range.startContainer, "startOffset:", range.startOffset, "isAtStart:", isAtStart, "isAtEnd:", isAtEnd);

        // Se caret efetivamente no início ou no fim: criar bloco neutro logo após a tag
        if (isAtStart || isAtEnd) {
            const novo = document.createElement("span");
            novo.textContent = "\u200B";
            novo.classList.add("rt_default", "rt_geral");

            currentTag.insertAdjacentElement("afterend", novo);
            const nr = document.createRange();
            nr.selectNodeContents(novo);
            nr.collapse(true);
            selection.removeAllRanges();
            selection.addRange(nr);
            return;
        }

        // --- Caret no MEIO: split preservando nós (ex: <br>) ---
        // Construir fragmentos preservando os nós DOM usando cloneContents()
        const preRange = document.createRange();
        preRange.selectNodeContents(currentTag);
        preRange.setEnd(range.startContainer, range.startOffset);
        const fragBefore = preRange.cloneContents();

        const postRange = document.createRange();
        postRange.selectNodeContents(currentTag);
        postRange.setStart(range.startContainer, range.startOffset);
        const fragAfter = postRange.cloneContents();

        const spanLeft = currentTag.cloneNode(false);
        const spanMid = document.createElement("span");
        const spanRight = currentTag.cloneNode(false);

        spanLeft.className = currentTag.className;
        spanRight.className = currentTag.className;
        spanMid.className = "rt_default rt_geral";

        if (fragBefore && fragBefore.childNodes.length) {
            spanLeft.appendChild(fragBefore);
        } else {
            spanLeft.textContent = "\u200B";
        }

        // middle é apenas um placeholder vazio para caret
        spanMid.textContent = "\u200B";

        if (fragAfter && fragAfter.childNodes.length) {
            spanRight.appendChild(fragAfter);
        } else {
            spanRight.textContent = "\u200B";
        }

        currentTag.replaceWith(spanLeft, spanMid, spanRight);

        const nr = document.createRange();
        nr.selectNodeContents(spanMid);
        nr.collapse(true);
        selection.removeAllRanges();
        selection.addRange(nr);

        limparSpansRedundantes(editor);
        return;
    }



    if (!range.collapsed) {
        if (currentTag) joinDePartes(currentTag, range, "rt_default rt_geral");
        try {
            range.surroundContents(elemento);
        } catch (e) { }

        elemento.classList.add("rt_geral");
        range.setStartAfter(elemento);
        range.collapse(true);
        selection.removeAllRanges();
        selection.addRange(range);

        limparSpansRedundantes(editor);
        return;
    }

    const container = range.startContainer;

    if (container.nodeType === Node.TEXT_NODE) {
        const text = container.textContent;
        const before = text.slice(0, range.startOffset);
        const after = text.slice(range.startOffset);

        const beforeNode = before ? document.createTextNode(before) : null;
        const afterNode = after ? document.createTextNode(after) : null;
        const parent = container.parentNode;

        if (beforeNode) parent.insertBefore(beforeNode, container);
        parent.insertBefore(elemento, container);
        if (afterNode) parent.insertBefore(afterNode, container);
        parent.removeChild(container);
    } else {
        range.insertNode(elemento);
    }

    if (!elemento.textContent) elemento.textContent = "\u200B";
    elemento.classList.add("rt_default", "rt_geral");

    if (typeof insertSeparator === "function") {
        insertSeparator(elemento, "before");
        insertSeparator(elemento, "after");
    }

    const newRange = document.createRange();
    newRange.selectNodeContents(elemento);
    newRange.collapse(true);
    selection.removeAllRanges();
    selection.addRange(newRange);

    limparSpansRedundantes(editor);
}

function colocarCaretDentro(el, selection) {
    const r = document.createRange();
    r.selectNodeContents(el);
    r.collapse(true);
    selection.removeAllRanges();
    selection.addRange(r);
}

function joinDePartes(ancestor, range, classMeio) {
    const preRange = document.createRange();
    preRange.selectNodeContents(ancestor);
    preRange.setEnd(range.startContainer, range.startOffset);
    const fragBefore = preRange.cloneContents();

    const midRange = document.createRange();
    midRange.setStart(range.startContainer, range.startOffset);
    midRange.setEnd(range.endContainer, range.endOffset);
    const fragMid = midRange.cloneContents();

    const postRange = document.createRange();
    postRange.selectNodeContents(ancestor);
    postRange.setStart(range.endContainer, range.endOffset);
    const fragAfter = postRange.cloneContents();

    const before = ancestor.cloneNode(false);
    const after = ancestor.cloneNode(false);
    const elemento = document.createElement("span");

    before.className = ancestor.className;
    after.className = ancestor.className;
    elemento.className = classMeio;

    if (fragBefore && fragBefore.childNodes.length) {
        before.appendChild(fragBefore);
    } else {
        before.textContent = "\u200B";
    }

    if (fragMid && fragMid.childNodes.length) {
        elemento.appendChild(fragMid);
    } else {
        elemento.textContent = "\u200B";
    }

    if (fragAfter && fragAfter.childNodes.length) {
        after.appendChild(fragAfter);
    } else {
        after.textContent = "\u200B";
    }

    ancestor.parentNode.insertBefore(after, ancestor.nextSibling);
    ancestor.parentNode.insertBefore(elemento, after);
    ancestor.parentNode.insertBefore(before, elemento);
    ancestor.remove();

    // manter separadores (se usar)
    insertSeparator(elemento, "before");
    insertSeparator(elemento, "after");
}


function testarTag(container) {
    let el = container.nodeType === Node.TEXT_NODE
        ? container.parentElement
        : container;
    while (el && el !== document) {
        const match = TAGS_REMOVIVEIS.find(cls => el.classList.contains(cls));
        if (match) return el;
        el = el.parentElement;
    }
    return null;
}

function insertSeparator(ref, pos) {
    const sep = document.createElement("span");
    sep.classList.add("rt_separador");
    sep.classList.add("rt_geral");
    sep.contentEditable = "false";
    ref.insertAdjacentElement(pos === "before" ? "beforebegin" : "afterend", sep);
}
