const iconButton = document.getElementById("iconButton");


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
            

        });
    } 
}

document.addEventListener("DOMContentLoaded", iniciar);
