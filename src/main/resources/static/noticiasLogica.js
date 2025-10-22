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

    async function gerarNoticias() {
        const url = window.location.href;
        const partes = url.split('/');
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
                console.log(data);
                
                if (data.length === 0) {
                    primeiroPost.remove()
                    alert("Nenhum resultado encontrado!")
                    if (busca != "") {
                        inputBusca.value = ""
                        iniciarNoticias()
                    }
                } else {
                    data.forEach((post, index) => {
                        if (index == 0) {
                            construirNoticia(primeiroPost, post)
                        } else {
                            const noticia = primeiroPost.cloneNode(true);
                            construirNoticia(noticia,post)
                            if(index % 2 == 0) {
                                containerDireita.appendChild(noticia)
                            } else {
                                containerEsquerda.appendChild(noticia)
                            }
                        }
                    });
                }

            })
            .catch(err => console.error(err));

        function construirNoticia(noticia, dados) {
            noticia.querySelector(".tituloNoticia").textContent = dados.titulo
            noticia.querySelector(".autor").textContent = dados.nomeAutor
            if(dados.capa.imagem == "" || dados.capa.tipoImagem) {
                noticia.querySelector(".capa").src = "/imagens/noticia.png";
            } else {
                noticia.querySelector(".capa").src = "data:image/" + dados.capa.tipoImagem + ";base64," + dados.capa.imagem;
            }
            noticia.addEventListener("click", function () {
                window.location.href = "http://localhost:8080/publicacao/" + dados.idPostagem
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
    if (ultima === '/noticias') {
        window.location.href = "http://localhost:8080/noticias/ /0"
    }
}

async function paginaAtual() {
    const url = window.location.href;
    const partes = url.split('/');
    let busca = partes.pop();
    return Number(busca) + 1;
}



document.addEventListener("DOMContentLoaded", iniciarNoticias)