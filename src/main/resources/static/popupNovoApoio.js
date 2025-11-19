async function iniciarNovoApoio(url, idApoioExistente) {
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

        if (nomeInst.value.trim() == "" || sobreInst.value.trim() == "") {
            alert("preencha ao menos o campo de Nome e de Sobre!")
            return

        }

        if(idApoioExistente) {
            url = url + idApoioExistente
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

        fetch(url, {
            method: idApoio ? "PUT" : "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(novoApoio)
        })
            .then(res => {
                if (!res.ok) {
                    return res.json();
                }
                if(!idApoio) {
                    alert("Novo apoio cadastrado com sucesso!")
                } else {
                    alert("Apoio atualizado com sucesso!")
                }
                window.location.reload()
            }).then(data => {

            })
            .catch(err => console.error(err));

    })

}

document.addEventListener("DOMContentLoaded", iniciarNovoApoio);