async function anexarHTMLExterno(url, cssFile, jsFile, durl, idApoio) {
    const response = await fetch(url);
    const data = await response.text()
    const novoObjeto = document.createElement("div");
    document.body.appendChild(novoObjeto)
    novoObjeto.innerHTML = data;

    if (cssFile) {
        anexarCss(cssFile)
    }

    if (jsFile) {
        let script = document.createElement("script");
        script.src = jsFile;
        script.onload = () => {
            if (jsFile === "/popupNovoApoio.js" && typeof iniciarNovoApoio === "function") {
                iniciarNovoApoio(durl, idApoio);
            }
        };
        document.body.appendChild(script);
    }
}

async function iniciarTelaApoio() {
    const addApoio = document.getElementById("add-acolhimento")
    const primeiroApoioD = document.querySelector(".container-geral-instituicao-direita")
    const primeiroApoioE = document.querySelector(".container-geral-instituicao-esquerda")
    const primeiroApoioDC = primeiroApoioD.cloneNode(true)
    const primeiroApoioEC = primeiroApoioE.cloneNode(true)
    const containerLista = document.querySelector(".container-resto-tela-apoio")

    let isAdmin = false;

    try {
        const userResponse = await fetch("http://localhost:8080/api/user", {
            headers: { 'Content-Type': 'application/json' },
        });
        
        if (userResponse.ok) {
            const userData = await userResponse.json();
            if (userData.estadoDaConta === "ADMINISTRADOR") {
                isAdmin = true;
                
                addApoio.style.display = "flex"
                addApoio.addEventListener("click", function () {
                    anexarHTMLExterno(
                        "/novo_apoio", 
                        "/novoApoioStyle.css", 
                        "/popupNovoApoio.js", 
                        "http://localhost:8080/api/apoio/", 
                        null
                    )
                })
            }
        }
    } catch (err) {
        console.error("Erro ao verificar usuário:", err);
    }

    try {
        const apoiosResponse = await fetch("http://localhost:8080/api/apoio/", {
            headers: { 'Content-Type': 'application/json' },
        });
        
        if (!apoiosResponse.ok) throw new Error("Erro no servidor");
        
        const data = await apoiosResponse.json();
        console.log(data);
        
        if (!data[0]) {
            primeiroApoioD.remove()
            primeiroApoioE.remove()
            return;
        } else if (!data[1]) {
            primeiroApoioE.remove()
        }

        data.forEach((d, i) => {
            if (i == 0) {
                construirPublicacao(primeiroApoioD, d, isAdmin)
            } else if (i == 1) {
                construirPublicacao(primeiroApoioE, d, isAdmin)
            } else {
                let novoElemento
                if (i % 2 == 0) {
                    novoElemento = primeiroApoioDC.cloneNode(true)
                    construirPublicacao(novoElemento, d, isAdmin)
                    containerLista.appendChild(novoElemento)
                } else {
                    novoElemento = primeiroApoioEC.cloneNode(true)
                    construirPublicacao(novoElemento, d, isAdmin)
                    containerLista.appendChild(novoElemento)
                }
            }
        });
    } catch (err) {
        console.error("Erro ao carregar apoios:", err);
    }

    function construirPublicacao(elemento, d, isAdmin) {
        elemento.querySelector("#infoApoio").textContent = d.sobreInstituicao
        elemento.querySelector("#nomeApoio").textContent = d.nomeInstituicao
        
        let imagem = elemento.querySelector("#foto-instituicao-tela-apoio")
        if (d.foto == null) {
            imagem.src = "/imagens/grupo.png";
            imagem.alt = "Grupo sem imagem."
            imagem.title = "Grupo sem imagem."
        } else if (d.foto.imagem == "") {
            imagem.src = "/imagens/grupo.png";
            imagem.alt = "Grupo sem imagem."
            imagem.title = "Grupo sem imagem."
        } else {
            imagem.src = "data:image/" + d.foto.tipoImagem + ";base64," + d.foto.imagem;
            imagem.alt = d.foto.descricaoImagem
            imagem.title = d.foto.descricaoImagem
        }

        const botaoEditar = elemento.querySelector(".editar-acolhimento")
        if (botaoEditar) {
            botaoEditar.setAttribute("data-apoio-id", d.idApoio)
            
            if (isAdmin) {
                botaoEditar.style.display = "flex"
                botaoEditar.addEventListener("click", function() {
                    const apoioId = this.getAttribute("data-apoio-id")
                    console.log("ID do Apoio:", apoioId)
                    anexarHTMLExterno(
                        "/novo_apoio", 
                        "/novoApoioStyle.css", 
                        "/popupNovoApoio.js", 
                        "http://localhost:8080/api/apoio/", 
                        apoioId
                    )
                })
            }
        }

        twitter(d.twitter)
        insta(d.instagram)
        linkedin(d.linkedin)
        site(d.site)
        telefone(d.telefone)
        endereco(d.localizacao)

        function preencher_elemento(seletor, link, href) {
            let elemento = getElemento(seletor);
            if (!link) {
                elemento.parentNode.remove()
                return;
            }
            elemento.textContent = link;
            elemento.title = href;
            elemento.href = href;
        }

        function twitter(link) {
            preencher_elemento("#twitterC", "@"+link, "https://x.com/"+link)
        }

        function insta(link) {
            preencher_elemento("#instaC", "@"+link, "https://www.instagram.com/"+link)
        }

        function linkedin(link) {
            preencher_elemento("#linkedinC", "Linkedin", link)
        }

        function site(link) {
            let nl = link;
            if(link.includes("https://")) {
                nl = link.replace("https://","")
            }
            preencher_elemento("#siteC", nl, link)
        }

        function telefone(link) {
            let nt = link;
            //Telefone Fixo ou Celular
            if(link.length = 12) {
            nt = "+" + nt.substring(0,2) + " (" + nt.substring(2,4) +") "+nt.substring(4,8)+"-"+nt.substring(8,12);
            } else {
            nt = "+" + nt.substring(0,2) + " (" + nt.substring(2,4) +") "+nt.substring(4,9)+"-"+nt.substring(9,13);
            }
            preencher_elemento("#numeroC", nt, "tel:"+link)
        }
        
        function endereco(link) {
            preencher_elemento("#enderecoC",link,"https://www.google.com/maps/search/"+link)
        }

        function getElemento(seletor) {
            return elemento.querySelector(seletor)
        }
    }
}

document.addEventListener("DOMContentLoaded", iniciarTelaApoio);