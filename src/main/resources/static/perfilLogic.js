
async function iniciarPerfil() {
    const nomeUsuario = document.getElementById("nomeUsuario");

    const path = window.location.pathname;
    const id = path.split("/").pop();

    if (!id || isNaN(id)) {
        console.error("ID do usuario invalido na URL");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/user/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            window.location.pathname = "/telaInexistente"
        }

        const data = await response.json();

        nomeUsuario.textContent = data.nome;
        const cargoAtual = document.getElementById(data.estadoDaConta)
        cargoAtual.style.display = "flex"

        const btnAtvUser = document.getElementById("btnAtvUser")
        const btnRequisitar = document.getElementById("btnRequisitar")
        const btnConfigUser = document.getElementById("btnConfigUser")
        const btnAddCargo = document.getElementById("btnAddCargo")

        if(data.proprio == false) {
            btnConfigUser.remove()
            btnRequisitar.remove()
        }

        if (data.estadoDaConta == "MODERADOR" || data.estadoDaConta == "ADMINISTRADOR") {
            btnAtvUser.style.display = "flex"
            btnAddCargo.style.display = "flex"
        }

        btnAddCargo.addEventListener("click",function() {

        })

        async function gerarPublicacoes() {
            fetch("http://localhost:8080/api/postagem/usuario/" + id, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' },
            })
                .then(res => {
                    if (!res.ok) throw new Error("Erro no servidor");
                    return res.json();
                })
                .then(data => {
                    const primeiroPost = document.querySelector('.container-geral-publicacoes');
                    const containerGeral = document.getElementById("container-lista");

                    if (data.length === 0) {
                        primeiroPost.querySelector('.container-baixo').remove()
                    } else {
                        data.forEach((post, index) => {
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
                novoT = dados.titulo
                if(novoT.length > 32) {
                    novoT = novoT.substring(0,32) + "..."
                }
                publicacao.querySelector(".titulo-publicacao").textContent = novoT
                publicacao.querySelector(".autor").textContent = dados.autor
                publicacao.querySelector(".data").textContent = dados.data
                publicacao.querySelector(".paragrafo").innerHTML = dados.texto
                if (dados.capa.imagem == "" || dados.capa.tipoImagem) {
                    publicacao.querySelector(".imagem").src = "/imagens/publicacao.png";
                } else {
                    publicacao.querySelector(".imagem").src = "data:image/" + dados.capa.tipoImagem + ";base64," + dados.capa.imagem;
                }
                publicacao.addEventListener("click", function () {
                    window.location.href = "http://localhost:8080/publicacao/" + dados.idPostagem
                })
            }
        }

        

        gerarPublicacoes()


    } catch (error) {
        console.error('Erro:', error);
    }


}


document.addEventListener("DOMContentLoaded", iniciarPerfil);