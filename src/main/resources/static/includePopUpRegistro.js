async function iniciarPopupRegistro() {
    const fundoPopupRegistro = document.getElementById("posPopUp");
    if (fundoPopupRegistro) fundoPopupRegistro.style.display = "none";

    const botaoAbrirRegistro = document.getElementById("iconButton");
    const botaoAbrirLogin = document.getElementById("btnIrLogin");
    const fundoPopupLogin = document.getElementById("posPopUpLogin");
        const inputCpfRegistro = document.getElementById("inputCpfRegistro");

    
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

    const btnOcultarSenha = document.getElementById("ocultar_senha");
    const divCSenhaMaior = document.getElementById("confSenhaInputRegistro");
    const btnOcultarCSenha = document.getElementById("ocultar_csenha");
    const divSenhaMaior = document.getElementById("senhaInputRegistro");

    btnOcultarSenha.dataset.ativo = "0";
    btnOcultarCSenha.dataset.ativo = "0";

    btnOcultarSenha.addEventListener("click", function() {
        ocultarSenha(btnOcultarSenha, divSenhaMaior);
    })

    btnOcultarCSenha.addEventListener("click", function() {
        ocultarSenha(btnOcultarCSenha, divCSenhaMaior);
    })

    inputCpfRegistro.addEventListener("keydown", function(e) {
        if(e.key != "Backspace" && e.key != "Delete") {
            let v = inputCpfRegistro.value
            if(v.length == 3 || v.length == 7) {
                inputCpfRegistro.value += "."
            } else if (v.length == 11) {
                inputCpfRegistro.value += "-"
            }
        }
    })

    function ocultarSenha(elemento, pai) {
        ativo = Boolean(Number(elemento.dataset.ativo))
        elemento.dataset.ativo = String(Number(!ativo));
        if(!ativo) {
            elemento.src = "/imagens/olhos_abertos.png"
            pai.type = "text"
        } else {
            elemento.src = "/imagens/olhosfechados.png"
            pai.type = "password"
        }
    }


    const registerButton = document.getElementById("registerButton");
    const senhaInputRegistro = document.getElementById("senhaInputRegistro");
    const confSenhaInputRegistro = document.getElementById("confSenhaInputRegistro");
    const inputNomeRegistro = document.getElementById("inputNomeRegistro");
    const inputTelefoneRegistro = document.getElementById("inputTelefoneRegistro");
    const inputEmailRegistro = document.getElementById("inputEmailRegistro");
    const inputDataNascRegistro = document.getElementById("inputDataNascRegistro");
    const checkAceitar = document.querySelector(".checkAceitar");

    if (registerButton) {
        registerButton.addEventListener("click", () => {
            if (senhaInputRegistro.value !== confSenhaInputRegistro.value) {
                alert("As senhas não são iguais!")
                return;
            }
            if(!checkAceitar.checked) {
                alert("Aceite nossos termos de uso!")
                return
            }
            let gcpf = inputCpfRegistro.value
            if(gcpf.length == 14) {
                gcpf = gcpf.replaceAll(".","")
                gcpf = gcpf.replaceAll("-","")
            }

            const novoPost = {
                nome: inputNomeRegistro.value,
                senha: senhaInputRegistro.value,
                telefone: inputTelefoneRegistro.value,
                cpf: gcpf,
                email: inputEmailRegistro.value,
                dataDeNascimento: inputDataNascRegistro.value
            };

            fetch("http://localhost:8080/api/user/registrar", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(novoPost)
            })
                .then(res => {
                    if (!res.ok) {
                        return res.json();
                    }
                    inputNomeRegistro.value = "";
                    senhaInputRegistro.value = "";
                    confSenhaInputRegistro.value = "";
                    inputTelefoneRegistro.value = "";
                    inputCpfRegistro.value = "";
                    inputEmailRegistro.value = "";
                    inputDataNascRegistro.value = "";
                    window.location.reload()
                }).then(data => {
                    alert(data.mensagem)
                })
                .catch(err => console.error(err));
        });
    }
}

document.addEventListener("DOMContentLoaded", iniciarPopupRegistro);