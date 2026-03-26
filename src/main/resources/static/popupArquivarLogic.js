async function iniciarPopupArquivar(idForum) {
    const aplicar = document.querySelector(".botao-arquivar");
    const blur = document.querySelector("#blur-arquivar");
    const motivacao = document.querySelector("#motivacaoArquivar");
    const container_arquivar = document.querySelector("#container_arquivar");

    blur.display = "flex"
    container_arquivar.display = "flex"

    aplicar.addEventListener("click", async function () {
        await denunciar()
    })

    blur.addEventListener("click", sumir)

    function sumir() {
        container_arquivar.remove();
        blur.remove();
    }

    async function denunciar() {
        sumir();
        requestBody = {
            "id":id,
            "motivacao":motivacao.value
        }
        fetch("http://localhost:8080/api/forum/arquivar", {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        }).then(res => {
            if(!res.ok) {
                return res.json()
            }
            alert("Fórum arquivado com sucesso!")
            window.location.reload()
        }).then(data => {
            alert(data.mensagem)
        })
    }
}