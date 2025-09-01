const hideButtons = document.querySelectorAll(".hideButton");
const hideIcons = document.querySelectorAll(".hideIcon");
inputNomeRegistro = document.getElementById("inputNomeRegistro")
senhaInputRegistro = document.getElementById("senhaInputRegistro");
confSenhaInputRegistro = document.getElementById("confSenhaInputRegistro");
inputTelefoneRegistro = document.getElementById("inputTelefoneRegistro");
inputCpfRegistro = document.getElementById("inputCpfRegistro");
inputEmailRegistro = document.getElementById("inputEmailRegistro");
inputDataNascRegistro = document.getElementById("inputDataNascRegistro")


const url = 'https://localhost:8080/api/user';
const novoPost = {
  title: 'dadosRegistro',
  nome : inputNomeRegistro.value,
    senha : senhaInputRegistro.value,
  telefone : inputTelefoneRegistro.value,
  cpf : inputCpfRegistro.value,
  email: inputEmailRegistro.value,
  dataNasc : inputDataNascRegistro.value

};


fetch("http://localhost:8080/api/user", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(novoPost)
})
.then(response => {
    if (!response.ok) {
        throw new Error('sem resposta');
    }
    return response.json();
})
.then(data => {
    console.log('Post feito:', data);
})
.catch(error => {
    console.error('erro no post:', error);
});


    hideIcons.forEach(icon => {
        icon.addEventListener("click", () => {
            const inputId = icon.getAttribute("data-target");
            const input = document.getElementById(inputId);

            if (input.type === "password") {
                input.type = "text";
                icon.src = "olhoAberto.png";
            } else {
                input.type = "password";
                icon.src = "olhoFechado.png";
            }
        });
    });


console.log(novoPost)