async function iniciar() {
    const capa = document.getElementById("capaPostagem");
    const titulo = document.getElementById("titulo");
    const dadosPublicacao = document.getElementById("dados_publicacao");
    const texto = document.getElementById("textoPublicacao");

    const url = window.location.href;
    const id = url.substring(url.lastIndexOf('/') + 1)

    fetch("http://localhost:8080/api/postagem/" + id, {
        headers: { 'Content-Type': 'application/json' }
    })
        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })
        .then(data => {
            titulo.textContent = data.tituloPostagem;
            capa.src = "data:image/" + data.capa.tipoImagem + ";base64," + data.capa.imagem;
            dadosPublicacao.textContent = "Publicado em " + data.dataDaPostagem + " por " + data.autor.nome;
            texto.innerHTML = data.textoPostagem
        })

}

document.addEventListener("DOMContentLoaded", iniciar)