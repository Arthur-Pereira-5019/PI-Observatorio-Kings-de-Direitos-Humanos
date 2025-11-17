let linkNoticiaEsquerda;
let linkNoticiaDireita;

const seta_esquerda = document.getElementById("seta_esquerda");
const seta_direita = document.getElementById("seta_direita");

let page = 0;

async function iniciarTelaInicial() {
    let page = 0;
    const noticiaDireita = document.getElementById("noticia_direita");
    const noticiaEsquerda = document.getElementById("noticia_esquerda");
    updateNews();

    seta_esquerda.addEventListener("click", function () {
        if (page > 0) {
            page--;
            updateNews();
        }
    })

    seta_direita.addEventListener("click", async function () {
        if (page < 3) {
            page++;
            updateNews();
        }
    })

    async function updateNews() {
        seta_esquerda.style.display = "flex"
        seta_direita.style.display = "flex"
        noticiaEsquerda.style.display = "flex"
        noticiaDireita.style.display = "flex"
        if (page == 0) {
            seta_esquerda.style.display = "none"
        }
        const textoEsquerda = document.getElementById("texto_esquerda");
        const textoDireita = document.getElementById("texto_direita");

        const ImagemEsquerda = document.getElementById("imagem_esquerda");
        const ImagemDireita = document.getElementById("imagem_direita");
        const carrossel = document.getElementById("carrossel");
        let na = document.createElement("h1")
        na.class


        fetch("http://localhost:8080/api/postagem/busca_paginada/" + page, {
            headers: { 'Content-Type': 'application/json' }
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                if (data[0]) {
                    textoEsquerda.textContent = data[0].titulo
                    linkNoticiaEsquerda = "http://localhost:8080/publicacao/" + data[0].idPostagem;
                    ImagemEsquerda.src = "data:image/" + data[0].capa.tipoImagem + ";base64," + data[0].capa.imagem;

                } else {
                    noticiaEsquerda.style.display = "none"
                }
                if (data[1]) {
                    textoDireita.textContent = data[1].titulo
                    linkNoticiaDireita = "http://localhost:8080/publicacao/" + data[1].idPostagem;
                    ImagemDireita.src = "data:image/" + data[1].capa.tipoImagem + ";base64," + data[1].capa.imagem;
                } else {
                    noticiaDireita.style.display = "none"
                    seta_direita.style.display = "none"
                }

                if (noticiaDireita.style.display == "none" && noticiaEsquerda.style.display == "none") {
                    na.textContent = "Nenhuma publicação encontrada!"
                    carrossel.appendChild(na)
                } else {
                    na.remove()
                }
            })



    }
    noticiaDireita.addEventListener("click", function () {
        window.location.href = linkNoticiaDireita;
    });
    noticiaEsquerda.addEventListener("click", function () {
        window.location.href = linkNoticiaEsquerda;
    })
}

document.addEventListener("DOMContentLoaded", iniciarTelaInicial);
