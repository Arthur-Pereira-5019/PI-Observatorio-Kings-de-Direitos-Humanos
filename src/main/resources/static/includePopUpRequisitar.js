async function iniciarRequisitar() {
    const blur = document.getElementById("blurRequisitarPopup")

    blur.addEventListener("click", () => {
        blur.parentNode.remove()
    })

    const btnConfirmarRequisicao = document.getElementById("btnConfimarRequisicao")
    const textareaCargosRequisitar = document.getElementById("textarea-cargos-requisitar")
    const inputC = document.getElementById("inputC")
    const comboboxCargosRequisitar = document.getElementById("combobox-cargos-requisitar")

    fetch("http://localhost:8080/api/reqcar", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (res.ok) return res.json();
        }).then(data => {
            let cR = data.cargoRequisitado;
            let i;
            if (cR == 'ESPECIALISTA') {
                i = 2
            } else if (cR == 'MODERADOR') {
                i = 1;
            }
            textareaCargosRequisitar.value = data.motivacao
            comboboxCargosRequisitar.selectedIndex = i
            inputC.value = data.contato
        })
        .catch(err => console.error(err));

    btnConfirmarRequisicao.addEventListener("click", function () {

        if (comboboxCargosRequisitar.selectedIndex == 0) {
            return
        }
        let valorCargo = comboboxCargosRequisitar.selectedIndex - 1

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
                window.location.reload()
            })
            .catch(err => console.error(err));

    })

}

document.addEventListener("DOMContentLoaded", iniciarRequisitar);

