async function carregarHTML(id, url, cssFile) {
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
    await carregarHTML("editarPerfil", "/popupEditarPerfil", "configuracaoUsuarioPopupStyle.css");
        const fundoPopupConfigUser = document.getElementById("posPopUpConfigUser");
        if (fundoPopupConfigUser) fundoPopupConfigUser.style.display = "none";

        const botaoAbrirConfigUser = document.getElementById("btnConfigUser");
        

    if(botaoAbrirConfigUser && fundoPopupConfigUser){
        botaoAbrirConfigUser.addEventListener("click", ()=>{
            fundoPopupConfigUser.style.display = "flex";
            console.log("oie")
        })

        nomeAlterar = document.getElementById("campoNomeConfigUser")
        senhaAlterar = document.getElementById("campoSenhaConfigUser")
        senhaConfAlterar = document.getElementById("campoConfSenhaConfigUser")
        telefoneAlterar = document.getElementById("campoTelefoneConfigUser")

    const btnDeleteUser = document.getElementById("btnAplicar")
    btnDeleteUser.addEventListener("click", ()=>{

        const novoPut = {
            telefone: telefoneAlterar.value,
            nome: nomeAlterar.value,
            notificacoesPorEmail: false,
            senha: senhaAlterar.value
            
        }
        console.log(senhaAlterar.value)

        fetch("http://localhost:8080/api/user/atualizarUsuario", {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'}, 
            body: JSON.stringify(novoPut) 
                
        })
          .then(res => {
                    if (!res.ok) throw new Error("Erro no servidor");
                    return res.json();
                })
                .then(() => {
                    telefoneAlterar.value = ""
                    nomeAlterar.value = ""
                    senhaAlterar.value = ""
                    senhaConfAlterar.value = ""

                    fundoPopupConfigUser.style.display = "none";
                    
                })
                .catch(err => console.error(err));
                
    })

        fundoPopupConfigUser.addEventListener("click", (e) => {
        if (e.target === fundoPopupConfigUser) {
            fundoPopupConfigUser.style.display = "none";
        }
    
        const fundoPopupDelete = document.getElementById("posPopUpDelete");
        const btnDeleteUser = document.getElementById("btnDelete")

        if (btnDeleteUser && fundoPopupDelete) {
            btnDeleteUser.addEventListener("click", () => {
                fundoPopupDelete.style.display = "flex";
            });
        }

        const btnRequisitar = document.getElementById("btnRequisitar")


    });

    }

}

document.addEventListener("DOMContentLoaded", iniciarConfigUser);