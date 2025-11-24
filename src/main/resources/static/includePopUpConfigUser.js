async function iniciarConfigUser() {
    const fundoPopupConfigUser = document.getElementById("posPopUpConfigUser");
    if (fundoPopupConfigUser) fundoPopupConfigUser.style.display = "none";

    const botaoAbrirConfigUser = document.getElementById("btnConfigUser");

    const btnDeleteUser = document.getElementById("btnDelete")

    btnDeleteUser.addEventListener("click", function () {
        anexarHTMLExternoPerfil("/popupDeleteUser", "/configuracaoUsuarioPopupStyle.css", "/includePopUpExclusao.js");
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
            fundoPopupConfigUser.remove()
        }
    });

}

document.addEventListener("DOMContentLoaded", iniciarConfigUser);