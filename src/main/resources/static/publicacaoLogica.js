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


    cComentario.addEventListener("keydown", function () {
        setTimeout(function () {
            caracteres.textContent = cComentario.value.length + 1 + "/512";
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
                console.log(data);

                window.location.reload()
            })
    })

    async function chamarComentarios() {
        const rect = footer.getBoundingClientRect();

        if (rect.top >= 0 && rect.left >= 0 && rect.bottom-100 <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth)) {
            com++;
            await carregarComentarios()
        }
    }

    body.addEventListener('scroll', chamarComentarios);


    async function carregarComentarios() {

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
                console.log(data);

                const primeiroComentario = document.querySelector('#primeiro_comentario');
                const containerGeral = document.getElementById("container_publicacao");
                console.log(data);

                if (data.resultado.length === 0) {
                    primeiroComentario.remove()
                    body.removeEventListener('scroll', chamarComentarios);
                    console.log("Faltam comentários");
                } else {
                    if (data.proximosIndexes < 18) {
                        body.removeEventListener('scroll', chamarComentarios);
                    }
                    data.resultado.forEach((post, index) => {
                        if (index == 0) {
                            construirComentario(primeiroComentario, post)
                            console.log("oiieee");

                        } else {
                            containerGeral.appendChild(novaBarra)
                            const novoComentario = primeiroComentario.cloneNode(true);
                            containerGeral.appendChild(novoComentario)
                            construirComentario(novoComentario, post)
                        }
                    });
                }

            })
            .catch(err => console.error(err));

        function construirComentario(comentario, dados) {
            imagem = comentario.querySelector(".imagem")
            comentario.querySelector(".container-texto-comentario").textContent = dados.texto
            comentario.querySelector(".autor").textContent = dados.autor.nome
            comentario.querySelector(".autor").addEventListener("click", function () {
                window.location.href = "https://localhost:8080/usuario/" + id;
            })
            comentario.querySelector(".paragrafo").innerHTML = dados.texto
            if (dados.autor.foto.imagem == "" || dados.autor.foto.tipoImagem) {
                imagem.src = "/imagens/perfilIcon.png";
            } else {
                imagem.src = "data:image/" + dados.autor.foto.tipoImagem + ";base64," + dados.autor.foto.imagem;
            }
            imagem.addEventListener("click", function () {
                window.location.href = "https://localhost:8080/usuario/" + id;
            })
        }
    }

}

document.addEventListener("DOMContentLoaded", iniciarPublicacao)