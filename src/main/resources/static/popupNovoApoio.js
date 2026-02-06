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

    let novaImagem;

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
        excluirApoio.addEventListener("click", function () {
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
            capaPreview.src = URL.createObjectURL(entrada.files[0])
            novaImagem = imagemSubmetida;
            imagemAlterada = true
            capaPreview.onload = function () {
                URL.revokeObjectURL(capaPreview.src)
            }
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
            fetchImagem(dadosOriginais.foto.idImagem,capaPreview)

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
        const novoApoio = {
            nomeInstituicao: nomeInst.value,
            sobreInstituicao: sobreInst.value,
            twitter: twitter.value,
            telefone: wpp.value,
            localizacao: endereco.value,
            site: siteInst.value,
            instagram: insta.value,
            linkedin: likIn.value,
            atualizarImagem: imagemAlterada
        };

        const formData = new FormData();
        formData.append("capa", imagemAlterada ? novaImagem : null)
        formData.append("apoio", new Blob([JSON.stringify(novoApoio)], { type: "application/json" }))

        fetch(url, {
            method: 'POST',
            body: formData
        })
            .then(res => {
                if (!res.ok) {
                    return res.json();
                }
                alert("Novo apoio cadastrado com sucesso!");
                window.location.reload();
            }).catch(err => console.error(err));
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
            linkedin: likIn.value,
                        atualizarImagem: imagemAlterada

        };
        const formData = new FormData();
        formData.append("capa", imagemAlterada ? novaImagem : null)
        formData.append("apoio", new Blob([JSON.stringify(apoioEditado)], { type: "application/json" }))


        fetch(url + idApoioExistente, {
            method: 'PUT',
            body: formData
        })
            .then(res => {
                if (!res.ok) {
                    return res.json();
                }
                alert("Apoio atualizado com sucesso!");
                window.location.reload();
            }).catch(err => console.error(err));
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