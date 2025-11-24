async function iniciarPopupLogin() {

    const fundoPopupLogin = document.getElementById("posPopUpLogin");
    if (fundoPopupLogin) fundoPopupLogin.style.display = "none";

    const botaoAbrirLogin = document.getElementById("loginButtonCabc");
    const botaoVoltarRegistro = document.getElementById("btnIrRegistro");
    const fundoPopupRegistro = document.getElementById("posPopUp");

    const btnOcultarLSenha = document.getElementById("ocultar_lSenha");
    const divLSenhaMaior = document.getElementById("campoSenha");

    btnOcultarLSenha.dataset.ativo = "0";

    btnOcultarLSenha.addEventListener("click", function () {
        ocultarSenha(btnOcultarLSenha, divLSenhaMaior);
    })

    document.querySelector("#iconButton").addEventListener("click", function () {
        fetch("http://localhost:8080/api/user", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        }).then(response => {
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
                fundoPopupLogin.style.display = "flex";
                console.error('Erro:', error);
            });
    });

    function ocultarSenha(elemento, pai) {
        ativo = Boolean(Number(elemento.dataset.ativo))
        elemento.dataset.ativo = String(Number(!ativo));
        if (!ativo) {
            elemento.src = "/imagens/olhos_abertos.png"
            pai.type = "text"
        } else {
            elemento.src = "/imagens/olhosfechados.png"
            pai.type = "password"
        }
    }

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
    const lembrar = document.querySelector(".checkLembrar")

    if (botaoAcesso) {
        botaoAcesso.addEventListener("click", () => {
            const novoPost = {
                senha: campoSenha.value,
                email: campoEmail.value,
                lembrar: lembrar.checked
            };

            fetch("http://localhost:8080/api/user/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(novoPost)
            })
                .then(res => {
                    if (!res.ok) {
                        alert("Login ou senha inválidos!")
                    } else {
                        window.location.reload();

                    }
                })
                .catch(err => console.error(err));
        });
    }
}

document.addEventListener("DOMContentLoaded", iniciarPopupLogin);