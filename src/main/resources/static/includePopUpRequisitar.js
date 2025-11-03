async function carregarHTMLRequisitar(id, url, cssFile) {
    const response = await fetch(url);
    const data = await response.text();
    document.getElementById(id).innerHTML = data;

    if (cssFile) {
        const link = document.createElement("link");
        link.rel = "stylesheet";
        link.href = cssFile;
        document.head.appendChild(link);
    }
}

async function iniciarRequisitar() {
    await carregarHTMLRequisitar("requisitar", "/popupRequisitar", "/requisitarPopupStyle.css");

    const btnRequisitar = document.getElementById("btnRequisitar");
    const fundoPopupRequisitar = document.getElementById("posPopUpRequisitar");
    const btnConfirmarRequisicao = document.getElementById("btnConfimarRequisicao");
    const textareaCargosRequisitar = document.getElementById("textarea-cargos-requisitar");
    const comboboxCargosRequisitar = document.getElementById("combobox-cargos-requisitar");
    const anexoRequisicao = document.getElementById("campoAnexoInput");

    let base64StringWithPrefix = null; 

    
    btnRequisitar.addEventListener("click", () => {
        fundoPopupRequisitar.style.display = "flex";
    });

    fundoPopupRequisitar.addEventListener("click", (e) => {
        if (e.target === fundoPopupRequisitar) {
            fundoPopupRequisitar.style.display = "none";
        }
    });

   
    anexoRequisicao.addEventListener("change", () => {
        const arquivo = anexoRequisicao.files[0];
        if (!arquivo) return;

        const reader = new FileReader();

        reader.onload = () => {
            
            base64StringWithPrefix = reader.result;
        };

        reader.readAsDataURL(arquivo); 
    });

    
    btnConfirmarRequisicao.addEventListener("click", async () => {

        console.log("oie")
        if (comboboxCargosRequisitar.selectedIndex === 0) return;

        if (!base64StringWithPrefix) {
            alert("Selecione um arquivo antes de confirmar!");
            return;
        }

        const valorCargo = comboboxCargosRequisitar.selectedIndex + 2;

        const novoPost = {
            cargoRequisitado: valorCargo,
            motivacao: textareaCargosRequisitar.value,
            anexoBase64: base64StringWithPrefix 
        };

        try {
            const res = await fetch("http://localhost:8080/api/user/requisitar_cargo", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(novoPost)
            });

            if (!res.ok) throw new Error("Erro no servidor");

            await res.json();
            fundoPopupRequisitar.style.display = "none";

        } catch (err) {
            console.error("Erro ao enviar requisição:", err);
        }
    });
}

document.addEventListener("DOMContentLoaded", iniciarRequisitar);
