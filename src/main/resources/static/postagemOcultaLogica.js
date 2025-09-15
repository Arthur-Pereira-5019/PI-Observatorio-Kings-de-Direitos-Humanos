document.addEventListener('DOMContentLoaded', function() {
url = 'http://localhost:8080/api/decmod';
path = window.location.pathname;
url = url+path.replace("pagina_banida/","");



  fetch(url)
  .then(response => {
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return response.json();
  })
  .then(data => {
    responsavel = document.getElementById("responsavel");
    motivacao = document.getElementById("motivacao");
    dataCampo = document.getElementById("data");
    dataCampo.textContent += data.data;
    motivacao.textContent += data.motivacao;
    responsavel.textContent += data.responsavel.nome;

  })
  .catch(error => {
    console.error('Error fetching data:', error);
  });
});
