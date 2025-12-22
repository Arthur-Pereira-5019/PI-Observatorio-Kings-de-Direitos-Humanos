# Observatório Kings de Direitos Humanos no Vale do Itajaí

Site desenvolvido com Spring Boot para dar a minorias sociais e demais pessoas engajadas no Vale do Itajaí (Região de Santa Catarina) as ferramentas necessárias para defender seus direitos.

*Projeto Integrador feito como requisito para conclusão do Curso Técnico Integrado ao Ensino Médio de Informática no IFSC - Gaspar.*

### As quatro funcionalidades chave do sistema são:
 - Criação de publicações por especialistas
 - Criação de fóruns para usuários comuns conversarem entre si
 - Agregação de notícias externas com a temática dos direitos humanos na região do Vale;
 - Listagem de pessoas e instituições relacionadas com a defesa de direitos humanos na região do Vale do Itajaí.


## A aplicação

### Usuários comuns

- Usuários comuns podem visualizar o conteúdo geral do site, tendo acesso ao que foi cadastrado de notícias, fóruns, apoios, publicações e sobre o site.
  - Na tela de publicações haverão conteúdos como infográficos, divulgação de pesquisas acadêmicas, divulgação de eventos (relacionados com a temática do site), artigos de opnião, reportagens, etc...
  - Na tela de notícias constará um subconjunto de publicações que são notícias, elas estarão com o símbolo do Kings. Caso não estejam com o símbolo do Kings e sim de um outro site de notícias, significa que essa notícia é agregada, clicar nela leva o usuário para seu site de publicação original.
  - A tela de apoios contém uma lista de pessoas ou instituições compromissadas com a causa dos Direitos Humanos na região do Vale do Itajaí que pediram para a administração do sistema para lá estarem, além de alguns dados para contato como telefone, Instagram, endereço, etc...
  - A tela de fóruns contém discussões simples entre a comunidade do site.
  - A tela de sobre constará informações sobre o Kings.
  - Nesses fóruns e publicações, usuários comuns podem fazer comentários, ou denunciar o conteúdo.
- Usuários comuns são capazes de criar fóruns, dando a eles títulos e um texto motivador, para assim se comunicarem com outros usuários.
- Através de referências pelo site, como o nome de quem efetuou um comentário, o usuário pode acessar a tela de perfil de outros usuários, vendo seus fóruns, publicações, foto de perfil, etc... Todo usuário possui uma tela de exibição de seu perfil.
  - Acessando o próprio perfil, um usuário comum pode configurar sua conta, requisitar um cargo para si ou requisitar a exclusão de sua conta em até 30 dias.
  - Acessando o perfil de outrém, um usuário comum pode denunciar essa conta.
  - Qualquer tela que possui algum conteúdo paginado, através de uma barra de pesquisa no cabeçalho do site é possível buscar os conteúdos desejados por uma busca textual.

### Especialistas

- Um especialista tem os mesmos acessos base de um usuário comum.
- Os especialistas na tela de publicação podem cadastrar novas publicações, dando a ela nomes, texto editado com um editor rich text, uma capa e algumas tags que auxiliam a sua exibição no sistema. Dar a tag "noticia", faz a publicação também aparecer na tela de notícias.
- Na tela de notícias, um especialista pode acessar uma outra tela, a de notícias agregadas, onde ficam listadas as notícias agregadas pelo sistema de forma programática. O especialista pode aceitar elas no sistema ou não.

### Moderadores

- Possuem os mesmos acessos base de um usuário comum, com exceção que não podem denunciar conteúdo (afinal eles possuem poder para lidar com conteúdo inadequado no site)
- Podem visualizar publicações ou fóruns ocultos, tanto nas listagens quanto em suas páginas de exibição.
- Em publicações ou fóruns:
  - Podem ocultar publicações e fóruns, fornecendo uma motivação para tal ação.
  - Podem desocultar publicações e fóruns que tenham sido ocultos, também fornecendo uma motivação para tal ação.
  - Podem excluir comentários, fornecendo uma motivação para tal.
- Ao visitarem a tela de perfil de outros usuários:
  - Podem alterar a titulação de usuários, alternando entre suspenso, moderador, especialista ou usuário padrão, bastando fornecer uma motivação para tal.
  - Podem acessar uma lista de comentários efetuados por aquele usuário, podendo os excluir remotamente ou acessar o local do comentário para maior contexto.
- Ao acessarem a própria tela de perfil:
  - Podem acessar a tela de decisões moderadoras
  - Podem configurar suas contas
- Na tela de decisões moderadoras, os moderadores podem visualizar toda sorte de conteúdos relevantes para a moderação do sistema, quem fez essas decisões, quem as sofreu (direta ou indiretamente), qual conteúdo sofreu, quando e por quê sofreu (Por isso as motivações necessárias quando o moderador toma certas decisões). São registradas as seguintes decisões:
  - Troca de titulação;
  - Ocultação de conteúdos;
  - Desocultação de conteúdos;
  - Exclusão de comentários;
  - Exclusão de contas;

### Administradores
- Possuem todos acessos de um moderador e de um especialista.
- O primeiro usuário cadastrado no sistema será um administrador.
- Ao adicionar titulações, podem também adicionar a de administrador
- Podem cadastrar, excluir e atualizar apoios.
- Podem editar a tela de sobre com um editor Rich Text.



## Tecnologias

**Cliente:** HTML, CSS, Javascript

**Servidor:** MySQL, Java 17 (Spring Boot 3.5.4) 


## Deploy Local

Para rodar o observatório na sua máquina:

1) Clone o repositório
```bash
  npm run deploy
```

2) Crie uma conta em https://newsdata.io/login e obtenha uma chave da API.

3) Defina a chave como variável local usando o terminal, no caso do Windows, use o Powershell como administrador e digite o seguinte comando:
```bash
setx NEWSDATA_API_KEY "Sua chave aqui"
```

4) Crie o banco de dados no MySQL:
```bash
create database kings
```

5) Configure o application.yaml da aplicação para refletir a senha do seu Banco de Dados:
```yaml
spring:
 datasource:
  password: xxxxxxxxxx
```

6) Abra um terminal na pasta raíz do projeto e use:
```bash
mvn spring-boot:run
```

## Autores

- [@Arthur-Pereira-5019](https://github.com/Arthur-Pereira-5019)
- [@Emanuel-Ferias ](https://github.com/Emanuel-Farias)
- [@Otapyy ](https://github.com/Otapyy)


## Demonstração

Clique na imagem abaixo para ter acesso ao vídeo de demonstração (sem áudio)

[![Demonstração](https://img.youtube.com/vi/iNj7MGae80g/0.jpg)](https://www.youtube.com/watch?v=iNj7MGae80g)


## Documentação

[Documentação técnica](https://github.com/Arthur-Pereira-5019/PI-Observatorio-Kings-de-Direitos-Humanos/blob/entregaFinal/Relat%C3%B3rio%20T%C3%A9cnico%20-%20OBSERVAT%C3%93RIO%20KINGS%20DE%20DIREITOS%20HUMANOS%20NO%20VALE%20DO%20ITAJA%C3%8D.pdf)

