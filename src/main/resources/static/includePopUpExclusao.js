async function carregarHTMLExclusaoUser(id, url, cssFile) {
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
async function iniciarExclusaoUser() {
    await carregarHTMLExclusaoUser("excluirPerfil", "/popupDeleteUser", "/deleteUsuarioPopupStyle.css");

        const fundoPopupDelete = document.getElementById("posPopUpDelete");
        fundoPopupDelete.addEventListener("click", (e) => {
            fundoPopupDelete.style.display = "none";
            console.log("eba")

    });
        
    }

    document.addEventListener("DOMContentLoaded", iniciarExclusaoUser);