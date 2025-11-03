async function iniciarPublicacao() {
    const capa = document.getElementById("capaPostagem");
    const titulo = document.getElementById("titulo");
    const dadosPublicacao = document.getElementById("dados_publicacao");
    const texto = document.getElementById("textoPublicacao");
    const cComentario = document.getElementById("text-area-comentario-forum");
    const caracteres = document.getElementById("limite-caracteres-criacao-comentario-forum");
    const pComentario = document.getElementById("botao-enviar-comentario-forum");
    const body = document.querySelector("body");
    const footer = document.querySelector("#footer");

    const url = window.location.href;
    const id = url.substring(url.lastIndexOf('/') + 1)
    let com = 0;
    let buscando = false;
    let moderador = false;


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
                moderador = true;
            }

        })
        .catch(err => console.error(err));


    cComentario.addEventListener("keydown", function () {
        setTimeout(function () {
            caracteres.textContent = cComentario.value.length + "/512";
        }, 1)

    })

    pComentario.addEventListener("click", function () {
        if (cComentario.value.length > 512) {
            alert("O seu comentário está grande de mais!")
            return;
        }

        const requestBody = {
            textoComentario: cComentario.value,
            tipo: 'P',
            idComentavel: id,
        };

        fetch("http://localhost:8080/api/com/", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                window.location.reload()
            })
    })

    async function chamarComentarios() {
        const rect = footer.getBoundingClientRect();

        if (rect.top >= 0 && rect.top <= (window.innerHeight || document.documentElement.clientHeight)) {
            if (!buscando) {
                await carregarComentarios()
            }
        }
    }

    body.addEventListener('scroll', chamarComentarios);


    async function carregarComentarios() {
        buscando = true;

        const requestBody = {
            parametro: "dataComentario",
            ascending: false
        };

        fetch("http://localhost:8080/api/com/listar_comentarios/" + id + "/P/" + com, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                const primeiroComentario = document.querySelector('#primeiro_comentario');
                const containerGeral = document.getElementById("container_publicacao");

                if (data.resultado.length === 0) {
                    primeiroComentario.remove()
                    body.removeEventListener('scroll', chamarComentarios);
                } else {
                    if (data.proximosIndexes == 0) {
                        body.removeEventListener('scroll', chamarComentarios);
                    }
                    data.resultado.forEach((post, index) => {
                        if (index == 0) {
                            construirComentario(primeiroComentario, post)
                        } else {
                            const novoComentario = primeiroComentario.cloneNode(true);
                            containerGeral.appendChild(novoComentario)
                            construirComentario(novoComentario, post)
                        }
                    });
                }
                com++;
                buscando = false;
            })
            .catch(err => console.error(err));

        function construirComentario(comentario, dados) {
            imagem = comentario.querySelector(".foto-usuario-comentarios")
            exclusao = comentario.querySelector(".excluirCom")
            comentario.querySelector(".container-texto-comentario").textContent = dados.texto
            comentario.querySelector(".autor").textContent = dados.autor.nome
            comentario.querySelector(".autor").addEventListener("click", function () {
                window.location.href = "http://localhost:8080/usuario/" + dados.autor.id;
            })
            if (dados.autor.foto == null) {
                imagem.src = "/imagens/perfilIconDark.png";
            } else if (dados.autor.foto.imagem == "" || dados.autor.foto.tipoImagem == "") {
                imagem.src = "/imagens/perfilIconDark.png";
            } else {
                imagem.src = "data:image/" + dados.autor.foto.tipoImagem + ";base64," + dados.autor.foto.imagem;
            }
            if (dados.proprio) {
                exclusao.style.backgroundColor = 'darkred'
                exclusao.addEventListener("click", excluirComentario("http://localhost:8080/api/com/excluir_proprio/" + dados.id, true))
            } else if (moderador) {
                exclusao.style.backgroundColor = 'purple'
                exclusao.addEventListener("click", excluirComentario("http://localhost:8080/api/com/excluir/" + dados.id, false))
            } else {
                exclusao.style.display = 'none'
            }
            imagem.addEventListener("click", function () {
                window.location.href = "http://localhost:8080/usuario/" + dados.autor.id;
            })
        }
    }
    async function excluirComentario(url, proprio) {
        if (proprio) {

        }

        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                alert("Comentário excluído com sucesso!")
                window.location.reload();
            })
            .catch(err => console.error(err));
    }

    await carregarComentarios();

}

document.addEventListener("DOMContentLoaded", iniciarPublicacao)