async function iniciarPopupRegistro() {
    const fundoPopupRegistro = document.getElementById("posPopUp");
    if (fundoPopupRegistro) fundoPopupRegistro.style.display = "none";

    const botaoAbrirRegistro = document.getElementById("iconButton");
    const botaoAbrirLogin = document.getElementById("btnIrLogin");
    const fundoPopupLogin = document.getElementById("posPopUpLogin");
    
        botaoAbrirRegistro.addEventListener("click", () => {

            fetch("http://localhost:8080/api/user", {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
                .then(response => {
                    if (!response.ok) {

                        throw new Error('Erro na requisição');

                    }
                    return response.json();
                })
                .then(data => {
                    usuarioId = data.idUsuario;

                    window.location.href = '/usuario/' + usuarioId;

                })
                .catch(error => {
                    fundoPopupRegistro.style.display = "flex";
                    console.error('Erro:', error);
                });

        });

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

document.addEventListener("DOMContentLoaded", iniciarPopupRegistro);