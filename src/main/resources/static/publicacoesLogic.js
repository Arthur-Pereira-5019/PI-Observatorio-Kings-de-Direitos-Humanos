let botaoNovaPostagem;
let page = 0;
let inputBusca;

async function iniciarPublicacoes() {
    inputBusca = document.getElementById("")
    botaoNovaPostagem = document.getElementById("botao-moderador")
    fetch("http://localhost:8080/api/user", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })
        .then(data => {
            if (data.estadoDaConta == "ESPECIALISTA") {
                botaoNovaPostagem.style.display = "flex";
            }

        })
        .catch(err => console.error(err));


        const requestBody = {
            numeroPagina: page,
            parametro: "dataDaPostagem",
            ascending: false
        };

    async function gerarPublicacoes() {
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
                data.array.forEach(post => {
                    console.log(post.titulo);
                });

            })
            .catch(err => console.error(err));
    }
}



document.addEventListener("DOMContentLoaded", iniciarPublicacoes)