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
    updateNews();


    if (iconButton) {
        iconButton.addEventListener("click", function () {

        });
    }

    seta_esquerda.addEventListener("click", function () {
        if (page > 0) {
            page--;
            updateNews();
        }
    })

    seta_direita.addEventListener("click", function () {
        if (page < 3) {
            page++;
            updateNews();
        }
    })

    function updateNews() {
        const textoEsquerda = document.getElementById("texto_esquerda");
        const textoDireita = document.getElementById("texto_direita");

        const ImagemEsquerda = document.getElementById("imagem_esquerda");
        const ImagemDireita = document.getElementById("imagem_direita");

        const requestBody = {
            numeroPagina: page,
            numeroResultados: 2,
            parametro: "dataDaPostagem",
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
                console.log(data);
                textoEsquerda.textContent = data[0].titulo
                textoDireita.textContent = data[1].titulo

                ImagemEsquerda = document.getElementById("imagem_esquerda");
                ImagemDireita = document.getElementById("imagem_direita");
            })
    }
}

document.addEventListener("DOMContentLoaded", iniciar);
