let home;
let menu;
let barraPesquisa;
async function iniciarCabecalho() {

    home = document.getElementById('home');
    menu = document.getElementById("menu");
    bemvindos = document.getElementById("bemvindo");
    pic = document.getElementById("iconButton");
    fundo = document.getElementById("containerCabecalho");

    btnNoticias = document.getElementById("btnNoticias");
    btnForuns = document.getElementById("btnForuns");
    btnPublicacoes = document.getElementById("btnPublicacoes");
    btnApoio = document.getElementById("btnApoio");
    btnSobre = document.getElementById("btnSobre");
    btnContato = document.getElementById("btnContato");
    btnSair = document.getElementById("btnSair");

    menuLateral = document.getElementById("parte-geral-menu-lateral");
    barraPesquisa = document.getElementById('campoPesquisa');
    barraPesquisa.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            if (window.location.href.includes("/noticias/")) {
                event.preventDefault();
                window.location.href = "http://localhost:8080/noticias/" + barraPesquisa.value + "/0";
            } else {
                event.preventDefault();
                window.location.href = "http://localhost:8080/publicacoes/" + barraPesquisa.value + "/0";
            }

        }
    });

    menu.addEventListener("click", function () {
        if (menuLateral.style.display === "flex") {
            menuLateral.style.display = "none";
        } else {
            menuLateral.style.display = "flex";
        }
    })

    btnNoticias.addEventListener("click", function () {
        window.location.href = "http://localhost:8080/noticias";
    })

    btnForuns.addEventListener("click", function () {
        window.location.href = "http://localhost:8080/foruns";
    })

    btnPublicacoes.addEventListener("click", function () {
        window.location.href = "http://localhost:8080/publicacoes/ /0";
    })

    btnApoio.addEventListener("click", function () {
        window.location.href = "http://localhost:8080/apoio";
    })

    btnSobre.addEventListener("click", function () {
        window.location.href = "http://localhost:8080/sobre";
    })

    home.addEventListener("click", function () {
        window.location.href = "http://localhost:8080";
    })

    btnSair.addEventListener("click", async function () {
        await logout();
        window.location.reload();
    })


    fetch("http://localhost:8080/api/user/apresentar", {
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (res.ok) {
                return res.json();
            }
        })
        .then(data => {
            if (data.edc === "MODERADOR" || data.edc === "ADMINISTRADOR") {
                fundo.style.backgroundColor = "#9F00D9";
            } else if (data.edc === "ESPECIALISTA") {
                fundo.style.backgroundColor = "#FF8800";
            }
            bemvindos.textContent = "Olá " + data.nome + "!";
            if (data.fotoDePerfil != null) {
                pic.src = "data:image/" + data.fotoDePerfil.tipoImagem + ";base64," + data.fotoDePerfil.imagem;
            }
            document.querySelector("#btnSair").style.display = "flex";
        })

    async function logout() {
        fetch("http://localhost:8080/api/user/logout", {
            headers: { 'Content-Type': 'application/json' },
        })
            .then(res => {
                if (!res.ok) throw new Error("Erro no servidor")

            })
    }
}
    document.addEventListener("DOMContentLoaded", iniciarCabecalho)