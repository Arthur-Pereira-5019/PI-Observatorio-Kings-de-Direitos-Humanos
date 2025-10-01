async function iniciar() {
    let file = document.getElementById("arquivo");
    let campoDescricao = document.getElementById("desc");
    let campoTitulo = document.getElementById("titulo_nova_imagem");

    function file_input() {
        const entrada = document.getElementById("arquivo");
        const canva = document.getElementById("canva");
        const inserir = document.getElementById("inserir");

        const arquivo = entrada.files[0];

        if (arquivo && arquivo.name.endsWith(".png") || arquivo.name.endsWith(".jpg")) {
            const reader = new FileReader();

            reader.onload = (e) => {
                const base64StringWithPrefix = e.target.result;
                canva.src = base64StringWithPrefix;
            };

            reader.readAsDataURL(arquivo);
        }
    }

    function img_insert() {
        let imageData = canva.src;
        let endPosition = imageData.indexOf(",");
        endPosition++;
        imageData = imageData.replace(imageData.substring(0, endPosition), "");

        const requestBody = {
            imageBase64: imageData,
            descricao: campoDescricao.value,
            titulo: campoTitulo.value
        };

        fetch("http://localhost:8080/api/imagem", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                return res.json();
            })
            .then(data => {
            })
            .catch(err => console.error(err));
    }

    file.addEventListener("input", file_input);
    inserir.addEventListener("click", img_insert);

    
}

document.addEventListener("DOMContentLoaded", iniciar);
