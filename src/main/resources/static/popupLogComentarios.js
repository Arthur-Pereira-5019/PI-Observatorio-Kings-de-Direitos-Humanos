async function iniciarPopupLogComentarios() {
    const blur = document.querySelector("#blur_LC");
    const container_log = document.querySelector(".fundo-popup-aplicar");

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
            comentario.querySelector("textoComentario").textContent = dados.texto
            comentario.querySelector(".data-log").textContent = dados.dataComentario
            comentario.querySelector(".post-original").textContent = dados.tituloDono
            comentario.querySelector(".post-original").addEventListener("click", function () {
                window.location.pathname = "/" + (dados.tipo == 'F' ? "foruns" : "postagens") + dados.id
            })
        }
    }
}