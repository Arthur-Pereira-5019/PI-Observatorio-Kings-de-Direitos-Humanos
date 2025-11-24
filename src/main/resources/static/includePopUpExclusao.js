async function iniciarExclusaoUser() {
    const fundoPopupConfigUser = document.getElementById("posPopUpConfigUser");
    const fundoPopupDelete = document.getElementById("posPopUpDelete");
    fundoPopupDelete.addEventListener("click", (e) => {
        if (e.target === fundoPopupDelete) {
            sumir()
        }

    })

    const btnCancelar = document.getElementById("btnCancelarExclusao");
    btnCancelar.addEventListener("click", function () {
        sumir()
    })

    const btnConfirmarExclusao = document.getElementById("btnConfirmarExclusao")

    function sumir() {
            fundoPopupDelete.remove()
            fundoPopupConfigUser.style.display = "flex"
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