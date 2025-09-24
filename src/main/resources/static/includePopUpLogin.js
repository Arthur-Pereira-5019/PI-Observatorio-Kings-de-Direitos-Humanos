async function carregarHTML(id, url, cssFile) {
    const response = await fetch(url);

    const data = await response.text();

    document.getElementById(id).innerHTML = data;

    if (cssFile) {
        let link = document.createElement("link");
        link.rel = "stylesheet";
        link.href = cssFile;
        document.head.appendChild(link);
    }
}

async function iniciar() {

    await carregarHTML("login", "/popupLogin", "popUpLoginStyle.css");
    const botaoAcesso = document.getElementById("botao-acessar");

    const fundoPopup = document.getElementById("posPopUpLogin");

    const campoSenha = document.getElementById("campoSenha");
    const campoEmail = document.getElementById("campoEmail");

    if (fundoPopup) fundoPopup.style.display = "none";


    const loginTesteButton = document.getElementById("loginButtonCabc");
    if (loginTesteButton && fundoPopup) {
        loginTesteButton.addEventListener("click", () => {
            fundoPopup.style.display = "flex";
        });
    }


    const fechar = document.querySelector(".botao-fechar");
    if (fechar && fundoPopup) {
        fechar.addEventListener("click", () => {
            fundoPopup.style.display = "none";
        });
    }


    fundoPopup.addEventListener("click", (e) => {
        if (e.target === fundoPopup) {
            fundoPopup.style.display = "none";
        }
    });

    botaoAcesso.addEventListener("click", () => {
        const novoPost = {
            senha: campoSenha.value,
            email: campoEmail.value
        };


        fetch("http://localhost:8080/api/user/login", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoPost)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                window.location.href = "http://localhost:8080/"

            })
            .catch(err => console.error(err));
    });
}

const inputDataNascRegistro = document.getElementById("inputDataNascRegistro");



document.addEventListener("DOMContentLoaded", iniciar);
