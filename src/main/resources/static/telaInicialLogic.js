async function iniciarTelaInicial() {
    let page = 0;
    updateNews();

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
                textoEsquerda.textContent = data[0].titulo
                textoDireita.textContent = data[1].titulo

                ImagemEsquerda.src = "data:image/"+data[0].capa.tipoImagem+";base64," + data[0].capa.imagem;
                ImagemDireita.src = "data:image/"+data[1].capa.tipoImagem+";base64," + data[1].capa.imagem;
            })
    }
}

    document.addEventListener("DOMContentLoaded", iniciarTelaInicial);
