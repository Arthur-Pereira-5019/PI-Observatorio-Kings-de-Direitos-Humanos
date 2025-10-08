const iconButton = document.getElementById("iconButton");

const seta_esquerda = document.getElementById("seta_esquerda");
const seta_direita = document.getElementById("seta_direita");

let page = 0;

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
    await carregarHTML("header", "/cabecalho", "rodapeStyle.css");
    await carregarHTML("footer", "/rodape", "cabecalhoStyle.css");

    if (iconButton) {
        iconButton.addEventListener("click", function () {
            fetch("http://localhost:8080/api/user", {
                method: "GET",
                headers: { "Content-Type": "application/json" },
        
            })

        });
    }

    
}

document.addEventListener("DOMContentLoaded", iniciar);
