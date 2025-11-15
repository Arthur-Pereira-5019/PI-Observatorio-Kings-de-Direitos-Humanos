let id;
async function iniciarPerfil() {
    const nomeUsuario = document.getElementById("nomeUsuario");

    const path = window.location.pathname;
    id = path.split("/").pop();

    async function anexarHTMLExterno(url, cssFile, jsFile, durl, msg) {
        const response = await fetch(url);
        const data = await response.text()
        const novoObjeto = document.createElement("div");
        document.body.appendChild(novoObjeto)
        novoObjeto.innerHTML = data;

        if (cssFile) {
            anexarCss(cssFile)
        }

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
                if (jsFile === "/aplicarPopupLogic.js" && typeof iniciarPopupAplicarCargo === "function") {
                    iniciarPopupAplicarCargo(durl);
                }
                if (jsFile === "/popupLogComentarios.js" && typeof iniciarPopupLogComentarios === "function") {
                    iniciarPopupLogComentarios();
                }
                
            };
            document.body.appendChild(script);
        }
    }




    if (!id || isNaN(id)) {
        window.location.pathname = "/telaInexistente"
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/user/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            window.location.pathname = "/telaInexistente"
        }

        const data = await response.json();

        nomeUsuario.textContent = data.nome;
        document.title = data.nome;
        const cargoAtual = document.getElementById(data.estadoDaConta)
        cargoAtual.style.display = "flex"

        const btnAtvUser = document.getElementById("btnAtvUser")
        const btnRequisitar = document.getElementById("btnRequisitar")
        const btnConfigUser = document.getElementById("btnConfigUser")
        const btnAddCargo = document.getElementById("btnAddCargo")
        const btnLogModerador = document.getElementById("btnLogModerador")
        const entrada = document.getElementById("capaUsuarioInput");

        if (data.proprio == 0) {
            btnConfigUser.remove()
            btnRequisitar.remove()
            btnAddCargo.remove()
            btnAtvUser.remove()
            btnLogModerador.remove()
            entrada.remove()
        } else if (data.proprio == 1) {
            btnConfigUser.style.display = "flex"
            btnRequisitar.style.display = "flex"
        } else if (data.proprio == 2) {
            btnAddCargo.style.display = "flex"
            btnAtvUser.style.display = "flex"
            btnAtvUser.addEventListener("click", async function(){
                await anexarHTMLExterno("/log_com","/log_comentarios.css","/popupLogComentarios.js")
            })
            entrada.remove()
        } else {
            btnConfigUser.style.display = "flex"
            btnRequisitar.style.display = "flex"
            btnLogModerador.style.display = "flex"
        }

        entrada.addEventListener("input", input_capa);


        function input_capa() {
            capaPreview = document.querySelector(".icon-user");

            const imagemSubmetida = entrada.files[0];

            if (imagemSubmetida && imagemSubmetida.name.endsWith(".png") || imagemSubmetida.name.endsWith(".jpg") || imagemSubmetida.name.endsWith(".jpeg")) {
                const reader = new FileReader();

                reader.onload = (e) => {
                    const base64StringWithPrefix = e.target.result;
                    requestBody = {
                        imageBase64: base64StringWithPrefix.replace(base64StringWithPrefix.substring(0, base64StringWithPrefix.indexOf(",") + 1), ""),
                        descricao: "Foto de perfil de " + data.nome,
                        titulo: "Foto de perfil de " + data.nome
                    }

                    fetch("http://localhost:8080/api/user/atualizar_imagem", {
                        method: 'PUT',
                        body: JSON.stringify(requestBody),
                        headers: { 'Content-Type': 'application/json' },
                    })
                        .then(res => {
                            if (!res.ok) return res.json();
                            alert("Imagem adicionada com sucesso!")
                            window.location.reload();
                        })
                        .then(d => {
                            alert(d.mensagem)
                        })
                };

                reader.readAsDataURL(imagemSubmetida);
            }
        }

        if (data.fotoDePerfil != null) {
            let fotoB64 = data.fotoDePerfil.imagem
            if (fotoB64 != "") {
                document.querySelector(".icon-user").src = "data:image/" + data.fotoDePerfil.tipoImagem + ";base64," + fotoB64;
            }
        }

        btnAddCargo.addEventListener("click", function () {
            anexarHTMLExterno("/aplicar_cargo", "/aplicarPopupStyle.css", "/aplicarPopupLogic.js", "http://localhost:8080/api/user/aplicar_cargo/" + id)
        })

        async function gerarPublicacoes() {
            fetch("http://localhost:8080/api/postagem/usuario/" + id, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' },
            })
                .then(res => {
                    if (!res.ok) throw new Error("Erro no servidor");
                    return res.json();
                })
                .then(data => {
                    const primeiroPost = document.querySelector('.container-geral-publicacoes');
                    const containerGeral = document.getElementById("container-lista");

                    if (data.length === 0) {
                        primeiroPost.querySelector('.container-baixo').remove()
                    } else {
                        data.forEach((post, index) => {
                            if (index == 0) {
                                construirPublicacao(primeiroPost, post)
                            } else {
                                const novoPost = primeiroPost.cloneNode(true);
                                containerGeral.appendChild(novoPost)
                                construirPublicacao(novoPost, post)
                            }
                        });
                    }

                })
                .catch(err => console.error(err));

            function construirPublicacao(publicacao, dados) {
                novoT = dados.titulo
                if (novoT.length > 35) {
                    novoT = novoT.substring(0, 35) + "..."
                }
                publicacao.querySelector(".titulo-publicacao").textContent = novoT
                publicacao.querySelector(".autor").textContent = dados.autor
                publicacao.querySelector(".data").textContent = dados.data
                publicacao.querySelector(".paragrafo").innerHTML = dados.texto
                if (dados.capa.imagem == "" || dados.capa.tipoImagem) {
                    publicacao.querySelector(".imagem").src = "/imagens/publicacao.png";
                } else {
                    publicacao.querySelector(".imagem").src = "data:image/" + dados.capa.tipoImagem + ";base64," + dados.capa.imagem;
                }
                publicacao.addEventListener("click", function () {
                    window.location.href = "http://localhost:8080/publicacao/" + dados.idPostagem
                })
            }
        }

        async function gerarForuns() {
            fetch("http://localhost:8080/api/forum/usuario/" + id, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' }
            })

                .then(res => {
                    if (!res.ok) throw new Error("Erro no servidor");
                    return res.json();
                })
                .then(data => {
                    const primeiroPost = document.querySelector('.container-forum');
                    const containerGeral = document.getElementById("containerForuns");
                    if (data.length === 0) {
                        primeiroPost.remove()
                    } else {
                        data.forEach((post, index) => {
                            if (index == 0) {
                                construirForum(primeiroPost, post)
                            } else {
                                const novoPost = primeiroPost.cloneNode(true);
                                containerGeral.appendChild(novoPost)
                                construirForum(novoPost, post)
                            }
                        });
                    }

                })

            function construirForum(forum, dados) {
                novoT = dados.titulo
                if (novoT.length > 35) {
                    novoT = novoT.substring(0, 35) + "..."
                }
                forum.querySelector(".titulo-forum").textContent = novoT
                forum.querySelector(".data-forum").textContent = dados.dataCriacao
                forum.querySelector(".nRespostas").textContent = "Respostas: " + dados.respostas
                forum.querySelector(".dAtualizacao").textContent = "Última Atualização: " + dados.ultimaAtualizacao
                forum.addEventListener("click", function () {
                    window.location.href = "http://localhost:8080/forum/" + dados.idForum
                })
            }

        }

        await gerarForuns()
        gerarPublicacoes()


    } catch (error) {
        console.error('Erro:', error);
    }


}


document.addEventListener("DOMContentLoaded", iniciarPerfil);