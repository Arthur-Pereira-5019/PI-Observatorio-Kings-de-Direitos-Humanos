async function iniciarPopupDecisao(callback) {
    const blur = document.querySelector("#blur-nova-decisao");


    blur.addEventListener("click", function() {
        blur.style.display = "none";
    })
}

document.addEventListener("DOMContentLoaded", iniciarPopupDecisao);