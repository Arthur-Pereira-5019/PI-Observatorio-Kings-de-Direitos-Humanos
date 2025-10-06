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

        });
    }

    seta_esquerda.addEventListener("click", function () {
        if (page > 0) {
            page--;
        }
    })

    seta_direita.addEventListener("click", function () {
        if (page < 3) {
            page++;
        }
    })

    function updateNews() {
        const requestBody = {
            numeroPagina: campoImagem,
            numeroResultados: 2,
            parametro: "Date",
            ascending: false
        };

        fetch("http://localhost:8080/api/postagem/busca_paginada", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                data
            })
    }
}

document.addEventListener("DOMContentLoaded", iniciar);
