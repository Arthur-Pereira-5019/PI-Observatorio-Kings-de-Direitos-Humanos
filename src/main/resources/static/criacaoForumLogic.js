const btnPublicarForum = document.getElementById("botaoPublicarForum")
const btnCancelarForum = document.getElementById("botaoCancelar")

const campoTitulo = document.getElementById("campoTitulo")
const textoPublicacao = document.getElementById("textoPublicacao")


btnPublicarForum.addEventListener("click", function () {


    let novoPost

    if (campoTitulo.value.trim() === "") {
        alert("Dê um título para o forum antes de publicá-lo.!")
        campoTitulo.focus()
        return
    }


    if (textoPublicacao.value.trim() === "") {
        alert("Escreva algo para iniciar a discussão!")
        textoPublicacao.focus()
        return
    }

    novoPost = {
        tituloForum: campoTitulo.value,
        textoForum: textoPublicacao.value,
    }
    fetch("http://localhost:8080/api/forum", {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(novoPost)

    })
        .then(res => {
            if (res.ok) {
                alert("Forum postado!")
                window.location.pathname = "foruns";
            }

        })

})

btnCancelarForum.addEventListener("click", function () {
    window.location.pathname = "foruns";

})
