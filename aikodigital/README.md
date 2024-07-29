# Teste Android Aiko Digital

Este projeto é um app de mobilidade desenvolvido utilizando a API do Olho Vivo do estado de São Paulo e a API do Google Maps. Ele visa proporcionar informações em tempo real sobre o transporte público da cidade de São Paulo, integrando dados de localização e mapas para uma melhor experiência do usuário.

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

## Uso

1. **Execute o aplicativo**:
    - Conecte um dispositivo Android ou inicie um emulador.
    - Clique no ícone "Run" no Android Studio para compilar e instalar o aplicativo no dispositivo/emulador.

2. **Navegação no aplicativo**:
    - Use a barra de pesquisa para encontrar linhas de ônibus.
    - Visualize as paradas e a localização dos ônibus no mapa.

---
