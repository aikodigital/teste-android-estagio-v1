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