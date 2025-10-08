let botaoNovaPostagem;

async function iniciarPublicacoes() {
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

    async function gerarPublicacoes() {
fetch("http://localhost:8080/api/postagem/busca_paginada", {
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
    }
}

document.addEventListener("DOMContentLoaded", iniciarPublicacoes)