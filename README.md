## Commands

| Command                   | Action               |
| :---------------------    | :------------------- |
| `npm install`             | Installs dependencies|
| `npm expo start`          | Start project        |
| `npm expo start --android`| Start project Android|
| `npm expo start --ios`    | Start project IOS    |
| `npm expo start --web`    | Start project Web    |      

Para rodar pode ser pelo emulador ou usando o app expo go disponivel na app store e na google play.


## Desenvolvimento

1. Estudo da Documentação da API:
Para iniciar o desenvolvimento, estudei a documentação da API para entender como fazer chamadas e integrar as funcionalidades necessárias na aplicação.

2. Testes com Postman:
Utilizei o Postman para testar a API e validar as requisições antes de integrá-las ao projeto. Isso garantiu que os endpoints estavam funcionando conforme esperado e ajudou a ajustar os parâmetros necessários para a aplicação.
(imagens/postiman.png)


3. Design de Interface com Figma:
Criei o layout da interface no Figma para garantir uma base sólida para o design da aplicação. O protótipo ajudou a definir a estrutura visual e a experiência do usuário.
(imagens/figma-print.png)

## Tecnologias e Ferramentas Utilizadas

1. React Native:
Escolhi React Native devido à sua flexibilidade e capacidade de desenvolvimento para ambas as plataformas (iOS e Android) com um único código base. Isso permitiu um desenvolvimento ágil e eficiente.

2. TypeScript:
Utilizei TypeScript para adicionar tipagem estática ao código, o que ajuda a prevenir erros comuns e melhorar a manutenção e escalabilidade do código.

3. Expo:
Expo foi usado para simplificar o processo de desenvolvimento e proporcionar um ambiente de desenvolvimento consistente e eficiente.

4. Styled Components:
Adotei o Styled Components para a estilização da aplicação. Esta biblioteca moderna permite uma abordagem modular e dinâmica para o CSS, facilitando a criação e a manutenção dos estilos dos componentes.

Estrutura do Código
1. Organização e Estrutura:
Busquei manter o código limpo e bem estruturado, seguindo boas práticas de desenvolvimento. O uso de inglês para nomenclaturas e mensagens ajuda a manter a consistência. Exceto em alguns momentos onde fiz uso do português por causa da api

2. Hook Personalizado:
Criei um hook personalizado chamado useOlhoVivoAPI para encapsular a lógica de chamadas à API. Isso simplifica o desenvolvimento, tornando o código mais modular e reutilizável.

3. Clean Code:
Tentei deixar o código organizado e fácil de entender, com a intenção de facilitar a manutenção e futuras modificações.























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

## O que é permitido

* Android Nativo (Java/Kotlin)

* React Native

* Native Script (Vue, Angular, etc)

* Flutter

* Xamarin

* Kivy

* Qualquer tecnologia complementar as citadas anteriormente são permitidas desde que seu uso seja justificável

## O que não é permitido

* Utilizar bibliotecas ou códigos de terceiros que implementem algum dos requisitos.

## Recomendações

* **Linter**: Desenvolva o projeto utilizando algum padrão de formatação de código.

## Extras

Aqui são listados algumas sugestões para você que quer ir além do desafio inicial. Lembrando que você não precisa se limitar a essas sugestões, se tiver pensado em outra funcionalidade que considera relevante ao escopo da aplicação fique à vontade para implementá-la.

* **Refresh automático**: Que as informações exibidas no aplicativo sejam atualizadas de tempo em tempo de forma transparente ao usuário

* **Cálculo de rotas**: Exibir a possível rota de um ou mais ônibus em relação a uma parada, ou do usuário em relação a uma parada (Utilizando API do Google Maps ou equivalentes)

* **Corredores**: Mostrar informações sobre os corredores de ônibus de SP.

* **Velocidade das vias**: Mostrar informações sobre as velocidades das vias.

* **Testes**: Desenvolva testes que achar necessário para a aplicação.

* **Documentação**: Gerar uma documentação da aplicação. A documentação pode incluir detalhes sobre as decisões tomadas, especificação das funcionalidades desenvolvidas, instruções de uso dentre outras informações que achar relevantes.

## Entrega

Para realizar a entrega do teste você deve:

* Relizar o fork e clonar esse repositório para sua máquina.
  
* Criar uma branch com o nome de `teste/[NOME]`.
  * `[NOME]`: Seu nome.
  * Exemplos: `teste/fulano-da-silva`; `teste/beltrano-primeiro-gomes`.
  
* Faça um commit da sua branch com a implementação do teste.
  
* Realize o pull request da sua branch nesse repositório.
