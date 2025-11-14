let file = document.getElementById("arquivo");
let campoDescricao = document.getElementById("desc");
let campoTitulo = document.getElementById("titulo_nova_imagem");
let canva = document.getElementById("canva");
let inserir = document.getElementById("inserir");
let blur_nova_imagem = document.getElementById("blur_nova_imagem")
let selBox = document.getElementById("combobox-tamanhos")


window.iniciarPopupNovaImagem = function () {


    file = document.getElementById("arquivo");
    campoDescricao = document.getElementById("desc");
    campoTitulo = document.getElementById("titulo_nova_imagem");
    canva = document.getElementById("canva");
    inserir = document.getElementById("inserir");


    function file_input() {
        const entrada = document.getElementById("arquivo");


        const arquivo = entrada.files[0];


        if (arquivo && arquivo.name.endsWith(".png") || arquivo.name.endsWith(".jpg") || arquivo.name.endsWith(".jpeg") || arquivo.name.endsWith(".gif") || arquivo.name.endsWith(".webp")) {
            const reader = new FileReader();


            reader.onload = (e) => {
                const base64StringWithPrefix = e.target.result;
                canva.src = base64StringWithPrefix;
            };


            reader.readAsDataURL(arquivo);
        }
    }


    function img_insert() {
        const textoPublicacao = document.getElementById("textoPublicacao");
        textoPublicacao.focus();
        let imageData = canva.src;
        let fimPrefixo = imageData.indexOf(";")
        let inicioPrefixo = imageData.indexOf("/")
        let endPosition = imageData.indexOf(",");
        let sel = selBox.value
        endPosition++;
        prefixo = imageData.substring(inicioPrefixo, fimPrefixo);
        imageData = imageData.replace(imageData.substring(0, endPosition), "");
        let tagT = "rt_Inormal"
        if (imageData.naturalHeight < imageData.naturalWidth) {
            if (sel == "Grande") {
                tagT = "rt_Igrande"
            } else if (sel == "Pequena") {
                tagT = "rt_Ipequena"
            }
        } else {
            tagT = "rt_IVnormal"
            if (sel == "Grande") {
                tagT = "rt_IVgrande"
            } else if (sel == "Pequena") {
                tagT = "rt_IVpequena"
            }
        }




        const requestBody = {
            imageBase64: imageData,
            descricao: campoDescricao.value,
            titulo: campoTitulo.value,
            tipoImagem: prefixo
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
                ocultarPopupNI()
                const img = document.createElement("img");
                img.classList.add("rt_imagem");
                img.classList.add(tagT);
                img.textContent = "";
                img.src = canva.src
                console.log(selecaoAntiga);
                if (!selecaoAntiga || !selecaoAntiga.rangeCount) {
                    const editor = document.getElementById("textoPublicacao");
                    const sel = window.getSelection();
                    const range = document.createRange();
                    range.selectNodeContents(editor);
                    range.collapse(false);
                    sel.removeAllRanges();
                    sel.addRange(range);

                    // ESSENCIAL: salvar novo range corretamente
                    selecaoAntiga = range.cloneRange();
                }
                img.dataset.db_id = data.idImagem;

                const sel = window.getSelection();
                sel.removeAllRanges();
                sel.addRange(selecaoAntiga.cloneRange());
                inserirElemento(img, sel)
            })
            .catch(err => console.error(err));
    }


    file.addEventListener("input", file_input);
    inserir.addEventListener("click", img_insert);


    blur_nova_imagem.addEventListener("click", (e) => {
        if (e.target === blur_nova_imagem) {
            ocultarPopupNI()
        }
    });


    function ocultarPopupNI() {
        blur_nova_imagem.style.display = "none";
    }


}
