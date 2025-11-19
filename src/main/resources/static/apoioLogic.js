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
            if (jsFile === "/popupNovoApoio.js.js" && typeof iniciarNovoApoio === "function") {
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
                    anexarHTMLExterno("/novo_apoio", "/novoApoioStyle.css", "/popupNovoApoio.js", "http://localhost:8080/api/apoio", null)
                })
            }

        })
        .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded", iniciarTelaApoio);