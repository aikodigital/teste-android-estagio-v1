# Desafio Olho Vivo

Desafio técnico referente a implementação da api Olho Vivo em Android nativo.

No diretório raiz do projeto, crie o arquivo local.properties se não existir e adicione o campo `OLHO_VIVO_API_KEY` com o valor da api key da aplicação do Olho Vivo.

### O que este app faz?

- É possível buscar as linhas pelo nome e filtrar pelo sentido
- Exibe os corredores inteligentes
- Ao selecionar uma linha, é mostrada no mapa os ônibus daquela linha com as paradas (se estiverem disponíveis)
- Ao selecionar um corredor é mostrado as paradas disponíveis (se estiverem disponíveis)
- Ambas as paradas e os ônibus ao tocarem centralizaram no mapa a posição atual da entidade em questão

Foram criados testes unitários com Junit 4 e Mockito
