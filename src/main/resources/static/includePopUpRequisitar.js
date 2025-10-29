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


    btnRequisitar.addEventListener("click", function(){
        fundoPopupRequisitar.style.display = "flex"

    })
    
    fundoPopupRequisitar.addEventListener("click", (e) =>{
        if (e.target === fundoPopupRequisitar) {
            fundoPopupRequisitar.style.display = "none";
        }

    })

    const btnConfirmarRequisicao = document.getElementById("btnConfimarRequisicao")
    const textareaCargosRequisitar = document.getElementById("textarea-cargos-requisitar")
    const comboboxCargosRequisitar = document.getElementById("combobox-cargos-requisitar")

    btnConfirmarRequisicao.addEventListener("click", function(){
        
        
            if(comboboxCargosRequisitar.selectedIndex == 0){
                return

            }
            console.log(comboboxCargosRequisitar.selectedIndex)
            let valorCargo = comboboxCargosRequisitar.selectedIndex + 2

            const novoPost = {
                cargoRequisitado : valorCargo,
                motivacao : textareaCargosRequisitar.value,
                anexoBase64 : 
                tipoAnexo : 


            }

            fetch("http://localhost:8080/api/user/requisitar_cargo", {
            method: 'POST',
            headers: {'Content-Type': 'application/json'}, 
            body: JSON.stringify(novoPost) 
                
        })
          .then(res => {
                    if (!res.ok) throw new Error("Erro no servidor");
                    return res.json();
                })
                .then(() => {
                    
                    fundoPopupRequisitar.style.display = "none";
                    
                })
                .catch(err => console.error(err));

    })
   

}

document.addEventListener("DOMContentLoaded", iniciarRequisitar);