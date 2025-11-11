const btnCriarForum = document.getElementById("botao-addForum-tela-foruns")

btnCriarForum.addEventListener("click", function () {
    window.location.href = "http://localhost:8080/novo_forum";
})




fetch("http://localhost:8080/api/forum/1", {
    method: "GET",
    headers: {
        "Content-Type": "application/json"
    },
   
})
.then(res => {
    if (!res.ok) throw new Error("Erro na requisição");
    return res.json();
})
.then(dados => {
    console.log("Retorno completo:", dados);

    const tituloForum = document.getElementById("tituloForum")
    const dataForum = document.getElementById("data-forum-tela-foruns")
    const respostasForum = document.getElementById("respostasForum")
    const dataUltimaAtualizacao = document.getElementById("dataUltimaAtualizacao")

    tituloForum.innerHTML = dados.titulo
    dataForum.innerHTML = dados.dataCriacao
    respostasForum.innerHTML = dados.respostas
    dataUltimaAtualizacao.innerHTML = dados.dataUltimaAtualizacao

})
.catch(err => console.error("Erro:", err));