async function iniciarPopupDecisao(url) {
    const blur = document.querySelector("#blur-decmod");
    const container_dm = document.querySelector("#container_dm");
    const campoM = document.querySelector("#motivacao");
    const campoD = document.querySelector("#data");
    const campoR = document.querySelector("#responsavel");

    blur.addEventListener("click", function() {
        ocultarDecMod();
    })

    function ocultarDecMod() {
        blur.remove();
        container_dm.remove()
    }

    fetch(url, {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) {
                throw new Error("Erro no servidor");
            }
            return res.json();
        })
        .then(data => {
            campoM.textContent += data.motivacao;
            campoD.textContent += data.data;
            campoR.textContent += data.nomeModerado;
        })
        .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded", iniciarPopupDecisao);