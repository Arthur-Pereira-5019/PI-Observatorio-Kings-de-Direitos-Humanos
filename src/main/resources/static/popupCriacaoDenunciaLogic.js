async function iniciarPopupNovaDenuncia(msg, idDenunciado, tipoDenunciado) {
    const aplicar = document.querySelector(".botao-denunciar");
    const blur = document.querySelector("#blur-nova-denuncia");
    const motivacao = document.querySelector("#motivacaoDenuncia");
    const container_decisao = document.querySelector("#container_denuncia");

    blur.display = "flex"
    container_decisao.display = "flex"

    aplicar.addEventListener("click", async function () {
        await denunciar()

    })

    blur.addEventListener("click", sumir)

    function sumir() {
        container_decisao.remove();
        blur.remove();
    }

    async function denunciar() {
        sumir();
        const requestBody = {
            motivacao: motivacao.value,
            tipoDenunciado: tipoDenunciado,
            idDenunciado: idDenunciado
        }

        fetch("http://localhost:8080/api/denuncia/denunciar", {
            method: "PUT",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                alert(msg)
                window.location.reload();
            })
            .catch(err => console.error(err));
    }
}