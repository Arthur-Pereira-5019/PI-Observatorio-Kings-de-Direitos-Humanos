
const hideIcons = document.querySelectorAll(".hideIcon");
const registerButton = document.getElementById("registerButton");

const senhaInputRegistro = document.getElementById("senhaInputRegistro");
const confSenhaInputRegistro = document.getElementById("confSenhaInputRegistro");
const inputNomeRegistro = document.getElementById("inputNomeRegistro");
const inputTelefoneRegistro = document.getElementById("inputTelefoneRegistro");
const inputCpfRegistro = document.getElementById("inputCpfRegistro");
const inputEmailRegistro = document.getElementById("inputEmailRegistro");
const inputDataNascRegistro = document.getElementById("inputDataNascRegistro");

const inputEmailLogin = document.getElementById("inputEmailLogin");
const senhaLogin = document.getElementById("senhaLogin");

const loginButton = document.getElementById("loginButton");


hideIcons.forEach(icon => {
  icon.addEventListener("click", () => {
    const input = document.getElementById(icon.dataset.target);
    if (!input) return;
    if (input.type === "password") {
      input.type = "text";
      icon.src = "imagens/setaicon.png";
    } else {
      input.type = "password";
      icon.src = "imagens/olhosfechados.png";
    }
  });
});


loginButton.addEventListener("click", function(){

    const loginData = {
        title: 'dadosLogin',
        email: inputEmailLogin.value,
        senha: senhaLogin.value
    };

    fetch("http://localhost:8080/api/user/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (!response.ok) throw new Error("Erro no login");
        return response.json();
    })
    .then(data => {
        console.log("Usuário logado:", data);

        inputEmailLogin.value = '';
        senhaLogin.value = '';

        console.log(data.id)
        localStorage.setItem('id',data.id)
        localStorage.setItem('nome',data.nome)
        window.location.href = "http://localhost:8080/"

    })
    .catch(error => {
        console.error("Erro:", error);
    });


})


registerButton.addEventListener("click", () => {
  const novoPost = {
    title: 'dadosRegistro',
    nome: inputNomeRegistro.value,
    senha: senhaInputRegistro.value,
    telefone: inputTelefoneRegistro.value,
    cpf: inputCpfRegistro.value,
    email: inputEmailRegistro.value,
    dataDeNascimento: inputDataNascRegistro.value
  };

  fetch("http://localhost:8080/api/user", {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(novoPost)
  })
  .then(res => {
    if (!res.ok) throw new Error("Erro no servidor");
    return res.json();
  })
  .then(data => {
    console.log("Registro feito:", data);
    inputNomeRegistro.value = '';
    senhaInputRegistro.value = '';
    confSenhaInputRegistro.value = '';
    inputTelefoneRegistro.value = '';
    inputCpfRegistro.value = '';
    inputEmailRegistro.value = '';
    inputDataNascRegistro.value = '';

    window.location.href = "http://localhost:8080/"

  })
  .catch(err => console.error(err));
});