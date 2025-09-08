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
    await carregarHTML("registro", "/registro", "popUpRegistroStyle.css"); 
    
}

document.addEventListener("DOMContentLoaded", iniciar);