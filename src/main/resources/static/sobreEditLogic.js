async function iniciarSobreEdit() {
    let textp;

    await fetch("http://localhost:8080/api/tf/1", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) {
                window.location.pathname = "telaInexistente"
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
    document.querySelector("#botaoResetar").addEventListener("click", function () {
        document.getElementById("textoPublicacao").innerHTML = "<span class=\"rt_default rt_geral\">&#8203;</span>"
        
    })
    document.querySelector("#botaoPublicar").addEventListener("click", function () {
        let textoSobre = textp.innerHTML;

        if (textoSobre.length < 80) {
            let result = confirm("Texto curto ou nulo submetido, deseja prosseguir com a alteração?.");
            if(!result) {
                return;
            }
        }

        const imagens = textoPublicacao.querySelectorAll("img")
        imagens.forEach((i) => {
            i.src = "";
        })

        textoSobre = textp.innerHTML;

        const requestBody = {
            id: 1,
            text: textoSobre
        };


        fetch("http://localhost:8080/api/tf/criar", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) return res.json();
                alert("Sobre atualizado com sucesso!")
                window.location.pathname = "sobre"
            })
            .then(data => {
                alert(data.mensagem)
            })
    })
}

async function carregarSrc(id) {
        try {
            let response = await fetch("http://localhost:8080/api/imagem/" + id, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' }
            })
            if (!response.ok) throw new Error("Erro no servidor");

            let data = await response.json();
            return "data:image/" + data.tipoImagem + ";base64," + data.imagem;
        } catch (err) {
            console.error(err);
            return null;
        };
    }

document.addEventListener("DOMContentLoaded", iniciarSobreEdit)