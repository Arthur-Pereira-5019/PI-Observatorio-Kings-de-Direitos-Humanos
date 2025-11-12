async function iniciarPopupAplicarCargo(url) {
    const aplicar = document.querySelector(".botao-aplicar");
    const blur = document.querySelector("#blur_AP");
    const motivacao = document.querySelector("#textarea-cargos-aplicar");
    const container_decisao = document.querySelector(".fundo-popup-aplicar");
    const box = document.querySelector("#combobox-cargos-aplicar");

    blur.display = "flex"
    container_decisao.display = "flex"

    aplicar.addEventListener("click", async function () {
        await aplicarCargo(url)
    })

    blur.addEventListener("click", sumir)


    function sumir() {
        container_decisao.remove();
        blur.remove();
    }

    function getId() {
        let sel = box.value;
        if(sel == "Titulação:") {
            return -1;
        } else if(sel == "Suspenso") {
            return 1;
        } else if(sel == "Padrão") {
            return 2;
        } else if(sel == "Especialista") {
            return 3;
        } else if(sel == "Moderador") {
            return 4;
        } else if(sel == "Administrador") {
            return 5;
        }
    }

    async function aplicarCargo(url) {
        sumir();
        let sel = getId();
        if(sel == -1) {
            alert("Selecione um cargo!")
            return;
        }
        const requestBody = {
            "motivacao": motivacao.value,
            "idCargo": sel
        }

        fetch(url, {
            method: "PUT",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (!res.ok) {
                    alert("Permissões insuficientes");
                    return;
                }
                alert("Cargo aplicado com sucesso!")
                window.location.reload();
            })
            .catch(err => console.error(err));
    }
}