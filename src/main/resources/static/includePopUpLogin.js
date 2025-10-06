async function carregarHTML(id, url, cssFile) {
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

async function iniciar() {
    await carregarHTML("login", "/popupLogin", "popUpLoginStyle.css");

    const fundoPopupLogin = document.getElementById("posPopUpLogin");
    if (fundoPopupLogin) fundoPopupLogin.style.display = "none";

    const botaoAbrirLogin = document.getElementById("loginButtonCabc");
    const botaoVoltarRegistro = document.getElementById("registerButtonCabc");
    const fundoPopupRegistro = document.getElementById("posPopUp");

    if (botaoAbrirLogin && fundoPopupLogin) {
        botaoAbrirLogin.addEventListener("click", () => {
            fundoPopupLogin.style.display = "flex";
            if (fundoPopupRegistro) fundoPopupRegistro.style.display = "none";
        });
    }

    if (botaoVoltarRegistro && fundoPopupRegistro) {
        botaoVoltarRegistro.addEventListener("click", () => {
            fundoPopupLogin.style.display = "none";
            fundoPopupRegistro.style.display = "flex";
        });
    }

    const fechar = document.querySelector(".botao-fechar");
    if (fechar && fundoPopupLogin) {
        fechar.addEventListener("click", () => {
            fundoPopupLogin.style.display = "none";
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
                    return res.json();
                })
                .then(() => window.location.href = "http://localhost:8080/")
                .then(res => {
                    if (res.ok){
                        fundoPopupLogin.style.display = "none";

                    }
                    
                })
                .catch(err => console.error(err));
        });
    }
}

document.addEventListener("DOMContentLoaded", iniciar);