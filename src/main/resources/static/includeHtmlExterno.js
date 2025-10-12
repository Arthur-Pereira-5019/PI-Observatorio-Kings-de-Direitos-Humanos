const iconButton = document.getElementById("iconButton");

const seta_esquerda = document.getElementById("seta_esquerda");
const seta_direita = document.getElementById("seta_direita");

let page = 0;

async function carregarHTMLExterno(id, url, cssFile, jsFile) {
    const response = await fetch(url);
    const data = await response.text();
    document.getElementById(id).innerHTML = data;


    if (cssFile) {
        let link = document.createElement("link");
        link.rel = "stylesheet";
        link.href = cssFile;
        document.head.appendChild(link);
    }

    if (jsFile) {
        let script = document.createElement("script");
        script.src = jsFile;
        script.onload = () => {
            if (typeof iniciarCabecalho === "function") {
                iniciarCabecalho();
            }
        };
        document.body.appendChild(script);
    }
}


async function iniciarHtmlExterno() {
    await carregarHTMLExterno("header", "/cabecalho", "rodapeStyle.css", "/cabecalhoLogic.js");
    await carregarHTMLExterno("footer", "/rodape", "cabecalhoStyle.css");

    if (iconButton) {
        iconButton.addEventListener("click", function () {
            fetch("http://localhost:8080/api/user", {
                method: "GET",
                headers: { "Content-Type": "application/json" },
        
            })

        });
    }

    
}

document.addEventListener("DOMContentLoaded", iniciarHtmlExterno);
