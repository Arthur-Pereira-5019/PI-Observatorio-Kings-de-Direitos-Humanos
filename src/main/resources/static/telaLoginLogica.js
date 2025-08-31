const hideButtons = document.querySelectorAll(".hideButton");
const hideIcons = document.querySelectorAll(".hideIcon");
inputEmailRegistro = document.getElementById("inputEmailRegistro");
inputCpfRegistro = document.getElementById("inputCpfRegistro");
inputTelefoneRegistro = document.getElementById("inputTelefoneRegistro");
senhaInputRegistro = document.getElementById("senhaInputRegistro");
inputDataNascRegistro = document.getElementById("inputDataNascRegistro")
confSenhaInputRegistro = document.getElementById("confSenhaInputRegistro");


const url = 'https://localhost:8080/api/user'; 
const novoPost = {
  title: 'dadosRegistro',
  id : 1231412,
  nome : "oi",
   senha : senhaInputRegistro.value,
  telefone : inputTelefoneRegistro.value,
  cpf : 11111111111,
  email: "a@a",
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
                icon.src = "./imagens/olhosfechados.png";
            } else {
                input.type = "password";
                icon.src = "./imagens/olhosfechados.png";
            }
        });
    });


console.log(novoPost)
    


