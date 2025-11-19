let btnDireito;
let btnEsquerdo;
let btnLonge;
let btnPrimeiro;
let btnCampo;
let filtro;
let tituloP;

async function iniciarTelaRequisicoes() {
    inputBusca = document.getElementById("campoPesquisa")
    filtro = document.getElementById("filtro")
    tituloP = document.getElementById("titulo");

    document.querySelector("#botao_requisicoes").addEventListener("click", function () {
        window.location.pathname = "registro"
    })

    document.querySelector("#img_seta").addEventListener("click", function () {
        window.location.pathname = "registro"
    })

    consertarUrl()
    corrigirNome()
    if (!localStorage.getItem('busca')) {
        localStorage.setItem('busca', 'cargos');
    } else {
        if (localStorage.getItem('busca') == 'denuncia') {
            filtro.selectedIndex = 2
        } else if (localStorage.getItem('busca') == 'exclusao') {
            filtro.selectedIndex = 1
        } else {
            filtro.selectedIndex = 0
        }
    }

    filtro.addEventListener("change", e => {
        if (e.target.value == "Titulações") {
            localStorage.setItem('busca', 'cargos');
        } else if (e.target.value == "Exclusão") {
            localStorage.setItem('busca', 'exclusao');
        } else {
            localStorage.setItem('busca', 'denuncia');
        }
        window.location.reload()
    })

    btnDireito = document.getElementById("botaodireito");
    btnDireito.addEventListener("click", function () {
        moverUrl(1)
    })

    btnPrimeiro = document.getElementById("botaoprimeiro");
    btnPrimeiro.addEventListener("click", function () {
        moverUrl(-1 * (paginaAtual() - 1))
    })

    btnEsquerdo = document.getElementById("botaoesquerdo");
    btnEsquerdo.addEventListener("click", function () {
        moverUrl(-1)
    })
    const url = window.location.href;
    const partes = url.split('/');
    if (partes.pop() == 0) {
        btnEsquerdo.remove()
        btnPrimeiro.remove()
    }

    btnAtual = document.getElementById("botaoatual");
    btnAtual.textContent = paginaAtual()

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

    async function buscarRequisicoes() {
        const url = window.location.href;
        const partes = url.split('/');
        let busca2 = "/" + partes.pop();

        let busca = partes.pop();
        buscaf = busca + busca2

        let tBusca = localStorage.getItem('busca')
        urlB = "http://localhost:8080/api/reqcar/listar_requisicoes/"
        if (tBusca == "exclusao") {
            urlB = "http://localhost:8080/api/exccon/listar_requisicoes/"
        } else if (tBusca == "denuncia") {
            urlB = "http://localhost:8080/api/denuncia/listar_requisicoes/"
        }

        fetch(urlB + buscaf, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                const primeiroPost = document.querySelector('.container-publicacao');
                const barra = document.querySelector('.container-linha');
                const containerGeral = document.getElementById("container-lista");

                if (data.resultado.length === 0) {
                    primeiroPost.style.display = "none"
                    barra.style.display = "none"
                    btnLonge.textContent = paginaAtual();
                    let path = window.location.pathname
                    if (path != "/requisicoes/%20/0") {
                        alert("Nenhum resultado encontrado!")
                        window.location.pathname = "/requisicoes/ /0"
                    }
                    btnDireito.remove()
                    btnLonge.remove()
                    btnCampo.remove()

                } else {
                    primeiroPost.style.display = "flex"
                    barra.style.display = "flex"
                    btnLonge.textContent = paginaAtual() + Math.ceil(data.proximosIndexes / 20);
                    if (Number(paginaAtual()) == Number(btnLonge.textContent)) {
                        btnDireito.remove()
                        btnLonge.remove()
                    }
                    data.resultado.forEach((post, index) => {
                        if (index == 0) {
                            mostrarRequisicao(primeiroPost, post)
                        } else {
                            const novaBarra = barra.cloneNode(true);
                            containerGeral.appendChild(novaBarra)
                            const novoPost = primeiroPost.cloneNode(true);
                            containerGeral.appendChild(novoPost)
                            mostrarRequisicao(novoPost, post)
                        }
                    });
                    if (!document.body.contains(btnEsquerdo) && !document.body.contains(btnDireito)) {
                        btnCampo.remove()
                    }
                }
            })
            .catch(err => console.error(err));



        function mostrarRequisicao(requisicao, dados) {
            requisicao.querySelector(".requisitor").textContent = dados.nomeRequisitor
            requisicao.querySelector(".data").textContent = dados.data
            let texto = dados.texto.replace("\n","<br>")
            requisicao.querySelector(".info").textContent = dados.info
            requisicao.querySelector(".texto").innerHTML = texto

            if (dados.id != 0) {
                requisicao.querySelector(".requisitor").addEventListener("click", function () {
                    window.location.pathname = "usuario/" + dados.idRequisitor
                })
            }
            if (dados.idExtra) {
                requisicao.querySelector(".info").addEventListener("click", function () {
                    window.location.pathname = dados.baseExtra + dados.idExtra;
                    
                })
                requisicao.querySelector(".info").classList.add("clicavel")
            }
        }
    }


    buscarRequisicoes();


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
        if (!Number.isFinite(Number(ultima.substring(1,ultima.length)))) {
        window.location.pathname = "requisicoes/ /0"
    }
}

function corrigirNome() {
    if (localStorage.getItem('busca') == 'cargos') {
        tituloP.textContent = "Pedidos de Titulação"
    } else if (localStorage.getItem('busca') == 'exclusao') {
        tituloP.textContent = "Pedidos de Exclusão"

    } else {
        tituloP.textContent = "Denúncias"

    }
}

function paginaAtual() {
    const url = window.location.href;
    const partes = url.split('/');
    let busca = partes.pop();
    return Number(busca) + 1;
}



document.addEventListener("DOMContentLoaded", iniciarTelaRequisicoes)