async function iniciarNovoApoio(url, idApoioExistente) {
    const nomeInst = document.getElementById("label-nome-instituicao")
    const sobreInst = document.getElementById("ta-sobre-instituicao")
    const twitter = document.getElementById("campo-twitter")
    const insta = document.getElementById("campo-insta")
    const wpp = document.getElementById("campo-whats")
    const endereco = document.getElementById("campo-endereco")
    const siteInst = document.getElementById("campo-site")
    const likIn = document.getElementById("campo-linkedin")
    const entrada = document.getElementById("novoApoioInput")
    const capaPreview = document.getElementById("foto-perfil-instituicao")

    const btnCriarApoio = document.getElementById("botao-salvar-instituicao")
    const excluirApoio = document.getElementById("lixo-deletar")

    const blur = document.querySelector("#blurNovoApoio");
    const container_decisao = document.querySelector("#fundoNovoApoio");

    blur.display = "flex"
    container_decisao.display = "flex"

    let imagemAlterada = false;
    let dadosOriginais = null;

    blur.addEventListener("click", sumir)

    function sumir() {
        container_decisao.remove();
        blur.remove();
    }

    if (idApoioExistente) {
        await carregarDadosApoio(idApoioExistente);
        excluirApoio.style.display = "block";
        excluirApoio.addEventListener("click", function() {
            if (confirm("Tem certeza que deseja excluir este apoio?")) {
                deletarApoio(url, idApoioExistente);
            }
        });
    } else {
        excluirApoio.remove();
    }

    entrada.addEventListener("input", input_capa);

    function input_capa() {
        const imagemSubmetida = entrada.files[0];

        if (imagemSubmetida && (imagemSubmetida.name.endsWith(".png") || imagemSubmetida.name.endsWith(".jpg") || imagemSubmetida.name.endsWith(".jpeg") || imagemSubmetida.name.endsWith(".webp"))) {
            const reader = new FileReader();

            reader.onload = (e) => {
                const base64StringWithPrefix = e.target.result;
                capaPreview.src = base64StringWithPrefix
                imagemAlterada = true
            }
            reader.readAsDataURL(imagemSubmetida);
        }
    }

    async function carregarDadosApoio(idApoio) {
        try {
            const response = await fetch(`http://localhost:8080/api/apoio/${idApoio}`, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include'
            });

            if (!response.ok) {
                console.error("Erro na resposta:", response.status);
                throw new Error("Erro ao carregar dados do apoio");
            }

            dadosOriginais = await response.json();
            console.log("Dados carregados:", dadosOriginais);

            nomeInst.value = dadosOriginais.nomeInstituicao || "";
            sobreInst.value = dadosOriginais.sobreInstituicao || "";
            twitter.value = dadosOriginais.twitter || "";
            insta.value = dadosOriginais.instagram || "";
            wpp.value = dadosOriginais.telefone || "";
            endereco.value = dadosOriginais.localizacao || "";
            siteInst.value = dadosOriginais.site || "";
            likIn.value = dadosOriginais.linkedin || "";

            if (dadosOriginais.foto && dadosOriginais.foto.imagem) {
                capaPreview.src = "data:image/" + dadosOriginais.foto.tipoImagem + ";base64," + dadosOriginais.foto.imagem;
            }

        } catch (error) {
            console.error("Erro ao carregar apoio:", error);
            alert("Erro ao carregar dados do apoio");
        }
    }

    btnCriarApoio.addEventListener("click", function () {
        if (idApoioExistente) {
            editarApoio();
        } else {
            if (nomeInst.value.trim() == "" || sobreInst.value.trim() == "") {
                alert("Preencha ao menos o campo de Nome e de Sobre!");
                return;
            }
            criarApoio();
        }
    });

    function criarApoio() {
        let ib64 = imagemAlterada ? capaPreview.src.replace(capaPreview.src.substring(0, capaPreview.src.indexOf(",") + 1), "") : "";
        
        const novoApoio = {
            nomeInstituicao: nomeInst.value,
            sobreInstituicao: sobreInst.value,
            twitter: twitter.value,
            telefone: wpp.value,
            localizacao: endereco.value,
            site: siteInst.value,
            instagram: insta.value,
            linkedin: likIn.value,
            ImagemBase64: ib64
        };

        fetch(url, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(novoApoio)
        })
            .then(res => {
                if (!res.ok) {
                    return res.json();
                }
                alert("Novo apoio cadastrado com sucesso!");
                window.location.reload();
            })
            .catch(err => console.error(err));
    }

    function editarApoio() {
        const apoioEditado = {
            nomeInstituicao: nomeInst.value,
            sobreInstituicao: sobreInst.value,
            twitter: twitter.value,
            telefone: wpp.value,
            localizacao: endereco.value,
            site: siteInst.value,
            instagram: insta.value,
            linkedin: likIn.value
        };

        if (imagemAlterada) {
            apoioEditado.ImagemBase64 = capaPreview.src.replace(capaPreview.src.substring(0, capaPreview.src.indexOf(",") + 1), "");
        } else {
            apoioEditado.ImagemBase64 = "";
        }

        fetch(url + idApoioExistente, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            credentials: 'include',
            body: JSON.stringify(apoioEditado)
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error("Erro ao atualizar");
                }
                alert("Apoio atualizado com sucesso!");
                window.location.reload();
            })
            .catch(err => {
                console.error(err);
                alert("Erro ao atualizar apoio");
            });
    }

    function deletarApoio(baseUrl, idApoio) {
        fetch(`${baseUrl}${idApoio}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error("Erro ao deletar");
                }
                alert("Apoio excluído com sucesso!");
                window.location.reload();
            })
            .catch(err => {
                console.error(err);
                alert("Erro ao excluir apoio");
            });
    }
}

document.addEventListener("DOMContentLoaded", iniciarNovoApoio);