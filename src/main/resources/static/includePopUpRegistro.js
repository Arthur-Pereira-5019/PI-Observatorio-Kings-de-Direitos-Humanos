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
    await carregarHTML("registro", "/popupRegistro", "popUpRegistroStyle.css");

    const fundoPopupRegistro = document.getElementById("posPopUp");
    if (fundoPopupRegistro) fundoPopupRegistro.style.display = "none";

    const botaoAbrirRegistro = document.getElementById("iconButton");
    const botaoAbrirLogin = document.getElementById("btnIrLogin");
    const fundoPopupLogin = document.getElementById("posPopUpLogin");

    if (botaoAbrirRegistro && fundoPopupRegistro) {
        botaoAbrirRegistro.addEventListener("click", () => {
            fundoPopupRegistro.style.display = "flex";
        });
    }

    if (botaoAbrirLogin && fundoPopupLogin) {
        botaoAbrirLogin.addEventListener("click", () => {
            fundoPopupRegistro.style.display = "none";
            fundoPopupLogin.style.display = "flex";
        });
    }

    fundoPopupRegistro.addEventListener("click", (e) => {
        if (e.target === fundoPopupRegistro) {
            fundoPopupRegistro.style.display = "none";
        }
    });

    const registerButton = document.getElementById("registerButton");
    const senhaInputRegistro = document.getElementById("senhaInputRegistro");
    const confSenhaInputRegistro = document.getElementById("confSenhaInputRegistro");
    const inputNomeRegistro = document.getElementById("inputNomeRegistro");
    const inputTelefoneRegistro = document.getElementById("inputTelefoneRegistro");
    const inputCpfRegistro = document.getElementById("inputCpfRegistro");
    const inputEmailRegistro = document.getElementById("inputEmailRegistro");
    const inputDataNascRegistro = document.getElementById("inputDataNascRegistro");

    if (registerButton) {
        registerButton.addEventListener("click", () => {
            if (senhaInputRegistro.value !== confSenhaInputRegistro.value) return;

            const novoPost = {
                nome: inputNomeRegistro.value,
                senha: senhaInputRegistro.value,
                telefone: inputTelefoneRegistro.value,
                cpf: inputCpfRegistro.value,
                email: inputEmailRegistro.value,
                dataDeNascimento: inputDataNascRegistro.value
            };

            fetch("http://localhost:8080/api/user", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(novoPost)
            })
                .then(res => {
                    if (!res.ok) throw new Error("Erro no servidor");
                    return res.json();
                })
                .then(() => {
                    inputNomeRegistro.value = "";
                    senhaInputRegistro.value = "";
                    confSenhaInputRegistro.value = "";
                    inputTelefoneRegistro.value = "";
                    inputCpfRegistro.value = "";
                    inputEmailRegistro.value = "";
                    inputDataNascRegistro.value = "";
                    window.location.href = "http://localhost:8080/";
                })
                .catch(err => console.error(err));
        });
    }
}

document.addEventListener("DOMContentLoaded", iniciar);