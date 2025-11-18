async function iniciarVerForum() {
    const textoComentario = document.getElementById("text-area-comentario-forum")
    const comentarioLimite = document.getElementById("limite-caracteres-criacao-comentario-forum")
    const btnEnviar = document.getElementById("botao-enviar-comentario-forum")
    const body = document.querySelector("body");
    const footer = document.querySelector("#footer")

    const url = window.location.href;
    const id = url.substring(url.lastIndexOf('/') + 1)
    let com = 0;
    let buscando = false;
    let moderador = false;
    
    await fetch("http://localhost:8080/api/forum/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })

        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })

        .then(data => {
            const fotoPerfil = document.querySelector("#foto-user-discussao-forum")
            document.getElementById("titulo-forum-forum").textContent = data.titulo
            document.title = data.titulo
            document.getElementById("data-comentario-forum").textContent = data.ultimaAtualizacao
            document.getElementById("nome-user-discussao-forum").textContent = data.autor.nome
            document.getElementById("textoForum").textContent = data.textoForum
            document.getElementById("data-comentario-forum").textContent = data.dataCriacao
             if (data.autor.foto == null) {
                fotoPerfil.src = "/imagens/perfilIcon.png";
            } else if (data.autor.foto.fotoPerfil == "" || data.autor.foto.tipoImagem == "") {
                fotoPerfil.src = "/imagens/perfilIcon.png";
            } else {
                fotoPerfil.src = "data:image/" + data.autor.foto.tipoImagem + ";base64," + data.autor.foto.fotoPerfil;
            }

        })

    fetch("http://localhost:8080/api/user", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) {
                carregarComentarios()
                throw new Error("Erro no servidor");

            }
            return res.json();
        })
        .then(data => {
            if (data.estadoDaConta == "MODERADOR" || data.estadoDaConta == "ADMNISTRADOR") {
                botaoOcultar.style.display = "flex";
                moderador = true;
            }
            carregarComentarios();
        })
        .catch(err => console.error(err));


    textoComentario.addEventListener("keydown", function () {
        setTimeout(function () {
            comentarioLimite.textContent = textoComentario.value.length + "/512";
        }, 1)

    })


    btnEnviar.addEventListener("click", function () {
        if (textoComentario.value.length > 512) {
            alert("O seu comentário está grande de mais!")
            return;
        }

        const requestBody = {
            textoComentario: textoComentario.value,
            tipo: 'F',
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

        fetch("http://localhost:8080/api/com/listar_comentarios/" + id + "/F/" + com, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                const primeiroComentario = document.querySelector('.containerPrimeiroComentario');
                const containerGeral = document.getElementById("container-universal-tela-forum");

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
            imagem = comentario.querySelector(".foto-user-discussao-forum")
            exclusao = comentario.querySelector(".botao-ocultar-comentario-forum")
            comentario.querySelector(".textoComentarioForum").textContent = dados.texto
            comentario.querySelector(".nomeComentario").textContent = dados.autor.nome
            comentario.querySelector("#data-comentario-forum").textContent = dados.date
            comentario.querySelector(".nomeComentario").addEventListener("click", function () {
                window.location.pathname = "usuario/" + dados.autor.id;
            })
            if (dados.autor.foto == null) {
                imagem.src = "/imagens/perfilIcon.png";
            } else if (dados.autor.foto.imagem == "" || dados.autor.foto.tipoImagem == "") {
                imagem.src = "/imagens/perfilIcon.png";
            } else {
                imagem.src = "data:image/" + dados.autor.foto.tipoImagem + ";base64," + dados.autor.foto.imagem;
            }
            if (dados.proprio) {
                exclusao.style.backgroundColor = 'darkred'
                exclusao.addEventListener("click", function () {
                    excluirProprioComentario("http://localhost:8080/api/com/excluir_proprio/" + dados.id, "Comentário excluído com sucesso!")
                })
            } else if (moderador) {
                exclusao.style.backgroundColor = 'purple'
                exclusao.addEventListener("click", function () {
                    openCriacaoDecisao("http://localhost:8080/api/com/excluir/" + dados.id)
                })
            } else {
                exclusao.style.display = 'none'
            }
            imagem.addEventListener("click", function () {
                window.location.href = "http://localhost:8080/usuario/" + dados.autor.id;
            })
        }
    }


    async function excluirProprioComentario(url) {
        fetch(url, {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' },
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                alert("Comentário excluído com sucesso!")
                window.location.reload();
            })
            .catch(err => console.error(err));
    }


    async function openCriacaoDecisao(durl, msg) {
        await anexarHTMLExterno("/nova_decisao", "/novaDecisaoModeradoraStyle.css", "/popupNovaDecisaoLogica.js", durl, msg);
    }

}

document.addEventListener("DOMContentLoaded", iniciarVerForum);