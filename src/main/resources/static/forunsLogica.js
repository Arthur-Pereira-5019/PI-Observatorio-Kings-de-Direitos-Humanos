let btnDireito;
let btnEsquerdo;
let btnLonge;
let btnPrimeiro;
let btnCampo;

async function iniciarForunsLogica() {
    const btnCriarForum = document.getElementById("botao-addForum-tela-foruns")

    btnCriarForum.addEventListener("click", function () {
        window.location.pathname = "novo_forum";
    })

     fetch("http://localhost:8080/api/user", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(async res => {
            if (res.ok) {
                const text = await res.text();

                if (text.trim() === "null") {
                    btnCriarForum.remove()
                } else {
                    return JSON.parse(text)
                }
            } else {
                btnCriarForum.remove()
            }
        })
        .then(data => {
            if (data.estadoDaConta == "SUSPENSO") {
                btnCriarForum.remove()
            }
        })
        .catch(err => console.error(err));


    consertarUrl()
    btnPrimeiro = document.getElementById("botaoprimeiro");
    btnPrimeiro.addEventListener("click", function () {
        moverUrl(-1 * (paginaAtual() - 1))
    })

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

    async function gerarForuns() {
        const url = window.location.href;
        const partes = url.split('/');
        let busca2 = "/" + partes.pop();

        let busca = partes.pop();
        buscaf = busca + busca2
        
        fetch("http://localhost:8080/api/forum/listar_publicacoes/" + buscaf, {
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
                if (data.resultado.length === 0) {
                    primeiroPost.remove()
                    alert("Nenhum resultado encontrado!")
                    btnLonge.textContent = paginaAtual();
                    let path = window.location.pathname
                    if (path != "/foruns/%20/0") {
                        alert("Nenhum resultado encontrado!")
                        window.location.pathname = "/foruns/ /0"
                    }
                    btnDireito.remove()
                    btnLonge.remove()
                    btnCampo.remove()
                } else {
                    btnLonge.textContent = paginaAtual() + Math.ceil(data.proximosIndexes % 10);
                    if (Number(paginaAtual()) == Number(btnLonge.textContent)) {
                        btnDireito.remove()
                        btnLonge.remove()
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
                if (!document.body.contains(btnEsquerdo) && !document.body.contains(btnDireito)) {
                    btnCampo.remove()
                }

            })
            .catch(err => console.error(err));

        function construirForum(forum, dados) {
            forum.querySelector(".tituloForum").textContent = dados.titulo
            forum.querySelector(".tituloForum").addEventListener("click", function () {
                window.location.pathname = "/forum/" + dados.idForum

            })
            forum.querySelector(".autorForum").textContent = dados.autor.nome
            forum.querySelector(".autorForum").addEventListener("click", function () {
                window.location.pathname = "usuario/" + dados.autor.id
            })
            forum.querySelector(".dataForum").textContent = dados.dataCriacao
            forum.querySelector(".nRespostas").textContent = "Respostas: " + dados.respostas
            forum.querySelector(".nRespostas").textContent = "Respostas: " + dados.respostas
            forum.querySelector(".ultAtt").textContent = "Última Atualização: " + dados.ultimaAtualizacao
            let imagem = forum.querySelector("#foto-user-forum-tela-foruns")
            imagem.addEventListener("click", function () {
                window.location.pathname = "usuario/" + dados.autor.id
            })
            if (dados.autor.foto == null) {
                imagem.src = "/imagens/perfilIcon.png";
            } else if (dados.autor.foto.imagem == "") {
                imagem.src = "/imagens/perfilIcon.png";
            } else {
                imagem.src = "data:image/" + dados.autor.foto.tipoImagem + ";base64," + dados.autor.foto.imagem;
            }
        }

    }

    gerarForuns()

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
            window.location.pathname = "foruns/ /0"
        }
    }

    function paginaAtual() {
        const url = window.location.href;
        const partes = url.split('/');
        let busca = partes.pop();
        return Number(busca) + 1;
    }
}

document.addEventListener("DOMContentLoaded", iniciarForunsLogica)