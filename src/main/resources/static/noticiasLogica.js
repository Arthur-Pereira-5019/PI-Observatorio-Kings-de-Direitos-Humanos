let botaoNovaPostagem;
let btnDireito;
let btnEsquerdo;
let btnLonge;
let btnPrimeiro;
let btnCampo;

async function iniciarNoticias() {
    consertarUrl()

    inputBusca = document.getElementById("campoPesquisa")
    botaoNovaPostagem = document.getElementById("botao-moderador")

    btnDireito = document.getElementById("botaodireito");
    btnDireito.addEventListener("click", function () {
        moverUrl(1)
    })

    btnAtual = document.getElementById("botaoatual");
    btnAtual.textContent = paginaAtual()

    btnPrimeiro = document.getElementById("botaoprimeiro");
    btnPrimeiro.addEventListener("click", async function () {
        moverUrl(-1 * (paginaAtual() - 1))
    })

    btnEsquerdo = document.getElementById("botaoesquerdo");
    btnEsquerdo.addEventListener("click", function () {
        moverUrl(-1)
    })
    let url = window.location.href;
    let partes = url.split('/');
    if (partes.pop() == 0) {
        btnEsquerdo.remove()
        btnPrimeiro.remove()
    }

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

    fetch("http://localhost:8080/api/user", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) {
                carregarComentarios()
                throw new Error("Erro no servidor");

            }
            return res.json();
        })
        .then(data => {
            if (data.estadoDaConta == "PADRAO" || data.estadoDaConta == "SUSPENSO") {
                document.querySelector("#containerbtn").remove()
                moderador = true;
            } else if (data.estadoDaConta == "ADMINISTRADOR" || data.estadoDaConta == "ESPECIALISTA") {
                document.querySelector("#containerbtn").style.display = "flex";
                document.querySelector("#containerbtn").addEventListener("click", function () {
                    window.location.pathname = "/noticias_agregadas"
                })
            }
        })
        .catch(err => console.error(err));

    const requestBody = {
        parametro: "dataDaPostagem",
        ascending: false
    };

    async function gerarNoticias() {
        url = window.location.href;
        partes = url.split('/');
        let busca2 = "/" + partes.pop();
        let busca = "/" + partes.pop() + " noticia";
        buscaf = busca + busca2

        fetch("http://localhost:8080/api/noticia/listar_noticias" + buscaf, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                const primeiroPost = document.getElementById('noticiaBase');
                const containerDireita = document.getElementById("noticias-direita");
                const containerEsquerda = document.getElementById("noticias-esquerda");

                if (data.resultado.length === 0) {
                    primeiroPost.remove()
                    btnLonge.textContent = paginaAtual();
                    let path = window.location.pathname
                    if (path != "/noticias/%20/0") {
                        alert("Nenhum resultado encontrado!")
                        window.location.pathname = "/noticias/ /0"
                    }
                    btnDireito.remove()
                    btnLonge.remove()
                } else {
                    btnLonge.textContent = paginaAtual() + Math.ceil(data.proximosIndexes / 16);
                    if (Number(paginaAtual()) == Number(btnLonge.textContent)) {
                        btnDireito.remove()
                        btnLonge.remove()
                    }
                    data.resultado.forEach((post, index) => {
                        if (index == 0) {
                            construirNoticia(primeiroPost, post)
                        } else {
                            const noticia = primeiroPost.cloneNode(true);
                            construirNoticia(noticia, post)
                            if (index % 2 == 0) {
                                containerDireita.appendChild(noticia)
                            } else {
                                containerEsquerda.appendChild(noticia)
                            }
                        }
                    });
                }
                if (!document.body.contains(btnEsquerdo) && !document.body.contains(btnDireito)) {
                    btnCampo.remove()
                }
            })
            .catch(err => console.error(err));

        function construirNoticia(noticia, dados) {
            if (dados.externa) {
                console.log(dados)
                noticia.querySelector(".tituloNoticia").textContent = dados.titulo
                noticia.querySelector(".autor").textContent = dados.nomeAutorNoticiaExterna
                if (dados.linkCapaExterna) {
                    noticia.querySelector(".capa").src = dados.linkCapaExterna
                } else {
                    noticia.querySelector(".capa").src = "/imagens/noticia-icon.png"
                }
                noticia.querySelector(".capa").addEventListener("click", function () {
                    window.location.href = "/" + dados.linkNoticiaExterna
                })
                noticia.querySelector(".tituloNoticia").addEventListener("click", function () {
                    window.location.href = "/" + dados.linkNoticiaExterna
                    
                })
                if (dados.linkIconDonoExterno) {
                    noticia.querySelector(".logoObservatorio").src = dados.linkIconDonoExterno
                }
                return;
            }
            noticia.querySelector(".tituloNoticia").textContent = dados.titulo
            noticia.querySelector(".autor").textContent = dados.nomeAutor
            if (dados.capa.imagem == "" || dados.capa.tipoImagem) {
                noticia.querySelector(".capa").src = "/imagens/noticia.png";
            } else {
                noticia.querySelector(".capa").src = "data:image/" + dados.capa.tipoImagem + ";base64," + dados.capa.imagem;
            }
            noticia.addEventListener("click", function () {
                window.location.pathname = "publicacao/" + dados.idPostagem
            })
        }
    }


    gerarNoticias();

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
    if (!Number.isFinite(Number(ultima.substring(1, ultima.length)))) {
        window.location.pathname = "noticias/ /0"
    }
}

function paginaAtual() {
    const url = window.location.href;
    const partes = url.split('/');
    let busca = partes.pop();
    return Number(busca) + 1;
}



document.addEventListener("DOMContentLoaded", iniciarNoticias)