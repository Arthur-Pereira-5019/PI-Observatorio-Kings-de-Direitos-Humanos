
async function fetchImagem(id,componente) {
    let descricao;
    await fetch("http://localhost:8080/api/imagem/" + id, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
    })
        .then(res => {
            if (!res.ok) throw new Error("Erro no servidor");
            descricao = res.headers.get("descricao")
            return res.blob();
        })
        .then(data => {
            const url = URL.createObjectURL(data)
            componente.src = url;
            componente.title = descricao
            componente.alt = descricao
            componente.onload = () => {
                URL.revokeObjectURL(url);
            };
            return true;
        })
        .catch(err => console.error(err));
}