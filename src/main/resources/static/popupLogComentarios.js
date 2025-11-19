async function iniciarPopupLogComentarios() {
    const blur = document.querySelector("#blur_LC");
    const container_log = document.querySelector(".fundo-popup-logs");

    blur.display = "flex"
    container_log.display = "flex"



    blur.addEventListener("click", sumir)


    function sumir() {
        container_log.remove();
        blur.remove();
    }

    carregarComentarios()

    async function carregarComentarios() {
        buscando = true;

        fetch("http://localhost:8080/api/com/encontrar_comentarios/" + id, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                const primeiroComentario = document.querySelector('#comentario');
                const containerGeral = document.querySelector(".container-comentario-logs");

                if (data.length === 0) {
                    containerGeral.remove()
                } else {
                    document.querySelector("#avisoComentarios").remove()
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
                buscando = false;
            })
            .catch(err => console.error(err));


        function construirComentario(comentario, dados) {
            const tipo = dados.tipo;
            let link = "/" + (tipo == 'F' ? "forum/" : "publicacao/") + dados.idDono;
            comentario.querySelector("#textoComentario").textContent = dados.texto
            comentario.querySelector(".data-log").textContent = dados.dataComentario
            comentario.querySelector(".post-original").textContent = ((tipo == 'F' ? "Fórum: " : "Publicação: ")) + dados.tituloDono
            comentario.querySelector(".post-original").addEventListener("click", function () {
                window.location.pathname = link
            })
            comentario.querySelector(".botao-ocultar").addEventListener("click", function () {
                let c = confirm("Deseja apagar o comentário sem olhar o contexto da publicação?")
                if (c) {
                    sumir()
                    openCriacaoDecisao("http://localhost:8080/api/com/excluir/" + dados.id, "Comentário excluído com sucesso")
                }

            })
        }
    }
}