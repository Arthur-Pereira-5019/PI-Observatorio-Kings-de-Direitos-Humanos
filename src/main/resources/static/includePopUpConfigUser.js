async function carregarHTMLConfigUser(id, url, cssFile) {
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
async function iniciarConfigUser() {
    await carregarHTMLConfigUser("editarPerfil", "/popupEditarPerfil", "configuracaoUsuarioPopupStyle.css");
    const fundoPopupConfigUser = document.getElementById("posPopUpConfigUser");
    if (fundoPopupConfigUser) fundoPopupConfigUser.style.display = "none";

    const botaoAbrirConfigUser = document.getElementById("btnConfigUser");

    const btnDeleteUser = document.getElementById("btnDelete")

    const fundoPopupDelete = document.getElementById("posPopUpDelete");
    btnDeleteUser.addEventListener("click", function () {
        fundoPopupDelete.style.display = "flex";
        fundoPopupConfigUser.style.display = "none";
    })

    if (botaoAbrirConfigUser && fundoPopupConfigUser) {
        botaoAbrirConfigUser.addEventListener("click", () => {
            fundoPopupConfigUser.style.display = "flex";

        })

        nomeAlterar = document.getElementById("campoNomeConfigUser")
        senhaAlterar = document.getElementById("campoSenhaConfigUser")
        senhaConfAlterar = document.getElementById("campoConfSenhaConfigUser")
        telefoneAlterar = document.getElementById("campoTelefoneConfigUser")
        checkNotificacoes = document.querySelector(".checkEmail")

        fetch("http://localhost:8080/api/user/config", {
            headers: { 'Content-Type': 'application/json' },
        }).then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })
            .then((data) => {
                telefoneAlterar.value = data.telefone
                nomeAlterar.value = data.nome
                checkNotificacoes.checked = data.notificacoesPorEmail
            })
            .catch(err => console.error(err));
    }

    const btnAplicar = document.getElementById("btnAplicar")
    btnAplicar.addEventListener("click", () => {

        const novoPut = {
            telefone: telefoneAlterar.value,
            nome: nomeAlterar.value,
            notificacoesPorEmail: checkNotificacoes.checked,
            senha: senhaAlterar.value

        }

        fetch("http://localhost:8080/api/user/atualizarUsuario", {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoPut)

        })
            .then(res => {
                if (!res.ok) return res.json();
                telefoneAlterar.value = ""
                nomeAlterar.value = ""
                senhaAlterar.value = ""
                senhaConfAlterar.value = ""

                fundoPopupConfigUser.style.display = "none";
                window.location.reload();
            })
            .then((data) => {
                alert(data.mensagem)
            })
            .catch(err => console.error(err));

    })

    fundoPopupConfigUser.addEventListener("click", (e) => {
        if (e.target === fundoPopupConfigUser) {
            fundoPopupConfigUser.style.display = "none";
        }
    });

}

document.addEventListener("DOMContentLoaded", iniciarConfigUser);