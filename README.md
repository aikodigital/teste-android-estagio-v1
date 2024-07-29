# Teste Android

## O Desafio

Aplicativo que exibe os dados sobre o transporte público da cidade de São Paulo, consultando a [API **Olho Vivo**](api.md) que provê informações em tempo real do monitoramento da frota de ônibus da cidade de São Paulo.

## Requisitos

### Aba Home:

![Captura de tela 2024-07-29 160925](https://github.com/user-attachments/assets/8b24b41c-0e14-490c-8122-3b8baa37da30)


* **Linhas**: Exibe informações sobre as linhas informadas pelo usuário.

![Captura de tela 2024-07-29 161011](https://github.com/user-attachments/assets/66285788-3db0-433c-ac4b-3abc5ff41c18) ![Captura de tela 2024-07-29 161034](https://github.com/user-attachments/assets/f0ae66a9-d99d-42d3-a5ac-b92190c27a93)


* **Previsão de chegada**: Exibe a previsão de chegada de todos os veículos que passarão pela parada informada.

![Captura de tela 2024-07-29 161141](https://github.com/user-attachments/assets/a00ce822-4d67-40f5-93a6-10f502ed822a) ![Captura de tela 2024-07-29 161121](https://github.com/user-attachments/assets/3b3b0118-89cd-4bdc-80d0-fa9eed3a888e)

* **Busca por Linha**: Informa todos os pontos de parada atendidos pela linha selecionada pelo usuário.

![Captura de tela 2024-07-29 161229](https://github.com/user-attachments/assets/5bd4ee10-f7a0-4e9d-b872-62e12bcfd1ec) ![Captura de tela 2024-07-29 161249](https://github.com/user-attachments/assets/0a626584-b122-4ff0-95c9-43950d2e7adb)



### Aba Explore:

* **Paradas**: Exibe no mapa o ponto da parada informada pelo usuário.

![Captura de tela 2024-07-29 141737](https://github.com/user-attachments/assets/20d8e973-fd51-4f6e-adff-56fa8b204572)

* **Posições dos veículos**: Exibe no mapa onde os veículos estavam na sua última atualização.

![Captura de tela 2024-07-29 141521](https://github.com/user-attachments/assets/24966a7a-76aa-4fd5-a111-9d40ac8693d5)


**IMPORTANTE: As duas abas contam com o recurso de pesquisa.**


## Tecnologias utilizadas

* React Native e react-native-maps
* Expo
* Typescript/Javascript
* Android Emulator

## Como rodar esse projeto

#### vá para o repositório do projeto
$ cd teste

#### instale as dependências
$ npm install

#### rode o servidor
$ npx expo start

O Android SDK (device manager) foi utilizado para esse projeto: 
> para abrir o projeto no emulador, pressione a tecla A.

### Entry point (home screen):
app/index.tsx

## Extra

* **Documentação**

## Comentários

Enfrentei dificuldades com a autenticação da API do SPTrans. Mesmo com a chamada de autenticação retornando True (conforme orienta a documentação da API), continuei recebendo logs de falha/ausência de autorização. Parece haver alguma instabilidade na API.
