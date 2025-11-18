let botaoNovaPostagem;
let btnDireito;
let btnEsquerdo;
let btnLonge;
let btnPrimeiro;
let btnCampo;

async function iniciarRegistro() {
    consertarUrl()
    inputBusca = document.getElementById("campoPesquisa")

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

    document.querySelector("botao_requisicoes").addEventListener("click", function() {
        window.location.pathname = "requisicoes"
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

        fetch("http://localhost:8080/api/decmod/listar" + buscaf, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                const primeiroPost = document.querySelector('.decisao');
                const barra = document.querySelector('.container-linha');
                const containerGeral = document.querySelector(".container-decisao");

                if (data.resultado.length === 0) {
                    primeiroPost.querySelector('.container-baixo').remove()
                    btnLonge.textContent = paginaAtual();
                    let path = window.location.pathname
                    if (path != "/registro/%20/0") {
                        alert("Nenhum resultado encontrado!")
                            window.location.pathname = "/registro/ /0"

                    }
                    btnDireito.remove()
                } else {
                    btnLonge.textContent = paginaAtual() + Math.ceil(data.proximosIndexes / 25);
                    if (Number(paginaAtual()) == Number(btnLonge.textContent)) {
                        btnDireito.remove()
                    }
                    data.resultado.forEach((post, index) => {
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
            publicacao.querySelector(".acao").textContent = dados.acao
            publicacao.querySelector(".data").textContent  = "Data: " + dados.data
            publicacao.querySelector(".espaco").textContent = "Onde: " + dados.nomeEspaco
            publicacao.querySelector(".moderador").textContent = "Responsável: " + dados.nomeModerador
            publicacao.querySelector(".moderado").textContent = "Alvo: " + dados.nomeModerado
            publicacao.querySelector(".motivacao").textContent = "Motivação: " + dados.motivacao
            publicacao.querySelector(".moderador").addEventListener("click", function() {
                window.location.pathname = "usuario/"+dados.idModerador
            })
            publicacao.querySelector(".moderado").addEventListener("click", function() {
                window.location.pathname = "usuario/"+dados.idModerado
            })
            publicacao.querySelector(".espaco").addEventListener("click", function() {
                window.location.pathname = dados.linkEspaco
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
    if (ultima === '/registro') {
        window.location.pathname = "registro/ /0"
    }
}

function paginaAtual() {
    const url = window.location.href;
    const partes = url.split('/');
    let busca = partes.pop();
    return Number(busca) + 1;
}



document.addEventListener("DOMContentLoaded", iniciarRegistro)