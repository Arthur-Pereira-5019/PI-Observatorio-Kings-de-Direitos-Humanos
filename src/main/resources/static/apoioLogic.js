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
    const editApoioD = document.getElementById("editar-acolhimento-direita")
    const editApoioE = document.getElementById("editar-acolhimento-esquerda")
    const addApoio = document.getElementById("add-acolhimento")
    const primeiroApoioD = document.querySelector(".container-geral-instituicao-direita")
    const primeiroApoioE = document.querySelector(".container-geral-instituicao-esquerda")
    const containerLista = document.querySelector(".container-resto-tela-apoio")

    fetch("http://localhost:8080/api/user", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })
        .then(data => {
            if (data.estadoDaConta == "ADMINISTRADOR") {
                editApoioD.style.display = "flex"
                editApoioE.style.display = "flex"
                addApoio.style.display = "flex"
                addApoio.addEventListener("click", function () {
                    anexarHTMLExterno("/novo_apoio", "/novoApoioStyle.css", "/popupNovoApoio.js", "http://localhost:8080/api/apoio/", null)
                })
            }
            

        })
        .catch(err => console.error(err));

        fetch("http//localhost:8080/api/apoio/"), {
                    headers: { 'Content-Type': 'application/json' },
        }.then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })
        .then(data => {
            if(!data[0]) {
                primeiroApoioD.remove()
                primeiroApoioE.remove()
                return;
            } else if(!data[1]) {
                primeiroApoioD.remove()
            }

            data.array.forEach(d, i => {
                if(i == 0) {
                    construirPublicacao(primeiroApoioE, d)
                } else if(i == 1) {
                    construirPublicacao(primeiroApoioD, d)
                } else {
                    let novoElemento
                    if(i % 2 != 0) {
                        novoElemento = document.cloneNode(primeiroApoioD)
                        construirPublicacao(novoElemento, d)
                    }
                }

            });

        })

        async function construirPublicacao(elemento, d) {
            elemento.querySelector(".infoApoio").textContent = d.sobreInstituicao
            elemento.querySelector(".nomeApoio").textContent = d.nomeInstituicao

            preencher_elemento("#twitterC",d.twitter)
            preencher_elemento("#instaC",d.instagram)
            preencher_elemento("#linkedinC",d.linkedin)
            preencher_elemento("#siteC",d.site)
            preencher_elemento("#numeroC",d.numero)
            preencher_elemento("#enderecoC",d.localizacao)
        }

        function preencher_elemento(seletor, link) {
            if(!link) {
                elemento.querySelector.remove()
            }
            elemento.querySelector(seletor).textContent = link;
            elemento.querySelector(seletor).href = link;
        }
}

document.addEventListener("DOMContentLoaded", iniciarTelaApoio);