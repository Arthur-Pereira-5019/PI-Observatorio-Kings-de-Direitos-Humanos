let iconeCasa;
let iconeMenu;
let barraPesquisa;
async function iniciarCabecalho() {
    iconeCasa = document.getElementById('home');
    iconeMenu = document.getElementById("menu");
    barraPesquisa = document.getElementById('campoPesquisa');
    barraPesquisa.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            window.location.href = "http://localhost:8080/publicacoes/" + barraPesquisa.textContent;
        }
        
    });
    iconeCasa.addEventListener("click", function () {
            window.location.href = "http://localhost:8080";
        })
        menu.addEventListener
}
document.addEventListener("DOMContentLoaded", iniciarCabecalho)