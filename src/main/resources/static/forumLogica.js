const btnCriarForum = document.getElementById("botao-addForum-tela-foruns")

btnCriarForum.addEventListener("click", function () {
        window.location.href = "http://localhost:8080/novo_forum";
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
                const primeiroPost = document.querySelector('.forum-tela-foruns');
                const containerGeral = document.getElementById("container-all-foruns");
                console.log(data);
                
                if (data.resultado.length === 0) {
                    primeiroPost.querySelector('.forum-tela-foruns').remove()
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
            publicacao.querySelector(".tituloForum").textContent = dados.titulo
            publicacao.querySelector(".autor").textContent = dados.autor
            publicacao.querySelector(".dataForum").textContent = dados.data
            publicacao.addEventListener("click", function () {
                window.location.href = "http://localhost:8080/forum/" + dados.idPostagem
            })
        }
    }


    gerarPublicacoes();