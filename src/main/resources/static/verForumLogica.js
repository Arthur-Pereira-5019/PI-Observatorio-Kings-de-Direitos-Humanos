async function iniciarVerForum() {

    const tituloForum = document.getElementById("tituloForum")
    const dataForum = document.getElementById("dataForum")


    const path = window.location.pathname;
    const id = path.split("/").pop();

    console.log("porra: " + id)

    await fetch("http://localhost:8080/api/forum/" + id, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })

        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            return res.json();
        })

        .then(data => {
            console.log(data)
            const tituloForum = document.getElementById("titulo-forum-forum").textContent = data.titulo
            const dataAtualizacao = document.getElementById("data-comentario-forum").textContent = data.ultimaAtualizacao
            const nome = document.getElementById("nome-user-discussao-forum").textContent = data.autor.nome
            const textoForum = document.getElementById("textoForum").textContent = data.textoForum
            const dataForum = document.getElementById("data-comentario-forum").textContent = data.dataCriacao
            const limiteCaracteres = document.getElementById("limite-caracteres-comentario-forum").textContent = data.textoForum.length + "/512"


        })

}

document.addEventListener("DOMContentLoaded", iniciarVerForum);