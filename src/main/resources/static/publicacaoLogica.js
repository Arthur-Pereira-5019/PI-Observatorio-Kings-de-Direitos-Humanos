async function iniciarPublicacao() {
    const capa = document.getElementById("capaPostagem");
    const titulo = document.getElementById("titulo");
    const dadosPublicacao = document.getElementById("dados_publicacao");
    const texto = document.getElementById("textoPublicacao");
    const cComentario = document.getElementById("text-area-comentario-forum");
    const caracteres = document.getElementById("limite-caracteres-criacao-comentario-forum");
    const pComentario = document.getElementById("botao-enviar-comentario-forum");
    const botaoOcultar = document.getElementById("botao_ocultar");
    const body = document.querySelector("body");
    const decmod = document.querySelector("#dec_mod_martelo");
    const footer = document.querySelector("#footer");

    const url = window.location.href;
    const id = url.substring(url.lastIndexOf('/') + 1)
    let com = 0;
    let buscando = false;
    let moderador = false;

    if (localStorage.getItem("comentarioSalvo")) {
        cComentario.value = localStorage.getItem("comentarioSalvo")
    }

    async function anexarHTMLExterno(url, cssFile, jsFile, durl, msg, idDenunciado, tipoDenunciado) {
        if (cssFile) {
            anexarCss(cssFile)
        }
        
        const response = await fetch(url);
        const data = await response.text()
        const novoObjeto = document.createElement("div");
        document.body.appendChild(novoObjeto)
        novoObjeto.innerHTML = data;

        if (jsFile) {
            let script = document.createElement("script");
            script.src = jsFile;
            script.onload = () => {
                if (jsFile === "/popupNovaDecisaoLogica.js" && typeof iniciarPopupNovaDecisao === "function") {
                    iniciarPopupNovaDecisao(durl, msg);
                }
                if (jsFile === "/popupDecisaoModeradoraLogic.js" && typeof iniciarPopupDecisao === "function") {
                    iniciarPopupDecisao(durl);
                }
                if (jsFile === "/popupCriacaoDenunciaLogic.js" && typeof iniciarPopupNovaDenuncia === "function") {
                    iniciarPopupNovaDenuncia(msg, idDenunciado, tipoDenunciado);
                }
            };
            document.body.appendChild(script);
        }
    }


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
            if (data.estadoDaConta == "MODERADOR" || data.estadoDaConta == "ADMINISTRADOR") {
                botaoOcultar.style.display = "flex";
                moderador = true;
            } else if (data.estadoDaConta == "PADRAO" || data.estadoDaConta == "ESPECIALISTA") {
                botaoOcultar.style.display = "flex";
                botaoOcultar.style.backgroundColor = "darkred"
                botaoOcultar.querySelector(".mais").src = "/imagens/megafone-icon_white.png"
                botaoOcultar.addEventListener("click", function () {
                    openCriacaoDenuncia("Sua denúncia será processada!", id, "Postagem")
                })
            }
            carregarComentarios();
        })
        .catch(err => console.error(err));

    fetch("http://localhost:8080/api/postagem/" + id, {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) {
                window.location.pathname = "telaInexistente"
            }
            return res.json();
        })
        .then(async (data) => {
            titulo.textContent = data.tituloPostagem;
            document.title = data.tituloPostagem;
            if (data.oculto) {
                titulo.style.color = "purple";
                decmod.style.display = "flex";
                decmod.addEventListener("click", function () {
                    anexarHTMLExterno("/decisao", "/popupDecisaoModeradoraStyle.css", "/popupDecisaoModeradoraLogic.js", "http://localhost:8080/api/decmod/Postagem/" + id, null)
                })

                botaoOcultar.style.backgroundColor = "green"
                botaoOcultar.querySelector("img").src = "/imagens/olhos_abertos.png"
                botaoOcultar.addEventListener("click", async function () {
                    let durl = "http://localhost:8080/api/postagem/ocultar/" + id;
                    await openCriacaoDecisao(durl, "Postagem desocultada com sucesso!");
                })
            } else if (moderador) {
                botaoOcultar.addEventListener("click", async function () {
                    let durl = "http://localhost:8080/api/postagem/ocultar/" + id;
                    await openCriacaoDecisao(durl, "Postagem oculta com sucesso!");
                })
            }
            if(data.proprio == true) {
                botaoOcultar.remove()
            }


            capa.src = "data:image/" + data.capa.tipoImagem + ";base64," + data.capa.imagem;
            dadosPublicacao.textContent = "Publicado em " + data.dataDaPostagem + " por " + data.autor.nome;
            let elementoSurpresa = document.createElement("div");
            let imgs;
            elementoSurpresa.innerHTML = data.textoPostagem;
            imgs = elementoSurpresa.querySelectorAll("img");
            for (const i of imgs) {
                i.src = await carregarSrc(i.dataset.db_id);
            }
            texto.innerHTML = elementoSurpresa.innerHTML;
        })







    cComentario.addEventListener("keydown", function () {
        setTimeout(function () {
            caracteres.textContent = cComentario.value.length + "/512";
        }, 1)

    })


    pComentario.addEventListener("click", function () {
        if (cComentario.value.length >= 512) {
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
                const cont = res.headers.get("content-type");
                if (!res.ok) {
                    if (res.status)
                        if (cont && cont.includes("application/json")) {
                            return res.json()
                        } else {
                            alert("Houve um erro ao comentar. Tente novamente mais tarde.")
                        }
                } else {
                    window.location.reload()
                    localStorage.setItem("comentarioSalvo", "")
                }
            }).then(data => {
                if (data.mensagem) {
                    if (data.mensagem.includes("Access Denied")) {
                        alert("Você precisa se autenticar antes de comentar!")
                        localStorage.setItem("comentarioSalvo", cComentario.value)
                        document.querySelector(".perfil").click()
                    } else {
                        alert(data.mensagem)

                    }
                }
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
            btnDenuncia = comentario.querySelector(".denunciarCom")
            comentario.querySelector(".container-texto-comentario").textContent = dados.texto
            comentario.querySelector(".autor").textContent = dados.autor.nome
            comentario.querySelector(".autor").addEventListener("click", function () {
                window.location.pathname = "usuario/" + dados.autor.id;
            })
            if (dados.autor.foto == null) {
                imagem.src = "/imagens/perfilIconDark.png";
            } else if (dados.autor.foto.imagem == "") {
                imagem.src = "/imagens/perfilIconDark.png";
            } else {
                imagem.src = "data:image/" + dados.autor.foto.tipoImagem + ";base64," + dados.autor.foto.imagem;
            }
            if (dados.proprio) {
                exclusao.style.backgroundColor = 'darkred'
                btnDenuncia.style.display = 'none'

                exclusao.addEventListener("click", function () {
                    excluirProprioComentario("http://localhost:8080/api/com/excluir_proprio/" + dados.id, "Comentário excluído com sucesso!")
                })
            } else if (moderador) {
                exclusao.style.backgroundColor = 'purple'
                btnDenuncia.style.display = 'none'
                exclusao.addEventListener("click", function () {
                    openCriacaoDecisao("http://localhost:8080/api/com/excluir/" + dados.id, "Comentário excluído com sucesso!")
                })
            } else {
                exclusao.style.display = 'none'
                btnDenuncia.style.backgroundColor = 'darkred'
                btnDenuncia.addEventListener("click", function () {
                    openCriacaoDenuncia("Sua denúncia será processada!", dados.id, "Comentario")
                })

            }
            imagem.addEventListener("click", function () {
                window.location.pathname = "usuario/" + dados.autor.id;
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

    async function openCriacaoDenuncia(msg, id, tipo) {
        await anexarHTMLExterno("/popupNovaDenuncia", "/popupNovaDenunciaStyle.css", "/popupCriacaoDenunciaLogic.js", "", msg, id, tipo);
    }

    async function carregarSrc(id) {
        try {
            let response = await fetch("http://localhost:8080/api/imagem/" + id, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' }
            })
            if (!response.ok) throw new Error("Erro no servidor");

            let data = await response.json();
            return "data:image/" + data.tipoImagem + ";base64," + data.imagem;
        } catch (err) {
            console.error(err);
            return null;
        };
    }
}


document.addEventListener("DOMContentLoaded", iniciarPublicacao)