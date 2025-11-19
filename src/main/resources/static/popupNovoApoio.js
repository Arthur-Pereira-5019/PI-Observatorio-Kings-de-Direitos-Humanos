async function iniciarNovoApoio(url, idApoioExistente) {
    const nomeInst = document.getElementById("label-nome-instituicao")
    const sobreInst = document.getElementById("ta-sobre-instituicao")
    const twitter = document.getElementById("campo-twitter")
    const insta = document.getElementById("campo-insta")
    const wpp = document.getElementById("campo-whats")
    const endereco = document.getElementById("campo-endereco")
    const siteInst = document.getElementById("campo-site")
    const likIn = document.getElementById("campo-linkedin")
    const entrada = document.getElementById("campo-linkedin")

   const btnCriarApoio = document.getElementById("botao-salvar-instituicao")
    const excluirApoio = document.getElementById("lixo-deletar")

    const blur = document.querySelector("#blurNovoApoio");
    const container_decisao = document.querySelector("#fundoNovoApoio");

    blur.display = "flex"
    container_decisao.display = "flex"


    blur.addEventListener("click", sumir)

    function sumir() {
        container_decisao.remove();
        blur.remove();
    }

    if (idApoioExistente) {
        url = url + idApoioExistente
        excluirApoio.remove()
    } else {
        excluirApoio.addEventListener("click", function () {
            sumir()
        })
    }

    entrada.addEventListener("input", input_capa);
    

    function input_capa() {
        capaPreview = document.querySelector(".icon-user");

        const imagemSubmetida = entrada.files[0];

        if (imagemSubmetida && imagemSubmetida.name.endsWith(".png") || imagemSubmetida.name.endsWith(".jpg") || imagemSubmetida.name.endsWith(".jpeg")) {
            const reader = new FileReader();

            reader.onload = (e) => {
                const base64StringWithPrefix = e.target.result;
                requestBody = {
                    imageBase64: base64StringWithPrefix.replace(base64StringWithPrefix.substring(0, base64StringWithPrefix.indexOf(",") + 1), ""),
                    descricao: "Foto de perfil de " + data.nome,
                    titulo: "Foto de perfil de " + data.nome
                }

                fetch("http://localhost:8080/api/user/atualizar_imagem", {
                    method: 'PUT',
                    body: JSON.stringify(requestBody),
                    headers: { 'Content-Type': 'application/json' },
                })
                    .then(res => {
                        if (!res.ok) return res.json();
                        alert("Imagem adicionada com sucesso!")
                        window.location.reload();
                    })
                    .then(d => {
                        alert(d.mensagem)
                    })
            };

            reader.readAsDataURL(imagemSubmetida);
        }
    }

 


    btnCriarApoio.addEventListener("click", function () {

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

        fetch(url, {
            method: idApoioExistente ? "PUT" : "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(novoApoio)
        })
            .then(res => {
                if (!res.ok) {
                    return res.json();
                }
                if (!idApoioExistente) {
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