async function iniciarSobreEdit() {
    let textp = document.getElementById("textoPublicacao");

    fetch("http://localhost:8080/api/tf/1", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) {
                window.location.href = "http://localhost:8080/tela_inexistente"
            }
            return res.json();
        })
        .then(async (data) => {
            let elementoSurpresa = document.createElement("div");
            let imgs;
            elementoSurpresa.innerHTML = data.text;
            imgs = elementoSurpresa.querySelectorAll("img");
            for (const i of imgs) {
                i.src = await carregarSrc(i.dataset.db_id);
            }
            textp.innerHTML = elementoSurpresa.innerHTML;
        })

    document.querySelector("#botaoCancelar").addEventListener("click", function() {
        window.location.pathname = "/sobre"
    })
    document.querySelector("#botaoSalvar").addEventListener("click", function() {
        window.location.pathname = "/sobre"
    })
}

document.addEventListener("DOMContentLoaded", iniciarSobreEdit)