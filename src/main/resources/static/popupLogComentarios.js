async function iniciarPopupLogComentarios(url) {
    const blur = document.querySelector("#blur_LC");
    const container_log = document.querySelector(".fundo-popup-aplicar");

    blur.display = "flex"
    container_log.display = "flex"

    aplicar.addEventListener("click", async function () {
        await aplicarCargo(url)
    })

    blur.addEventListener("click", sumir)


    function sumir() {
        container_log.remove();
        blur.remove();
    }

    async function carregarComentarios() {
        buscando = true;

        fetch("http://localhost:8080/api/com/encontrar_comentarios", {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                const primeiroComentario = document.querySelector('.container-comentario-logs');
                const containerGeral = document.querySelector("fundo-popup-logs");

                if (data.length === 0) {
                    primeiroComentario.remove()
                } else {
                    data.forEach((post, index) => {
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
            comentario.querySelector("textoComentario").textContent = dados.texto
            comentario.querySelector(".autor").textContent = dados.autor.nome
            comentario.querySelector(".autor").addEventListener("click", function () {
                window.location.href = "http://localhost:8080/usuario/" + dados.autor.id;
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

    async function aplicarCargo(url) {
        sumir();
        let sel = getId();
        if(sel == -1) {
            alert("Selecione um cargo!")
            return;
        }
        const requestBody = {
            "motivacao": motivacao.value,
            "idCargo": sel
        }

        fetch(url, {
            method: "PUT",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) {
                    alert("Permissões insuficientes");
                    return;
                }
                alert("Cargo aplicado com sucesso!")
                window.location.reload();
            })
            .catch(err => console.error(err));
    }
}