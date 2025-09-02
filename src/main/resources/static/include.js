function loadHTML(id, url, cssFile) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById(id).innerHTML = data;
            if(cssFile){
                let link = document.createElement("link")
                link.rel = "stylesheet";
                link.href = cssFile;
                document.head.appendChild(link);

            }
        });
}

loadHTML("header", "/cabecalho", "rodapeStyle.css");
loadHTML("footer", "/rodape", "cabecalhoStyle.css");