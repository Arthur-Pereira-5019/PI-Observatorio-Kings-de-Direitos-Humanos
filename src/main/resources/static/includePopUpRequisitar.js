async function carregarHTMLRequisitar(id, url, cssFile) {
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

async function iniciarRequisitar() {

    await carregarHTMLRequisitar("requisitar", "/popupRequisitar", "/requisitarPopupStyle.css");

    const btnRequisitar = document.getElementById("btnRequisitar")
    const fundoPopupRequisitar = document.getElementById("posPopUpRequisitar")

    btnRequisitar.addEventListener("click", function(){
        fundoPopupRequisitar.style.display = "flex"

    })
   

}

document.addEventListener("DOMContentLoaded", iniciarRequisitar);