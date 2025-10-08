async function iniciarPerfil() {
    const nomeUsuario = document.getElementById("nomeUsuario");

    const path = window.location.pathname;
    const id = path.split("/").pop();

    if (!id || isNaN(id)) {
        console.error("ID do usuario invalido na URL");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/user/" + id, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (!response.ok) {
            throw new Error('Erro na requisição');
        }

        const data = await response.json();

        nomeUsuario.textContent = data.nome;
        const cargoAtual = document.getElementById(data.estadoDaConta)
        cargoAtual.style.display = "flex"


        const btnAtvUser = document.getElementById("btnAtvUser")
        const btnAddCargo = document.getElementById("btnAddCargo")
       
        if(data.estadoDaConta == "MODERADOR" || data.estadoDaConta == "ADMINISTRADOR" || data.estadoDaConta == "ESPECIALISTA"){
            btnAtvUser.style.display = "flex"
            btnAddCargo.style.display = "flex"
        }
        

    } catch (error) {
        console.error('Erro:', error);
    }
}


document.addEventListener("DOMContentLoaded", iniciarPerfil);