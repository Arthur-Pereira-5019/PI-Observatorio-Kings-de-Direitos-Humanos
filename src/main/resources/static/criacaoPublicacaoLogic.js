let campoTags;

async function iniciarCriacaoPublicacao() {
    campoTags = document.getElementById("campoTags");
    const capaPostagemInput = document.getElementById("capaPostagemInput");
    capaPostagemInput.addEventListener("input", input_capa);


    const botaoPublicar = document.getElementById("botaoPublicar")
    const botaoCancelar = document.getElementById("botaoCancelar")

    botaoPublicar.addEventListener("click", function () {
        publicarDocumento();
    })

    botaoCancelar.addEventListener("click", function() {
        window.location.pathname = 'publicacoes'
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
    let campoTextoPostagem = textoPublicacao.innerHTML;
    let textoPuroPostagem = textoPublicacao.textContent;
    const campoTituloPostagem = document.getElementById("campoTitulo");
    let canva = document.getElementById("capaPostagemPreview");
    let campoImagem = canva.src;

    //Precisa ser antes para não dar flicker nas imagens
    if (textoPuroPostagem.length < 20) {
        alert("Digite o texto da postagem antes de enviá-la.")
        return;
    }


    if (campoTituloPostagem.value.length == 0) {
        alert("Digite o título da postagem antes de enviá-la.")
        return;
    }


    if (campoImagem.length == 0 || campoImagem.includes("/nova_publicacao")) {
        alert("Dê uma capa para a publicação antes de enviá-la.")
        return;
    }


    const imagens = textoPublicacao.querySelectorAll("img")
    imagens.forEach((i) => {
        i.src = "";
    })
    campoImagem = canva.src;
    campoTextoPostagem = textoPublicacao.innerHTML;


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
            alert("Postagem publicada com sucesso!")


            window.location.pathname = "publicacao/" + data.id
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