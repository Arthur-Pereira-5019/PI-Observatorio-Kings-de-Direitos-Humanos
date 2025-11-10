async function iniciarPopupLogin() {
    
    const fundoPopupLogin = document.getElementById("posPopUpLogin");
    if (fundoPopupLogin) fundoPopupLogin.style.display = "none";

    const botaoAbrirLogin = document.getElementById("loginButtonCabc");
    const botaoVoltarRegistro = document.getElementById("btnIrRegistro");
    const fundoPopupRegistro = document.getElementById("posPopUp");

    

    if (botaoVoltarRegistro && fundoPopupRegistro) {
        botaoVoltarRegistro.addEventListener("click", () => {
            fundoPopupLogin.style.display = "none";
            fundoPopupRegistro.style.display = "flex";
        });
    }


    fundoPopupLogin.addEventListener("click", (e) => {
        if (e.target === fundoPopupLogin) {
            fundoPopupLogin.style.display = "none";
        }
    });

    const botaoAcesso = document.getElementById("botao-acessar");
    const campoSenha = document.getElementById("campoSenha");
    const campoEmail = document.getElementById("campoEmail");

    if (botaoAcesso) {
        botaoAcesso.addEventListener("click", () => {
            const novoPost = {
                senha: campoSenha.value,
                email: campoEmail.value
            };

            fetch("http://localhost:8080/api/user/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(novoPost)
            })
                .then(res => {
                    if (!res.ok) throw new Error("Erro no servidor");
                    window.location.reload();
                })
                .catch(err => console.error(err));
        });
    }
}

document.addEventListener("DOMContentLoaded", iniciarPopupLogin);