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

     const fundoPopup = document.getElementById("posPopUp");
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

}

document.addEventListener("DOMContentLoaded", iniciar);
