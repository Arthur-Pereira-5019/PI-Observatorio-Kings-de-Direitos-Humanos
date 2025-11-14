async function iniciarSobreEdit() {
    let textp;

    await fetch("http://localhost:8080/api/tf/1", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) {
                window.location.href = "http://localhost:8080/tela_inexistente"
            }
            return res.json();
        })
        .then(async (data) => {
            textp = document.getElementById("textoPublicacao");
            let elementoSurpresa = document.createElement("div");
            let imgs;
            elementoSurpresa.innerHTML = data.text;
            imgs = elementoSurpresa.querySelectorAll("img");
            for (const i of imgs) {
                i.src = await carregarSrc(i.dataset.db_id);
            }
            textp.innerHTML = elementoSurpresa.innerHTML;
        })

    document.querySelector("#botaoCancelar").addEventListener("click", function () {
        window.location.pathname = "/sobre"
    })
    document.querySelector("#botaoPublicar").addEventListener("click", function () {
        const textoSobre = textp.innerHTML;

        if (textoSobre.length < 80) {
            alert("Digite o texto do Sobre antes de salvá-lo")
            return;
        }

        const imagens = textoPublicacao.querySelectorAll("img")
        imagens.forEach((i) => {
            i.src = "";
        })

        const requestBody = {
            id: 0,
            texto: textoSobre
        };


        fetch("http://localhost:8080/api/tf/criar", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
                alert("Sobre atualizado com sucesso!")
                window.location.href = "http://localhost:8080/sobre/" + "0"
            })
    })
}

document.addEventListener("DOMContentLoaded", iniciarSobreEdit)