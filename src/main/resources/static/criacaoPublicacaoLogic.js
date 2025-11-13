let campoTags;
let selecaoAntiga = null;
const TAGS_REMOVIVEIS = [
    "rt_titulo",
    "rt_citacao"
];

async function carregarHTMLCP(id, url, cssFile, jsFile) {
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

function salvarSelecao() {
  const sel = window.getSelection();
  if (sel && sel.rangeCount > 0) {
    selecaoAntiga = sel.getRangeAt(0).cloneRange();
  }
}

async function iniciarCriacaoPublicacao() {
    campoTags = document.getElementById("campoTags");
    const capaPostagemInput = document.getElementById("capaPostagemInput");
    capaPostagemInput.addEventListener("input", input_capa);
    await carregarHTMLCP("inserirImagem", "/nova_imagem", "popUpNovaImagemStyle.css", "popupNovaImagemLogic.js");

    const botaoPublicar = document.getElementById("botaoPublicar")


    pic.addEventListener("click", function () {
        //salvarSelecao()
        const blur_nova_imagem = document.getElementById("blur_nova_imagem");
        const container_nova_imagem = document.getElementById("container_nova_imagem");
        blur_nova_imagem.style.display = "inline";
        container_nova_imagem.style.display = "flex";
    })

    botaoPublicar.addEventListener("click", function () {
        publicarDocumento();
    })

    campoTags.addEventListener("keydown", function (event) {
        if (event.key === ",") {
            adicionarTag();
        }
    })

}

async function adicionarTag() {
    let novaTag = campoTags.value
    novaTag = novaTag.substring(0, novaTag.length - 2)
    const elementoTag = document.createElement("div");
    elementoTag.setAttribute("class", "tags");
    elementoTag.textContent = novaTag;
    campoTags.appendChild(elementoTag);
}

async function publicarDocumento(finalizada) {
    const textoPublicacao = document.getElementById("textoPublicacao");
    const imagens = textoPublicacao.querySelectorAll("img")

    imagens.forEach((i) => {
        i.src = "";
    })

    const campoTextoPostagem = textoPublicacao.innerHTML;
    const campoTituloPostagem = document.getElementById("campoTitulo");


    let canva = document.getElementById("capaPostagemPreview");
    let campoImagem = canva.src;
    let endPosition = campoImagem.indexOf(",");
    let fimPrefixo = campoImagem.indexOf(";")
    let inicioPrefixo = campoImagem.indexOf("/")
    prefixo = campoImagem.substring(inicioPrefixo, fimPrefixo);

    endPosition++;
    campoImagem = campoImagem.replace(campoImagem.substring(0, endPosition), "");

    const requestBody = {
        capaBase64: campoImagem,
        tituloPostagem: campoTituloPostagem.value,
        textoPostagem: campoTextoPostagem,
        tags: campoTags.value,
        publicada: finalizada,
    };

    fetch("http://localhost:8080/api/postagem", {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestBody)
    })
        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })
        .then(data => {
            console.log(data);
            alert("Postagem publicada com sucesso!")

            window.location.href = "http://localhost:8080/publicacao/" + data.id
        })
}

function input_capa() {
    const entrada = document.getElementById("capaPostagemInput");
    capaPostagemPreview = document.getElementById("capaPostagemPreview");

    const imagemSubmetida = entrada.files[0];

    if (imagemSubmetida && imagemSubmetida.name.endsWith(".png") || imagemSubmetida.name.endsWith(".jpg") || imagemSubmetida.name.endsWith(".jpeg") || imagemSubmetida.name.endsWith(".webp")) {
        const reader = new FileReader();

        reader.onload = (e) => {
            const base64StringWithPrefix = e.target.result;
            capaPostagemPreview.src = base64StringWithPrefix;
        };

        reader.readAsDataURL(imagemSubmetida);
    }
}

document.addEventListener("DOMContentLoaded", iniciarCriacaoPublicacao);

const textoPublicacao = document.getElementById("textoPublicacao");

const btnLink = document.getElementById("link");
const btnBold = document.getElementById("bold");
const btnUnderlined = document.getElementById("underlined");
const btnItalic = document.getElementById("italic");
const btnFont_increase = document.getElementById("font_increase");
const btnFont_reduce = document.getElementById("font_reduce");


btnLink.addEventListener("click", function () {
    createLink();
})
btnBold.addEventListener("click", function () {
    createBold();
})
btnUnderlined.addEventListener("click", function () {
    createUnderline();
})
btnItalic.addEventListener("click", function () {
    createItalic();
})
btnFont_increase.addEventListener("click", function () {
    titulo();
})
btnFont_reduce.addEventListener("click", function () {
    citacao();
})


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
    selecaoAntiga = window.getSelection()

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
    document.execCommand("createLink", true, window.getSelection().toString())
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

function backspace() {

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

        // Remove spans totalmente vazios (sem texto e sem filhos)
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

function inserirElemento(elemento, selection) {
    if (!selection.rangeCount) return;
    const range = selection.getRangeAt(0);
    const currentTag = testarTag(selection?.anchorNode);
    const editor = document.querySelector("#textoPublicacao");

    
    if (currentTag && currentTag.classList.contains(elemento.classList[0]) && range.collapsed) {
        const spanLeft = currentTag.cloneNode(false);
        const spanRight = currentTag.cloneNode(false);
        const spanMid = document.createElement("span");
        spanMid.className = "rt_default rt_geral";

        const text = currentTag.textContent || "";
        const offset = getOffsetInNode(range, currentTag);
        const leftText = text.slice(0, offset);
        const rightText = text.slice(offset);

        spanLeft.textContent = leftText || "\u200B";
        spanMid.textContent = "\u200B";
        spanRight.textContent = rightText || "\u200B";

        currentTag.replaceWith(spanLeft, spanMid, spanRight);

        const nr = document.createRange();
        nr.selectNodeContents(spanMid);
        nr.collapse(true);
        selection.removeAllRanges();
        selection.addRange(nr);

        limparSpansRedundantes(editor);
        return;
    }

    // === [2] Caso de seleção: aplicar estilo sobre texto ===
    if (!range.collapsed) {
        if (currentTag) joinDePartes(currentTag, range, "rt_default rt_geral");
        try {
            range.surroundContents(elemento);
        } catch (e) { /* ignora fragmentos não contíguos */ }

        elemento.classList.add("rt_geral");
        range.setStartAfter(elemento);
        range.collapse(true);
        selection.removeAllRanges();
        selection.addRange(range);

        limparSpansRedundantes(editor);
        return;
    }

    // === [3] Caso sem seleção: inserir novo span ===
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
    const beforeText = preRange.toString();

    const postRange = document.createRange();
    postRange.selectNodeContents(ancestor);
    postRange.setStart(range.endContainer, range.endOffset);
    const afterText = postRange.toString();

    const before = ancestor.cloneNode(false);
    const after = ancestor.cloneNode(false);
    const elemento = document.createElement("span");

    before.className = ancestor.className;
    after.className = ancestor.className;
    elemento.className = classMeio;

    before.textContent = beforeText;
    after.textContent = afterText;
    elemento.textContent = range.toString() || "\u200B";;

    ancestor.insertAdjacentElement("afterend", after);
    ancestor.insertAdjacentElement("afterend", elemento);
    ancestor.insertAdjacentElement("afterend", before);
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