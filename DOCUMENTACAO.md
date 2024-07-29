# Documentação do Aplicativo

## Visão Geral

Este aplicativo é uma aplicação React Native que utiliza a API da SPTrans para fornecer informações sobre linhas de ônibus, previsões de chegada e rastreamento de veículos. A navegação entre as telas é gerenciada pelo `react-navigation`.

## Estrutura do Projeto

### Navegação

- **Arquivo:** `src/navigation/Navigation.js`
- **Descrição:** Define a navegação entre as telas do aplicativo usando `createStackNavigator`. As telas incluídas são:
  - `AuthTest`: Tela de autenticação.
  - `BusLinesScreen`: Tela para exibir linhas de ônibus e previsões de chegada.
  - `TrackingBus`: Tela para rastreamento de veículos e paradas de ônibus.

### Componentes

- **AuthTest**
  - **Arquivo:** `src/components/AuthTest.js`
  - **Descrição:** Tela de autenticação onde o usuário pode inserir um token para autenticação. Após a autenticação, o usuário pode acessar outras telas do aplicativo.
  - **Funcionalidades:**
    - Solicita autenticação com um token fixo.
    - Navega para telas de `BusLines` e `TrackingBus` se a autenticação for bem-sucedida.

- **BusLinesScreen**
  - **Arquivo:** `src/components/BusLinesAndForecast.js`
  - **Descrição:** Tela para exibir as linhas de ônibus e previsões de chegada.
  - **Funcionalidades:**
    - Permite ao usuário buscar linhas de ônibus por código.
    - Exibe as linhas de ônibus e suas previsões de chegada.

- **TrackingBus**
  - **Arquivo:** `src/components/TrackingBus.js`
  - **Descrição:** Tela para rastreamento de veículos e exibição de paradas de ônibus em um mapa.
  - **Funcionalidades:**
    - Exibe veículos e paradas em um mapa.
    - Permite filtrar veículos e paradas por status ativo e busca por ID de veículo ou nome de parada.
    - Carrega mais veículos ao clicar em "Load More".

### API

- **Arquivo:** `src/api/api.js`
- **Descrição:** Contém funções para interagir com a API da SPTrans.
  - `authenticate(token)`: Autentica o usuário com um token.
  - `fetchBusLines(token, lineCode)`: Busca as linhas de ônibus com base no código da linha.
  - `fetchVehiclePositions(token)`: Busca a posição dos veículos.
  - `fetchStops(token)`: Busca as paradas de ônibus.
  - `fetchArrivalForecast(token, lineCode)`: Busca a previsão de chegada para uma linha de ônibus.

### Estilos

- **Arquivo:** `src/utils/busLinesStyles.js`
  - **Descrição:** Define os estilos para a tela de linhas de ônibus.
- **Arquivo:** `src/utils/combinedScreenStyles.js`
  - **Descrição:** Define os estilos para a tela de rastreamento de veículos e paradas de ônibus.

## Como Executar o Projeto

1. **Instalar Dependências**
   ```bash
   npm install
