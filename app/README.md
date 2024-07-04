# # Olho Vivo - SP

## Inicialização
Para executar o projeto, utilize as ferramentas descritas na sessão **Ferramentas** e siga essas instruções:
No arquivo local.properties adicione as seguintes chaves:

GOOGLE_MAPS_API_KEY=AIzaSyCKJDJGZkTxi7ndpPjVXIaJ7mMEqSRvflo
OLHO_VIVO_API_KEY=8532e7ecca917d76b2bd81a38141922b3ee54ad0c3b4dc0ef6948c8838f4061c

> Para conseguir as chaves do Google Maps e da API do Olho Vivo siga as instruções em suas respectivas documentações, listadas na seção **Links importantes**.

## Linguagem utilizada
* [Kotlin](https://kotlinlang.org/docs/home.html)

## Ferramentas
* [Android Studio](https://developer.android.com/studio?hl=pt-br) - IDE para desenvolvimento.

## Links importantes
* [API do Olho Vivo](https://www.sptrans.com.br/desenvolvedores/api-do-olho-vivo-guia-de-referencia/) -  Guia de referência da API.

* [Maps SDK for Android](https://developers.google.com/maps/documentation/android-sdk/get-api-key?hl=pt-br) - Guia de referência da API.

* [Retrofit](https://square.github.io/retrofit/) - Um cliente HTTP para Android.

# Olho Vivo - SP

## Introdução

> O aplicativo Olho Vivo é projetado para facilitar a busca e visualização de linhas de ônibus em São Paulo, utilizando a API do Olho Vivo. Ele fornece uma interface amigável para que os usuários possam visualizar informações em tempo real sobre a posição dos ônibus do transporte público e pesquisar linhas de ônibus com base em critérios específicos.

## Análise técnica

### Descrição do ambiente técnico

O sistema é composto por uma interface Android que consome dados da API do Olho Vivo.

### Principais funcionalidades

* *F1* - Exibição dos Ônibus do transporte público de São Paulo no mapa.
* *F2* - Exibição de Linhas de Ônibus.
* *F3* - Pesquisa de Linhas de Ônibus.
* *F4* - Exibição de Informações em Tempo Real.

### Levantamento de requisitos

#### Requisitos Funcionais

Respeitando a proposta, o sistema deverá atender os seguintes requisitos:

* *RF1* - **Posições dos veículos**: Exibir no mapa onde os veículos estavam na sua última atualização.
* *RF2* - **Linhas**: Exibir informações sobre as linhas de ônibus.
* *RF3* - **Paradas**: Exibir os pontos de parada da cidade no mapa.
* *RF4* - **Previsão de chegada**: Dado uma parada informar a previsão de chegada de cada veículo que passe pela parada selecionada.
* *RF5* - **Pesquisa e Filtros**: Permitir que o usuário pesquise e filtre esses dados, interagindo com a interface.

## Padrão de projeto
* [ViewModel MVVM](https://coodesh.com/blog/dicionario/o-que-e-arquitetura-mvvm/) - O pattern MVVM é um padrão de projeto criado por John Grossman que visa estabelecer uma clara separação de responsabilidades e tonar um aplicativo mais fácil de dar manutenção.
