# Transporte Público SP

Este aplicativo exibe dados sobre o transporte público da cidade de São Paulo, utilizando a API Olho Vivo para fornecer informações em tempo real sobre a frota de ônibus.

## Índice

1. [Introdução](#introdução)
2. [Decisões de Design](#decisões-de-design)
3. [Funcionalidades Desenvolvidas](#funcionalidades-desenvolvidas)
4. [Instruções de Uso](#instruções-de-uso)
5. [Estrutura do Projeto](#estrutura-do-projeto)
6. [Código Fonte](#código-fonte)
7. [Considerações Finais](#considerações-finais)

## Introdução

Este documento descreve a implementação de um aplicativo de transporte público para a cidade de São Paulo, utilizando a API Olho Vivo. O aplicativo exibe informações em tempo real sobre a frota de ônibus, incluindo posições dos veículos, linhas de ônibus, paradas, corredores e velocidades das vias.

## Decisões de Design

1. **Tecnologia Utilizada**: O aplicativo foi desenvolvido utilizando Flutter devido à sua popularidade e capacidade de criar aplicativos multiplataforma com uma única base de código.
2. **Google Maps**: Utilizamos o plugin `google_maps_flutter` para exibir mapas e marcadores.
3. **Gerenciamento de Estado**: Utilizamos o `setState` para gerenciamento simples de estado.
4. **Autenticação**: A autenticação com a API Olho Vivo é realizada uma vez durante a inicialização do aplicativo.

## Funcionalidades Desenvolvidas

1. **Posições dos Veículos**: Exibe no mapa as posições dos veículos na última atualização.
2. **Linhas de Ônibus**: Permite pesquisar e exibir informações sobre as linhas de ônibus.
3. **Paradas**: Exibe os pontos de parada da cidade no mapa.
4. **Previsão de Chegada**: Informa a previsão de chegada de cada veículo em uma parada selecionada.
5. **Corredores**: Mostra informações sobre os corredores de ônibus de São Paulo.
6. **Velocidade das Vias**: Mostra informações sobre as velocidades das vias.

## Instruções de Uso

1. **Inicialização**: Ao abrir o aplicativo, a autenticação com a API Olho Vivo é realizada.
2. **Pesquisa de Linhas**: Utilize a barra de pesquisa para encontrar linhas de ônibus.
3. **Exibição de Paradas**: Clique no botão "Paradas" para exibir os pontos de parada no mapa.
4. **Corredores e Velocidades**: Utilize os botões "Corredores" e "Velocidades" para exibir essas informações no mapa.