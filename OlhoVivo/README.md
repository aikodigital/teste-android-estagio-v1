# Olho Vivo - São Paulo Transport Data Viewer

## Descrição

Olho Vivo é uma aplicação Android que fornece informações em tempo real sobre o transporte público de São Paulo. Utilizando a API Olho Vivo, o aplicativo permite aos usuários visualizar a localização de ônibus, informações de linhas, paradas e previsões de chegada, tudo integrado ao Google Maps para uma experiência intuitiva.

## Funcionalidades

- Autenticação: Realiza autenticação na API Olho Vivo para acessar os dados de transporte.
- Visualização de Veículos: Exibe a posição atual dos veículos de transporte público no mapa.
- Informações de Linhas: Permite a busca por linhas de ônibus específicas e exibe detalhes das mesmas.
- Visualização de Paradas: Exibe a localização das paradas de ônibus e suas respectivas informações.
- Previsão de Chegada: Fornece previsões de chegada de ônibus em paradas selecionadas.

## Estrutura do Projeto

- MainActivity: Ponto de entrada da aplicação, gerencia o mapa e a interface do usuário.
- ViewModels: Inclui LinhaViewModel, ParadaViewModel, PrevisaoViewModel e TransporteViewModel, responsáveis por gerenciar a lógica de dados e comunicação com a API.
- Modelos: Contém classes de dados como Linha, Parada, PrevisaoResponse e Veiculo, representando as entidades de transporte.
- Rede: A comunicação com a API é feita através da classe ApiClient usando Retrofit.

### Decisões de Design

- Uso do MVVM: O padrão de arquitetura Model-View-ViewModel (MVVM) foi escolhido para separar a lógica de negócios da interface do usuário, facilitando a manutenção e escalabilidade do aplicativo.
- Retrofit para Rede: Retrofit foi escolhido como a biblioteca de rede devido à sua simplicidade e eficiência na realização de chamadas HTTP e manipulação de respostas.
- Google Maps API: Integrado para fornecer uma visualização geográfica precisa e intuitiva dos dados de transporte público.
- Estudos de outras formas como jetcompose, tests.

## Instruções de Uso

Instalação:

- Clone o repositório: git clone
- Importe o projeto no Android Studio.

Configuração:

- Substitua API_KEY no arquivo build.gradle (Module) com sua chave de API da Olho Vivo.
Execução:
- Conecte um dispositivo Android ou inicie um emulador.
- Execute o projeto através do Android Studio.


## Contribuições
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests. Para grandes mudanças, abra um issue primeiro para discutir o que você gostaria de mudar.