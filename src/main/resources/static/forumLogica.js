let btnDireito;
let btnEsquerdo;
let btnLonge;
let btnPrimeiro;
let btnCampo;

const btnCriarForum = document.getElementById("botao-addForum-tela-foruns")

btnCriarForum.addEventListener("click", function () {
    window.location.href = "http://localhost:8080/novo_forum";
})

consertarUrl()



btnDireito = document.getElementById("botaodireito");
btnDireito.addEventListener("click", function () {
    moverUrl(1)
})
btnEsquerdo = document.getElementById("botaoesquerdo");


btnEsquerdo.addEventListener("click", function () {
    moverUrl(-1)
})
const url = window.location.href;
const partes = url.split('/');
if (partes.pop() == 0) {
    btnEsquerdo.remove()
}


btnAtual = document.getElementById("botaoatual");
btnAtual.textContent = paginaAtual()

btnPrimeiro = document.getElementById("botaoprimeiro");
btnPrimeiro.addEventListener("click", function () {
    moverUrl(-1 * (paginaAtual() - 1))
})

btnCampo = document.getElementById("botaocampo");
btnCampo.addEventListener("keydown", async function (event) {
    if (event.key === "Enter") {
        moverUrl(btnCampo.value - paginaAtual())
    }
})

btnLonge = document.getElementById("botaolonge");
btnLonge.textContent = paginaAtual() + 5;
btnLonge.addEventListener("click", async function () {
    moverUrl(Number(btnLonge.textContent) - paginaAtual())
})

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
                console.log("receba")
                primeiroPost.remove()
                alert("Nenhum resultado encontrado!")
                btnLonge.textContent = paginaAtual();
                let path = window.location.pathname
                if (path != "/foruns/%20/0") {
                    alert("Nenhum resultado encontrado!")
                    window.location.pathname = "/foruns/ /0"
                }

                btnDireito.remove()
            } else {
                btnLonge.textContent = paginaAtual() + data.proximosIndexes % 10;
                if (Number(paginaAtual()) == Number(btnLonge.textContent)) {
                    btnDireito.remove()
                }
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
            window.location.href = "http://localhost:8080/forum/" + dados.idForum
        })
    }

}

gerarForuns()

const botaoComentar = document.getElementById("botao-enviar-comentario-forum")
const textoComentario = document.getElementById("text-area-comentario-forum")

botaoComentar.addEventListener("click", function () {
    console.log("laele")
    if (textoComentario.value.length > 512) {
        alert("O seu comentário está grande de mais!")
        return;
    }
    
    const requestBody = {
        textoComentario: textoComentario.value,
        tipo: 'F',
        idComentavel: id,
    };

    fetch("http://localhost:8080/api/com/", {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestBody)
    })
        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })
        .then(data => {
            window.location.reload()
        })
})

async function moverUrl(d) {
    const url = window.location.href;
    const partes = url.split('/');
    let busca = partes.pop();
    busca = Number(busca) + d;
    if (busca >= 0) {
        partes.push(busca)
        window.location.href = partes.join("/");
    }
}

function consertarUrl() {
    const url = window.location.href;
    const partes = url.split('/');
    let ultima = "/" + partes.pop();
    if (ultima === '/foruns') {
        window.location.href = "http://localhost:8080/foruns/ /0"
    }
}

function paginaAtual() {
    const url = window.location.href;
    const partes = url.split('/');
    let busca = partes.pop();
    return Number(busca) + 1;
}