let campoTags;

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
            console.log("Script carregado:", jsFile);
            if (typeof window.iniciarPopupNovaImagem === "function") {
                window.iniciarPopupNovaImagem();
            }
        };
        document.body.appendChild(script);
    }
}


async function iniciarCriacaoPublicacao() {
    campoTags = document.getElementById("campoTags");
    const capaPostagemInput = document.getElementById("capaPostagemInput");
    capaPostagemInput.addEventListener("input", input_capa);
    await carregarHTMLCP("inserirImagem", "/nova_imagem", "popUpNovaImagemStyle.css", "popupNovaImagemLogic.js");

    const botaoImagem = document.getElementById("pic");
    const botaoPublicar = document.getElementById("botaoPublicar")


    pic.addEventListener("click", function () {
        const blur_nova_imagem = document.getElementById("blur_nova_imagem");
        const container_nova_imagem = document.getElementById("container_nova_imagem");
        blur_nova_imagem.style.display = "inline";
        container_nova_imagem.style.display = "flex";
    })

    botaoPublicar.addEventListener("click", function () {
        publicarDocumento();
    })

    campoTags.addEventListener("keydown",function(event) {
        if(event.key === ",") {
            adicionarTag();
        }
    })

}

async function adicionarTag() {    
    let novaTag = campoTags.value
    novaTag = novaTag.substring(0,novaTag.length-2)
    const elementoTag = document.createElement("div");
    elementoTag.setAttribute("class", "tags");
    elementoTag.textContent = novaTag;
    campoTags.appendChild(elementoTag);
}

async function publicarDocumento(finalizada) {
    const textoPublicacao = document.getElementById("textoPublicacao");

    const campoTituloPostagem = document.getElementById("campoTitulo");
    const campoTextoPostagem = textoPublicacao.innerHTML;
    
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
        tipoImagem: prefixo
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
            alert("Postagem publicada com sucesso!")
            window.location.href="http://localhost:8080/"+data.id
        })
}

function input_capa() {
    const entrada = document.getElementById("capaPostagemInput");
    capaPostagemPreview = document.getElementById("capaPostagemPreview");

    const imagemSubmetida = entrada.files[0];

    if (imagemSubmetida && imagemSubmetida.name.endsWith(".png") || imagemSubmetida.name.endsWith(".jpg")) {
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
    createItalic();
})
btnFont_reduce.addEventListener("click", function () {
    createItalic();
})


// Pegar o texto
function getText() {
    return textoPublicacao.innerText;
}

// Pegar o HTML formatado
function getHTML() {
    return textoPublicacao.innerHTML;
}

textoPublicacao.addEventListener("keydown", function (e) {
    if (e.key === "Enter") {
        e.preventDefault();
        document.execCommand("insertHTML", false, "<br><br>");
    }

    if (e.ctrlKey && e.key === "b") {
        createBold();
        e.preventDefault();
    }

    // Ctrl + I → Itálico
    if (e.ctrlKey && e.key === "i") {
        createItalic();
        e.preventDefault();
    }

    // Ctrl + U → Sublinhado
    if (e.ctrlKey && e.key === "u") {
        createUnderline();
        e.preventDefault();
    }

    // Ctrl + S → Salvar (exemplo)
    if (e.ctrlKey && e.key === "s") {
        console.log("Salvando conteúdo:", getHTML());
        e.preventDefault();
    }

    if (e.ctrlKey && e.key === "l") {
        createLink();
        e.preventDefault();
    }
});

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
    console.log(window.getSelection().toString())
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

function aumentarTamanho() {
    const campo = document.querySelector("textoPublicacao");
    const span = document.createElement("span");
    span.id = "tamanho"
    span.textContent = "inserido aqui";
    inserir(span, campo, 10);
}

function inserirElemento(element, parent, position) {
    const range = document.createRange();
    const textNode = parent.firstChild;
    range.setStart(textNode, position);
    range.collapse(true);
    range.insertNode(element);
}