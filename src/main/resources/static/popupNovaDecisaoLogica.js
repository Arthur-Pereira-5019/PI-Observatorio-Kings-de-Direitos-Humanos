async function iniciarPopupNovaDecisao(url) {
    const aplicar = document.querySelector(".botao-gerar");
    const blur = document.querySelector("#blur-nova-decisao");
    const motivacao = document.querySelector("#motivacao");
    const container_decisao = document.querySelector("#container_decisao");

    blur.display = "flex"
    container_decisao.display = "flex"

    aplicar.addEventListener("click", async function () {
        await excluir(url)
        
    })

    blur.addEventListener("click", sumir)

    function sumir() {
        container_decisao.remove();
        blur.remove();
    }

    async function excluir(url) {
        sumir();
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