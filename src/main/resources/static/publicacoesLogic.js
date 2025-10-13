let botaoNovaPostagem;
let paginaAtual = 0;
let inputBusca;

async function iniciarPublicacoes() {
    inputBusca = document.getElementById("campoPesquisa")
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
                botaoNovaPostagem.addEventListener("click", function() {
                    window.location.href = "http://localhost:8080/nova_publicacao";
                })
            }

        })
        .catch(err => console.error(err));


        const requestBody = {
            numeroPagina: paginaAtual,
            parametro: "dataDaPostagem",
            ascending: false
        };

    async function gerarPublicacoes() {
        fetch("http://localhost:8080/api/postagem/busca_paginada"+inputBusca.value, {
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
    gerarPublicacoes();
}



document.addEventListener("DOMContentLoaded", iniciarPublicacoes)