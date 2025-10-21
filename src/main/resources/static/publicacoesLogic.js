let botaoNovaPostagem;
let btnDireito;
let btnEsquerdo;
let btnLonge;
let btnPrimeiro;
let btnCampo;

async function iniciarPublicacoes() {
    consertarUrl()

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
                botaoNovaPostagem.addEventListener("click", function () {
                    window.location.href = "http://localhost:8080/nova_publicacao";
                })
            }

        })
        .catch(err => console.error(err));

    btnDireito = document.getElementById("botaodireito");
    btnDireito.addEventListener("click", function () {
        moverUrl(1)
    })
    btnEsquerdo = document.getElementById("botaoesquerdo");
    btnEsquerdo.addEventListener("click", function () {
        moverUrl(-1)
    })

    btnAtual = document.getElementById("botaoatual");
    btnAtual.textContent = await paginaAtual()

    btnPrimeiro = document.getElementById("botaoprimeiro");
    btnPrimeiro.addEventListener("click", async function () {
        moverUrl(-1 * (await paginaAtual() - 1))
    })

    btnCampo = document.getElementById("botaocampo");
    btnCampo.addEventListener("keydown", async function (event) {
        if (event.key === "Enter") {
            moverUrl(btnCampo.value - await paginaAtual())
        }
    })

    btnLonge = document.getElementById("botaolonge");
    btnLonge.textContent = await paginaAtual() + 5;
    btnLonge.addEventListener("click", async function () {
        moverUrl(await paginaAtual() + 4)
    })

    const requestBody = {
        parametro: "dataDaPostagem",
        ascending: false
    };

    async function gerarPublicacoes() {
        const url = window.location.href;
        const partes = url.split('/');
        let busca2 = "/" + partes.pop();

        //Significa que já não tem nada || Isso aqui ainda vai ser útil?
        if (busca2 === '/publicacoes') {
            buscaf = "/"
        } else {
            let busca = "/" + partes.pop();
            if (busca === '/publicacoes') {
                buscaf = "/" + busca2
            } else {
                buscaf = busca + busca2
            }
        }

        fetch("http://localhost:8080/api/postagem/listar_publicacoes" + buscaf, {
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
                const barra = document.querySelector('.container-linha');
                const containerGeral = document.getElementById("container-lista");
                if (data.length === 0) {
                    primeiroPost.querySelector('.container-baixo').remove()
                    alert("Nenhum resultado encontrado!")
                    if (busca != "") {
                        inputBusca.value = ""
                        gerarPublicacoes()
                    }
                } else {
                    data.forEach((post, index) => {
                        if (index == 0) {
                            construirPublicacao(primeiroPost, post)
                        } else {
                            const novaBarra = barra.cloneNode(true);
                            containerGeral.appendChild(novaBarra)
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
            publicacao.querySelector(".imagem").src = "data:image/" + dados.capa.tipoImagem + ";base64," + dados.capa.imagem;
            publicacao.addEventListener("click", function () {
                window.location.href = "http://localhost:8080/publicacao/" + dados.idPostagem
            })
        }
    }


    gerarPublicacoes();
}

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
    if (ultima === '/publicacoes') {
        window.location.href = "http://localhost:8080/publicacoes/ /0"
    }
}

async function paginaAtual() {
    const url = window.location.href;
    const partes = url.split('/');
    let busca = partes.pop();
    return Number(busca) + 1;
}



document.addEventListener("DOMContentLoaded", iniciarPublicacoes)