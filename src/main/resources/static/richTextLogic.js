let selecaoAntiga = null;
const TAGS_REMOVIVEIS = [
    "rt_titulo",
    "rt_citacao"
];

function salvarSelecao() {
    const sel = window.getSelection();
    if (!sel || sel.rangeCount === 0) return;

    selecaoAntiga = sel.getRangeAt(0).cloneRange();
}

function restaurarSelecao() {
    const editor = document.getElementById("textoPublicacao");
    if (!selecaoAntiga) return;

    editor.focus();

    setTimeout(() => {
        const sel = window.getSelection();
        sel.removeAllRanges();
        sel.addRange(selecaoAntiga);
    }, 0);
}

async function carregarHTMLRT(id, url, cssFile, jsFile) {
    const response = await fetch(url);
    const data = await response.text();
    document.getElementById(id).innerHTML = data;


    if (cssFile) {
        let link = document.createElement("link");
        link.rel = "stylesheet";
        link.href = cssFile;
        document.head.appendChild(link);
    }


    if (jsFile) {
        let script = document.createElement("script");
        script.src = jsFile;
        script.onload = () => {
            if (typeof window.iniciarPopupNovaImagem === "function") {
                window.iniciarPopupNovaImagem();
            }
        };
        document.body.appendChild(script);
    }
}

async function iniciarRichText() {
    await carregarHTMLRT("inserirImagem", "/nova_imagem", "/popUpNovaImagemStyle.css", "/popupNovaImagemLogic.js");

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

    btnLink.addEventListener("mousedown", function () {
        createLink();
    });
    btnBold.addEventListener("mousedown", function () {
        createBold();
    });
    btnUnderlined.addEventListener("mousedown", function () {
        createUnderline();
    });
    btnItalic.addEventListener("mousedown", function () {
        createItalic();
    });
    btnFont_increase.addEventListener("click", function () {
        titulo();
    });
    btnFont_reduce.addEventListener("click", function () {
        citacao();
    });

    textoPublicacao.addEventListener("keyup", salvarSelecao);
    textoPublicacao.addEventListener("mouseup", salvarSelecao);
    textoPublicacao.addEventListener("mouseout", salvarSelecao);
    textoPublicacao.addEventListener("focus", salvarSelecao);
    textoPublicacao.addEventListener("input", salvarSelecao);

    textoPublicacao.addEventListener("keydown", function (e) {
        salvarSelecao()

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
        if (selecaoAntiga) restaurarSelecao();
        setTimeout(() => document.execCommand("italic"), 1);
    }

    function createBold() {
        if (selecaoAntiga) restaurarSelecao();
        setTimeout(() => document.execCommand("bold"), 1);
    }

    function createUnderline() {
        if (selecaoAntiga) restaurarSelecao();
        setTimeout(() => document.execCommand("underline"), 1);
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
    }
}

function posicaoCaretEmTag(range, tag) {
    const node = range.startContainer;
    const offset = range.startOffset;

    if (node.nodeType === Node.TEXT_NODE) {
        const noAnterior = node.previousSibling == null;
        const noSeguinte = node.nextSibling == null;
        return {
            isAtStart: offset === 0 && noAnterior,
            isAtEnd: offset === node.length && noSeguinte
        };
    }

    return {
        isAtStart: node === tag && offset === 0,
        isAtEnd: node === tag && offset === tag.childNodes.length
    };
}

function aplicarRange(novoRange, selection) {
    selection.removeAllRanges();
    selection.addRange(novoRange);
}

function splitTagNoCaret(tag, range) {
    const pre = document.createRange();
    pre.selectNodeContents(tag);
    pre.setEnd(range.startContainer, range.startOffset);

    const post = document.createRange();
    post.selectNodeContents(tag);
    post.setStart(range.startContainer, range.startOffset);

    const left = tag.cloneNode(false);
    const right = tag.cloneNode(false);
    const middle = document.createElement("span");

    left.className = right.className = tag.className;
    middle.className = "rt_default rt_geral";

    const fragBefore = pre.cloneContents();
    const fragAfter = post.cloneContents();

    if (fragBefore && fragBefore.childNodes.length) left.appendChild(fragBefore);
    else left.textContent = "\u200B";

    if (fragAfter && fragAfter.childNodes.length) right.appendChild(fragAfter);
    else right.textContent = "\u200B";

    middle.textContent = "\u200B";

    tag.replaceWith(left, middle, right);
    return middle;
}

function inserirElemento(elemento, selection) {
    if (!selection.rangeCount) return;

    const range = selection.getRangeAt(0);
    const currentTag = testarTag(selection?.anchorNode);
    const editor = document.querySelector("#textoPublicacao");

    if (currentTag && currentTag.classList.contains(elemento.classList[0]) && range.collapsed) {
        const { isAtStart, isAtEnd } = posicaoCaretEmTag(range, currentTag);

        if (isAtEnd) {
            const next = currentTag.nextSibling;
            const precisaNovo =
                !next ||
                (next.nodeType === Node.ELEMENT_NODE &&
                    next.classList.contains("rt_default") &&
                    !next.textContent.trim());

            if (precisaNovo) {
                const novo = document.createElement("span");
                novo.textContent = "\u200B";
                novo.classList.add("rt_default", "rt_geral");

                currentTag.insertAdjacentElement("afterend", novo);
                const nr = document.createRange();
                nr.selectNodeContents(novo);
                nr.collapse(true);
                aplicarRange(nr, selection);

                if (!currentTag.textContent.trim()) currentTag.remove();
                return;
            }
        }

        if (isAtStart || isAtEnd) {
            const novo = document.createElement("span");
            novo.textContent = "\u200B";
            novo.classList.add("rt_default", "rt_geral");

            currentTag.insertAdjacentElement("afterend", novo);
            const nr = document.createRange();
            nr.selectNodeContents(novo);
            nr.collapse(true);
            aplicarRange(nr, selection);

            if (!currentTag.textContent.trim()) currentTag.remove();
            return;
        }
    }

    if (!range.collapsed) {
        if (currentTag) joinDePartes(currentTag, range, "rt_default rt_geral");
        try {
            range.surroundContents(elemento);
        } catch (e) { }

        elemento.classList.add("rt_geral");
        range.setStartAfter(elemento);
        range.collapse(true);
        aplicarRange(range, selection);

        limparSpansRedundantes(editor);
        return;
    }

    if (currentTag) {
        const spanMid = splitTagNoCaret(currentTag, range);
        const nr = document.createRange();
        nr.selectNodeContents(spanMid);
        nr.collapse(true);
        aplicarRange(nr, selection);

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
    aplicarRange(newRange, selection);

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
