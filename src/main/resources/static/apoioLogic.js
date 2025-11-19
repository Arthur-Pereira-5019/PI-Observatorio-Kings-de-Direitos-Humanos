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
        if (data.estadoDaConta == "ESPECIALISTA" || data.estadoDaConta == "ADMINISTRADOR") {
            editApoioD.style.display = "flex"
            editApoioE.style.display = "flex"
            addApoio.style.display = "flex"
            addApoio.addEventListener("click", function () {
               
            })
        }

    })
    .catch(err => console.error(err));