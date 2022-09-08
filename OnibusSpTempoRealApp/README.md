# Teste Android

![Aiko](imagens/aiko.png)

Neste teste serão avaliados seus conhecimentos e a metodologia aplicada no desenvolvimento de aplicações mobile Android.

## O Desafio

Seu objetivo é criar um aplicativo que exiba dados sobre o transporte público da cidade de São Paulo, consultando a [API **Olho Vivo**](api.md) que provê informações em tempo real do monitoramento da frota de ônibus da cidade de São Paulo.

## Requisitos

Esses requisitos são obrigatórios e devem ser desenvolvidos para a entrega do teste

* **Posições dos veículos**: Exibir no mapa onde os veículos estavam na sua última atualização.

* **Linhas**: Exibir informações sobre as linhas de ônibus.

* **Paradas**: Exibir os pontos de parada da cidade no mapa.

* **Previsão de chegada**: Dado uma parada informar a previsão de chegada de cada veículo que passe pela parada selecionada.

* **Pesquisa e Filtros**: Permitir que o usuário pesquise e filtre esses dados, interagindo com a interface.

## Documentação 

Para desenvolver o aplicativo proposto no desafio, primeiro analisei a API, para entender melhor os dados que seriam necessários consumir, tive um pouco de dificuldades para lidar com a autenticação da API. Julguei necessário utilizar apenas 4 requisições da API para as funcionalidades do aplicativo, o (POST) da autenticação para conseguir o cookie de certificação, (GET) de todos os ônibus em circulação, (GET) de uma linha específica que o usuário pesquisar e (GET) de paradas de uma determinada linha. E para desenvolver o aplicativo utilizei Android nativo com Kotlin.
Fiz alguns casos de usos beeem simples só para demonstrar como utilizar o aplicativo e após os casos de uso, comento como estruturei o projeto.
