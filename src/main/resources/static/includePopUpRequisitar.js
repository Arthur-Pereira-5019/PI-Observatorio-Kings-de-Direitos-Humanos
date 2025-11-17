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

    const btnRequisitar = document.getElementById("btnRequisitar")
    const fundoPopupRequisitar = document.getElementById("posPopUpRequisitar")

    btnRequisitar.addEventListener("click", function () {
        fundoPopupRequisitar.style.display = "flex"

    })

    fundoPopupRequisitar.addEventListener("click", (e) => {
        if (e.target === fundoPopupRequisitar) {
            fundoPopupRequisitar.style.display = "none";
        }

    })

    const btnConfirmarRequisicao = document.getElementById("btnConfimarRequisicao")
    const textareaCargosRequisitar = document.getElementById("textarea-cargos-requisitar")
    const inputC = document.getElementById("inputC")
    const comboboxCargosRequisitar = document.getElementById("combobox-cargos-requisitar")

    fetch("http://localhost:8080/api/reqcar", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (res.ok) return res.json()
            fundoPopupRequisitar.style.display = "none";
        }).then(data => {
            textareaCargosRequisitar.value = data.motivacao
            comboboxCargosRequisitar.selected = data.cargoRequisitado
            inputC.value = data.contato
        })
        .catch(err => console.error(err));

    btnConfirmarRequisicao.addEventListener("click", function () {

        if (comboboxCargosRequisitar.selectedIndex == 0) {
            return
        }
        let valorCargo = comboboxCargosRequisitar.selectedIndex + 2



        const novoPost = {
            cargoRequisitado: valorCargo,
            motivacao: textareaCargosRequisitar.value,
            contato: inputC.value
        }

        fetch("http://localhost:8080/api/reqcar/requisitar_cargo", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoPost)

        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor");
                alert("Seu pedido será processado!")
                fundoPopupRequisitar.style.display = "none";
            })
            .catch(err => console.error(err));

    })

}

document.addEventListener("DOMContentLoaded", iniciarRequisitar);

