# Teste Android Aiko Digital

Este projeto é um app de mobilidade desenvolvido utilizando a API do Olho Vivo da cidade de São Paulo e a API do Google Maps. Ele visa proporcionar informações em tempo real sobre o transporte público da cidade de São Paulo, integrando dados de localização e mapas para uma melhor experiência do usuário.

## Funcionalidades

- **Consulta de Linhas e Paradas**: Permite ao usuário pesquisar por linhas de ônibus e visualizar as paradas no mapa.
- **Informações em Tempo Real**: Utiliza a API do Olho Vivo para fornecer informações em tempo real sobre a localização dos ônibus.
- **Integração com Google Maps**: Exibe as paradas de ônibus e trajetos no Google Maps, permitindo ao usuário traçar rotas e visualizar o trânsito em tempo real.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação principal para o desenvolvimento do aplicativo.
- **Jetpack Compose**: Framework moderno de UI da Android para construção de interfaces nativas.
- **API Olho Vivo**: Provedor de dados de transporte público em tempo real de São Paulo.
- **API Google Maps**: Integração com o Google Maps para visualização e interação com mapas.

## Requisitos

- **Android Studio**: IDE recomendada para desenvolvimento Android.

## Configuração do Ambiente de Desenvolvimento

1. **Clone o repositório**:

2. **Abra o projeto no Android Studio**:
    - Abra o Android Studio e selecione "Open an existing project".
    - Navegue até o diretório onde você clonou o repositório e selecione-o.

3. **Configure a API Key do Google Maps**:
    - Obtenha uma API Key do Google Maps na [Google Cloud Console](https://console.cloud.google.com/).
    - Adicione a API Key no arquivo `local.properties`:
      ```
      MAPS_API_KEY=YOUR_API_KEY_HERE
      ```

4. **Instale as dependências**:
    - No Android Studio, sincronize o projeto com o Gradle.
    - Certifique-se de que todas as dependências sejam baixadas corretamente.

## Guia de Uso da Aplicação

Depois de configurar o ambiente e executar o projeto, você pode começar a usar o aplicativo. Aqui está um guia passo-a-passo para ajudar você a entender como utilizar as funcionalidades principais:

### Navegação Inicial

#### Tela Principal

- Ao abrir o aplicativo, você será direcionado para a tela principal que exibe uma tela de boas vindas.

#### Menu

- Você pode escolher entre ver informações sobres os corredores de São Paulo ou sobre as linhas.

#### Corredor

- Você pode escolher entre todos os corredores de São Paulo
- Após pressionar o corredor, as paradas relacionadas serão exibidas no mapa.

#### Buscar Linhas de Ônibus

- Utilize a barra de pesquisa no topo da tela para buscar por linhas de ônibus específicas.
- Digite o número da linha ou o nome da parada e pressione a lupa.

#### Visualizar Paradas

- Após pressionar a linha escolhida, as paradas relacionadas serão exibidas no mapa.
- Toque uma vez em uma parada para criar uma rota entre a parada e sua localização, e toque uma segunda vez para ver informações detalhadas, todas as linhas que a 
parada atende e os horários de ônibus previstos.

### Exemplos de Uso

#### Exemplo 1: Verificar Horários de Ônibus

1. Na tela principal, busque pela linha "123".
2. Selecione uma parada da lista de resultados.
3. Verifique a previsão de chegada dos próximos ônibus na parada selecionada.

#### Exemplo 2: Roteiro de Viagem

1. Busque pela linha "456" e visualize as paradas no mapa.
2. Toque em diferentes paradas para ver a localização atual dos ônibus e planeje seu trajeto.

---
