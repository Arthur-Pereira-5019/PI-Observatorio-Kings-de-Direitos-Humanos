const btnCriarForum = document.getElementById("botao-addForum-tela-foruns")

btnCriarForum.addEventListener("click", function () {
    window.location.href = "http://localhost:8080/novo_forum";
})

consertarUrl()


const requestBody = {
    parametro: "dataDaPostagem",
    ascending: false
};

async function gerarForuns() {
    const url = window.location.href;
    const partes = url.split('/');
    let busca2 = "/" + partes.pop();
    let buscaf

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

    console.log(buscaf)

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
            const primeiroPost = document.querySelector('.forum-tela-foruns');
            const containerGeral = document.getElementById("container-all-foruns");
            console.log(data);

            if (data.resultado.length === 0) {
                primeiroPost.querySelector('.forum-tela-foruns').remove()
                alert("Nenhum resultado encontrado!")
                //btnLonge.textContent = paginaAtual();
                if (busca != "") {
                    inputBusca.value = ""
                    gerarForuns()
                }
            } else {
                //btnLonge.textContent = paginaAtual() + data.proximosIndexes % 10;
                data.resultado.forEach((post, index) => {
                    if (index == 0) {
                        construirForum(primeiroPost, post)
                    } else {
                        const novoPost = primeiroPost.cloneNode(true);
                        containerGeral.appendChild(novoPost)
                        construirForum(novoPost, post)
                    }
                });
            }

        })
        .catch(err => console.error(err));

    function construirForum(forum, dados) {
        forum.querySelector(".tituloForum").textContent = dados.titulo
        forum.querySelector(".autorForum").textContent = dados.autor.nome
        forum.querySelector(".dataForum").textContent = dados.dataCriacao
        forum.querySelector(".respostasForum").textContent = dados.respostas
        forum.querySelector(".ultimaAtualizacao").textContent = dados.ultimaAtualizacao
        forum.addEventListener("click", function () {
            window.location.href = "http://localhost:8080/forum/" + dados.idPostagem
        })
    }

}

gerarForuns()

function consertarUrl() {
    const url = window.location.href;
    const partes = url.split('/');
    let ultima = "/" + partes.pop();
    if (ultima === '/foruns') {
        window.location.href = "http://localhost:8080/foruns/ /0"
    }
}

