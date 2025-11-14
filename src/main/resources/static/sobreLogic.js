async function iniciarSobre() {
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

    fetch("http://localhost:8080/api/user/apresentar", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (res.ok) return res.json();
        })
        .then(async (data) => {
            if (data.edc === "ADMINISTRADOR") {
                
            }
        })
}

document.addEventListener("DOMContentLoaded", iniciarSobre)