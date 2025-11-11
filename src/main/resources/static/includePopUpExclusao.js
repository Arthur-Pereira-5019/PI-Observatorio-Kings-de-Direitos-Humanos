async function carregarHTMLExclusaoUser(id, url, cssFile) {
    const response = await fetch(url);
    const data = await response.text();
    document.getElementById(id).innerHTML = data;

    if (cssFile) {
        const link = document.createElement("link");
        link.rel = "stylesheet";
        link.href = cssFile;
        document.head.appendChild(link);
    }
}
async function iniciarExclusaoUser() {
    await carregarHTMLExclusaoUser("excluirPerfil", "/popupDeleteUser", "/deleteUsuarioPopupStyle.css");

    const fundoPopupConfigUser = document.getElementById("posPopUpConfigUser");
    const fundoPopupDelete = document.getElementById("posPopUpDelete");
    fundoPopupDelete.addEventListener("click", (e) => {
        if (e.target === fundoPopupDelete) {
            fundoPopupDelete.style.display = "none";
            fundoPopupConfigUser.style.display = "flex"
        }

    })

    const btnCancelar = document.getElementById("btnCancelarExclusao");
    btnCancelar.addEventListener("click", function () {
        fundoPopupDelete.style.display = "none";
        fundoPopupConfigUser.style.display = "flex"

    })

    const btnConfirmarExclusao = document.getElementById("btnConfirmarExclusao")


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