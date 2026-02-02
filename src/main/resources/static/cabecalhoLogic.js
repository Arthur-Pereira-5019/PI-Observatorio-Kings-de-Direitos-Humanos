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

    btnLupa = document.getElementById("lupa")

    menuLateral = document.getElementById("parte-geral-menu-lateral");
    barraPesquisa = document.getElementById('campoPesquisa');
    barraPesquisa.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            busca()
        }
    });

    btnLupa.addEventListener("click", busca)

    menu.addEventListener("click", function () {
        if (menuLateral.style.display === "flex") {
            menuLateral.style.display = "none";
        } else {
            menuLateral.style.display = "flex";
        }
    })

    btnNoticias.addEventListener("click", function () {
        window.location.pathname = "noticias";
    })

    btnForuns.addEventListener("click", function () {
        window.location.pathname = "foruns";
    })

    btnPublicacoes.addEventListener("click", function () {
        window.location.pathname = "publicacoes/ /0";
    })

    btnApoio.addEventListener("click", function () {
        window.location.pathname = "apoio";
    })

    btnSobre.addEventListener("click", function () {
        window.location.pathname = "sobre";
    })

    home.addEventListener("click", function () {
        window.location.href = "http://localhost:8080";
    })

    btnSair.addEventListener("click", async function () {
        await logout();
        window.location.pathname = ""
        window.location.reload()
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
            if (data.edc === "MODERADOR") {
                fundo.style.backgroundColor = "#9F00D9";
            } else if (data.edc === "ESPECIALISTA") {
                fundo.style.backgroundColor = "#FF8800";
            } else if (data.edc === "ADMINISTRADOR") {
                fundo.style.backgroundColor = "#00c2b2ff";
            }
            bemvindos.textContent = "Olá " + data.nome + "!";
            if (data.fotoDePerfil != null) {
                let fotoId = data.fotoDePerfil.idImagem
                fetchImagem(fotoId,pic)
            }
            document.querySelector("#btnSair").style.display = "flex";
        }).catch(e => {

        })

    async function logout() {
        const res = await fetch("http://localhost:8080/api/user/logout", {
            headers: { 'Content-Type': 'application/json' },
        })
        if (!res.ok) throw new Error("Erro no servidor")
    }

    async function busca() {
        let busca = barraPesquisa.value == "" ? " " : barraPesquisa.value
        if (window.location.href.includes("/noticias/")) {
            window.location.pathname = "noticias/" + busca + "/0";
        } else if (window.location.href.includes("/foruns/")) {
            window.location.pathname = "foruns/" + busca + "/0";
        } else if (window.location.href.includes("/registro/")) {
            window.location.pathname = "registro/" + busca + "/0";
        } else if (window.location.href.includes("/requisicoes/")) {
            window.location.pathname = "requisicoes/" + busca + "/0";
        } else {
            window.location.pathname = "publicacoes/" + busca + "/0";
        }
    }
}
document.addEventListener("DOMContentLoaded", iniciarCabecalho)