async function iniciarSobre() {
    let textp = document.getElementById("textoPublicacao");

    fetch("http://localhost:8080/api/tf/1", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) {
                window.location.pathname = "telaInexistente"
            }
            return res.json();
        })
        .then(async (data) => {
            let elementoSurpresa = document.createElement("div");
            let imgs;
            elementoSurpresa.innerHTML = data.text;
            imgs = elementoSurpresa.querySelectorAll("img");
            for (const i of imgs) {
                await desserializarImagem(i);
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
            botao = document.querySelector("#edicao_post");
            if (data.edc === "ADMINISTRADOR") {
                botao.style.display = "flex"
                botao.addEventListener("click", function () {
                    window.location.pathname = "/sobre/edit"
                })
            } else {
                botao.remove();
            }
        })

}

 async function desserializarImagem(i) {
        let id = i.dataset.db_id
        try {
            let response = await fetch("http://localhost:8080/api/imagem/" + id, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' }
            })
            if (!response.ok) throw new Error("Erro no servidor");

            let data = await response.json();
            i.src = "data:image/" + data.tipoImagem + ";base64," + data.imagem;
            i.alt = data.decricaoImagem
            i.title = data.decricaoImagem
        } catch (err) {
            console.error(err);
            return null;
        };
    }

document.addEventListener("DOMContentLoaded", iniciarSobre)