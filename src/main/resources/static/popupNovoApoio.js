async function carregarHTMLNovoApoio(id, url, cssFile) {
    const response = await fetch(url);
    const data = await response.text();
    document.getElementById(id).innerHTML = data;

    if (cssFile) {
        const link = document.createElement("link");
        link.rel = "stylesheet";
        link.href = cssFile;
        document.head.appendChild(link);
    }
}
async function iniciarConfigUser() {
    await carregarHTMLNovoApoio("novoApoio", "/novo_apoio", "novoApoioStyle.css");


    const nomeInst = document.getElementById("label-nome-instituicao")
    const sobreInst = document.getElementById("ta-sobre-instituicao")
    const twitter = document.getElementById("campo-twitter")
    const insta = document.getElementById("campo-insta")
    const wpp = document.getElementById("campo-whats")
    const endereco = document.getElementById("campo-endereco")
    const siteInst = document.getElementById("campo-site")
    const likIn = document.getElementById("campo-linkedin")


    const btnCriarApoio = document.getElementById("botao-salvar-instituicao")

    btnCriarApoio.addEventListener("click", function () {

        console.log(wpp.value)

        if (nomeInst.value.trim() == "" || sobreInst.value.trim() == "") {
            alert("preencha ao menos o campo de Nome e de Sobre!")
            return

        }

        const novoApoio = {
            nomeInstituicao: nomeInst.value,
            sobreInstituicao: sobreInst.value,
            twitter: twitter.value,
            telefone: wpp.value,
            localizacao: endereco.value,
            site: siteInst.value,
            instagram: insta.value,
            linkedin: likIn.value,
            ImagemBase64: ""

        };

        fetch("http://localhost:8080/api/apoio/", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(novoApoio)
        })
            .then(res => {
                if (!res.ok) {
                    return res.json();
                }
                window.location.reload()
            }).then(data => {

            })
            .catch(err => console.error(err));

    })

}

document.addEventListener("DOMContentLoaded", carregarHTMLNovoApoio);