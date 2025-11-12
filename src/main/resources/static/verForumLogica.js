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
            const dataForum = document.getElementById("data-forum-forum").textContent = data.dataCriacao


        })

}

document.addEventListener("DOMContentLoaded", iniciarVerForum);