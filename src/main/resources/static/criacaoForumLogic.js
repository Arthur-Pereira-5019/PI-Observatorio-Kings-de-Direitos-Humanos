const btnPublicarForum = document.getElementById("botaoPublicarForum")
const btnCancelarForum = document.getElementById("botaoCancelar")

const campoTitulo = document.getElementById("campoTitulo")
const textoPublicacao = document.getElementById("textoPublicacao")


btnPublicarForum.addEventListener("click", function(){
    
    const novoPost = {
        tituloForum: campoTitulo.value,
        textoForum: textoPublicacao.value,
        
    }

    fetch("http://localhost:8080/api/forum", {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(novoPost)

    })
    
})
