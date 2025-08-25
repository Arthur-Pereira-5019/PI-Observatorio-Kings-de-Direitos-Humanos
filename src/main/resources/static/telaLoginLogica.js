const hideButtons = document.querySelectorAll(".hideButton");
const hideIcons = document.querySelectorAll(".hideIcon");
inputEmailRegistro = document.getElementById("inputEmailRegistro");
inputCpfRegistro = document.getElementById("inputCpfRegistro");
inputTelefoneRegistro = document.getElementById("inputTelefoneRegistro");
senhaInputRegistro = document.getElementById("senhaInputRegistro");
inputDataNascRegistro = document.getElementById("inputDataNascRegistro")
confSenhaInputRegistro = document.getElementById("confSenhaInputRegistro");



const url = 'https://localhost:8080/api/user'; 
const newPost = {
  title: 'dadosRegistro',
  body: '',
  email: inputEmailRegistro.value,
  cpf : inputCpfRegistro.value,
  telefone : inputTelefoneRegistro.value,
  senha : senhaInputRegistro.value,
  dataNasc : inputDataNascRegistro.value 
};


fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(newPost)
  })
  .then(response => {
    if (!response.ok) {
      throw new Error('sem reposta');
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



