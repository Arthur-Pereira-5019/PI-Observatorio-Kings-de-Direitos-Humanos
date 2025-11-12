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