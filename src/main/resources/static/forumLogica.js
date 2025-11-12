const btnCriarForum = document.getElementById("botao-addForum-tela-foruns")

btnCriarForum.addEventListener("click", function () {
    window.location.href = "http://localhost:8080/novo_forum";
})

consertarUrl()


const requestBody = {
    parametro: "dataDaPostagem",
    ascending: false
};

async function gerarPublicacoes() {
    const url = window.location.href;
    const partes = url.split('/');
    let busca2 = "/" + partes.pop();

    //Significa que já não tem nada || Isso aqui ainda vai ser útil?
    if (busca2 === '/foruns') {
        buscaf = "/"
    } else {
        let busca = "/" + partes.pop();
        if (busca === '/foruns') {
            buscaf = "/" + busca2
        } else {
            buscaf = busca + busca2
        }
    }

    fetch("http://localhost:8080/api/forum/listar_publicacoes" + buscaf, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestBody)
    })

        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })
        .then(data => {
            const primeiroPost = document.querySelector('.container-geral-publicacoes');
            const containerGeral = document.getElementById("container-lista");
            console.log(data);

            if (data.resultado.length === 0) {
                primeiroPost.querySelector('.container-baixo').remove()
                alert("Nenhum resultado encontrado!")
                btnLonge.textContent = paginaAtual();
                if (busca != "") {
                    inputBusca.value = ""
                    gerarPublicacoes()
                }
            } else {
                btnLonge.textContent = paginaAtual() + data.proximosIndexes % 10;
                data.resultado.forEach((post, index) => {
                    if (index == 0) {
                        construirPublicacao(primeiroPost, post)
                    } else {
                        const novoPost = primeiroPost.cloneNode(true);
                        containerGeral.appendChild(novoPost)
                        construirPublicacao(novoPost, post)
                    }
                });
            }

        })
        .catch(err => console.error(err));

    function construirPublicacao(publicacao, dados) {
        publicacao.querySelector(".titulo-publicacao").textContent = dados.titulo
        publicacao.querySelector(".autor").textContent = dados.autor
        publicacao.querySelector(".data").textContent = dados.data
        publicacao.querySelector(".paragrafo").innerHTML = dados.texto
        publicacao.addEventListener("click", function () {
            window.location.href = "http://localhost:8080/publicacao/" + dados.idPostagem
        })
    }

}

gerarPublicacoes()

function consertarUrl() {
    const url = window.location.href;
    const partes = url.split('/');
    let ultima = "/" + partes.pop();
    if (ultima === '/foruns') {
        window.location.href = "http://localhost:8080/foruns/ /0"
    }
}