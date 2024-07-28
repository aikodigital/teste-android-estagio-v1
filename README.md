# Teste Android

![Aiko](imagens/aiko.png)

Neste teste serão avaliados seus conhecimentos e a metodologia aplicada no desenvolvimento de aplicações mobile Android.

## O Desafio

Seu objetivo é criar um aplicativo que exiba dados sobre o transporte público da cidade de São Paulo, consultando a [API **Olho Vivo**](api.md) que provê informações em tempo real do monitoramento da frota de ônibus da cidade de São Paulo.

## Requisitos

Esses requisitos são obrigatórios e devem ser desenvolvidos para a entrega do teste

* **Posições dos veículos**: Exibir no mapa onde os veículos estavam na sua última atualização. [X]

* **Linhas**: Exibir informações sobre as linhas de ônibus. [X]

* **Paradas**: Exibir os pontos de parada da cidade no mapa.[X]

* **Previsão de chegada**: Dado uma parada informar a previsão de chegada de cada veículo que passe pela parada selecionada.[X]

* **Pesquisa e Filtros**: Permitir que o usuário pesquise e filtre esses dados, interagindo com a interface. [X]


## Tecnologia Ultilizada

* React Native 
* TypeScript
* Expo

## Dependencias

    "@react-native-community/masked-view": "^0.1.11",
    "@react-navigation/native": "^6.1.18",
    "@react-navigation/stack": "^6.4.1",
    "expo": "~51.0.14",
    "expo-location": "^17.0.1",
    "expo-status-bar": "~1.12.1",
    "react": "18.2.0",
    "react-native": "0.74.2",
    "react-native-gesture-handler": "^2.17.1",
    "react-native-maps": "^1.17.1",
    "react-native-reanimated": "^3.14.0",
    "react-native-safe-area-context": "^4.10.8",
    "react-native-screens": "^3.32.0"

## Entrega

Para realizar a entrega do teste você deve:

* Relizar o fork e clonar esse repositório para sua máquina.
  
* Criar uma branch com o nome de `teste/[NOME]`.
  * `[NOME]`: Seu nome.
  * Exemplos: `teste/fulano-da-silva`; `teste/beltrano-primeiro-gomes`.
  
* Faça um commit da sua branch com a implementação do teste.
  
* Realize o pull request da sua branch nesse repositório.