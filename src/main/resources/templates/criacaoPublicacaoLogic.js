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

}

async function publicarDocumento(finalizada) {
    const textoPublicacao = document.getElementById("textoPublicacao");

    const campoTituloPostagem = document.getElementById("campoTitulo");
    const campoTextoPostagem = textoPublicacao.innerHTML;
    const campoTags = document.getElementById("campoTags");
    let campoImagem = document.getElementById("capaPostagemInput").src;
    let endPosition = campoImagem.indexOf(",");
    endPosition++;

    campoImagem = campoImagem.replace(campoImagem.substring(0, endPosition), "");

    const requestBody = {
        capaBase64: campoImagem,
        tituloPostagem: campoTituloPostagem.value,
        textoPostagem: campoTextoPostagem,
        tags: campoTags.value,
        publicada: finalizada
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


// Pegar o texto
function getText() {
    return textoPublicacao.innerText;
}

// Pegar o HTML formatado
function getHTML() {
    return textoPublicacao.innerHTML;
}

textoPublicacao.addEventListener("keydown", function (e) {
    // Ctrl + B → Negrito
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