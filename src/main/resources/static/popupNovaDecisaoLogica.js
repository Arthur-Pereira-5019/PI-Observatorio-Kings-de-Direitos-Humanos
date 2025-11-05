async function iniciarPopupNovaDecisao(url) {
    const aplicar = document.querySelector(".botao-geral");
    const blur = document.querySelector("#blur");
    const motivacao = document.querySelector("#blur");

    blur.display = "flex"

    aplicar.addEventListener("click", async function () {
        sumir()
        await excluir(url)
        window.location.reload();
    })

    blur.addEventListener("click", sumir)

    function sumir() {
        blur.remove();
    }

    async function excluir(url) {
        const requestBody = {
            "motivacao": motivacao.value
        }

        fetch(url, {
            method: 'DELETE',
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
}