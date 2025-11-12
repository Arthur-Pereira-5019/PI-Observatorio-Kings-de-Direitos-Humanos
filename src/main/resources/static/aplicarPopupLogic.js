async function iniciarPopupAplicarCargo(url) {
    const aplicar = document.querySelector(".botao-aplicar");
    const blur = document.querySelector(".blur_AP");
    const motivacao = document.querySelector("#textarea-cargos-aplicar");
    const container_decisao = document.querySelector("#fundo-popup-aplicar");

    blur.display = "flex"
    container_decisao.display = "flex"

    aplicar.addEventListener("click", async function () {
        await aplicar(url)
    })

    blur.addEventListener("click", sumir)


    function sumir() {
        container_decisao.remove();
        blur.remove();
    }

    async function aplicar(url) {
        sumir();
        const requestBody = {
            "motivacao": motivacao.value
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