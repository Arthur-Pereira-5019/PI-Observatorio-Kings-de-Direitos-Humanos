async function iniciarPopupNovaDecisao(callback) {
    const aplicar = document.querySelector(".botao-geral");
    const blur = document.querySelector("#blur");

    aplicar.addEventListener("click", async function () {
        blur.style.display = "none";
        await callback();
        window.location.reload();
    })

    blur.addEventListener("click", function() {
        blur.style.display = "none";
    })
}

document.addEventListener("DOMContentLoaded", iniciarPopupNovaDecisao);