async function iniciar() {
    const capa = document.getElementById("capaPostagem");
    const titulo = document.getElementById("titulo");
    const dadosPublicacao = document.getElementById("dados_publicacao");
    const texto = document.getElementById("textoPublicacao");

    const url = window.location.href;
    const id = url.substring(url.lastIndexOf('/') + 1)

    function ocultar() {

    }

    fetch("http://localhost:8080/api/postagem/" + id, {
            headers: { 'Content-Type': 'application/json' },
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

    botaoOcultar = document.getElementById("botao_ocultar")
    fetch("http://localhost:8080/api/user", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })
        .then(data => {
            if (data.estadoDaConta == "MODERADOR" || data.estadoDaConta == "ADMNISTRADOR") {
                botaoOcultar.style.display = "flex";
            }

        })
        .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded", iniciar)