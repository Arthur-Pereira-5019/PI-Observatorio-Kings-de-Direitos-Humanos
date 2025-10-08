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

    }

    /*if (botaoAbrirDeleteUser && fundoPopupDeleteUser) {
        botaoAbrirDeleteUser.addEventListener("click", () => {
            fundoPopupConfigUser.style.display = "none";
            fundoPopupDeleteUser.style.display = "flex";
        });
    }

    fundoPopupConfigUser.addEventListener("click", (e) => {
        if (e.target === fundoPopupConfigUser) {
            fundoPopupConfigUser.style.display = "none";
        }
    });
*/

}

document.addEventListener("DOMContentLoaded", iniciarConfigUser);