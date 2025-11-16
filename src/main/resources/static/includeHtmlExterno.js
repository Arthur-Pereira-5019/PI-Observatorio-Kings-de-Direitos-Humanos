const iconButton = document.getElementById("iconButton");



async function carregarHTMLExterno(id, url, cssFile, jsFile) {
    alvo = document.getElementById(id);
    if (alvo == null | alvo == undefined) {
        throw "Alvo não encontrado"
    }
    const response = await fetch(url);
    const data = await response.text();
    alvo.innerHTML = data;

    if (cssFile) {
        anexarCss(cssFile)
    }

    if (jsFile) {
        anexarJs(jsFile)
    }
}

async function anexarCss(cssFile) {
    let link = document.createElement("link");
    link.rel = "stylesheet";
    link.href = cssFile;
    document.head.appendChild(link);
}

async function anexarJs(jsFile) {
    let script = document.createElement("script");
    script.src = jsFile;
    script.onload = () => {
        if (jsFile === "/cabecalhoLogic.js" && typeof iniciarCabecalho === "function") {
            iniciarCabecalho();
        } else if (jsFile === "/includePopUpRegistro.js" && typeof iniciarCabecalho === "function") {
            iniciarPopupRegistro();
        } else if (jsFile === "/includePopupLogin.js" && typeof iniciarCabecalho === "function") {
            iniciarPopupLogin();
        } else if (jsFile === "/richTextLogic.js" && typeof iniciarRichText === "function") {
            iniciarRichText();
        } else if (jsFile === "/sobreEditLogic.js" && typeof iniciarSobreEdit === "function") {
            iniciarSobreEdit();
        }
    };
    document.body.appendChild(script);
}


async function iniciarHtmlExterno() {
    await anexarCss("/posicionamentoPopups.css")
    await anexarCss("/richTextStyle.css")
    await carregarHTMLExterno("header", "/cabecalho", "/cabecalhoStyle.css", "/cabecalhoLogic.js");
    await carregarHTMLExterno("footer", "/rodape", "/rodapeStyle.css");
    await carregarHTMLExterno("login", "/popupLogin", "/popUpLoginStyle.css", "/includePopupLogin.js");
    await carregarHTMLExterno("registro", "/popupRegistro", "/popUpRegistroStyle.css", "/includePopUpRegistro.js");
    try {
        await carregarHTMLExterno("richtexteditor", "/rte", "/richTextEditorStyle.css", "/richTextLogic.js");
    } catch (e) { }
    if(window.location.pathname.includes("sobre/edit")) {
        await anexarJs("/sobreEditLogic.js")
    }


    if (iconButton) {
        iconButton.addEventListener("click", function () {
            fetch("http://localhost:8080/api/user", {
                method: "GET",
                headers: { "Content-Type": "application/json" },


            })


        });
    }

}


document.addEventListener("DOMContentLoaded", iniciarHtmlExterno);