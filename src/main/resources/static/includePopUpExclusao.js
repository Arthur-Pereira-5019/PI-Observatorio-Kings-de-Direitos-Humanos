async function iniciarExclusaoUser() {
    const blurDelete = document.getElementById("blurDelete");
    blurDelete.addEventListener("click", (e) => {
        sumir()
    })

    const btnCancelar = document.getElementById("btnCancelarExclusao");
    btnCancelar.addEventListener("click", function () {
        sumir()
    })

    const btnConfirmarExclusao = document.getElementById("btnConfirmarExclusao")

    function sumir() {
        blurDelete.parentNode.remove()
    }

    btnConfirmarExclusao.addEventListener("click", function () {
        const campoSenhaExc = document.getElementById("campoSenhaExc");
        const requestBody = {
            "senha": campoSenhaExc.value
        }

        fetch("http://localhost:8080/api/user/requisitar_exclusao", {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(requestBody)
        }).then(res => {
            if (!res.ok) {
                return res.json();
            }
            alert("Seu pedido de exclusão será processado.")
            window.location.reload()
        }).then(data => {
            alert(data.mensagem)
        })
    })

}

document.addEventListener("DOMContentLoaded", iniciarExclusaoUser);