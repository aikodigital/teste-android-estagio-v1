# Welcome to your Expo app 👋

This is an [Expo](https://expo.dev) project created with [`create-expo-app`](https://www.npmjs.com/package/create-expo-app).

## Get started

1. Install dependencies

   ```bash
   npm install
   ```

2. Start the app

   ```bash
    npx expo start
   ```

In the output, you'll find options to open the app in a

- [development build](https://docs.expo.dev/develop/development-builds/introduction/)
- [Android emulator](https://docs.expo.dev/workflow/android-studio-emulator/)
- [iOS simulator](https://docs.expo.dev/workflow/ios-simulator/)
- [Expo Go](https://expo.dev/go), a limited sandbox for trying out app development with Expo

You can start developing by editing the files inside the **app** directory. This project uses [file-based routing](https://docs.expo.dev/router/introduction).

# Olho vivo bus

1. Sobre o projeto

- Teste desenvolvido com React Native(Expo) com typescript
- Decide usar o Expo(Tabs) por causa dos templates disponíveis tanto para projetos MVP quanto projetos que sai do MVP para ter a sua 1º versão e isso foi um decisão muito simples de fazer.
- Como todas as rotas do Api da Olho Vivo , fora a rota de login, usam o método GET, então em cada tela de navegação eu solicito 2 métodos(login e a rota Get a ser chamada). e foi simples e ao mesmo tempo difícil, por que o exemplo que é mostrado no anexo está desatualizado, mas consegui resolver e entender mais a fundo sobre a Api.
- As funcionalidades no momento eu apenas consegui fazer 3 telas de navegação.

1. A tela mapa onde mostra onde os veículos estão posicionados, a cada 30 segundos ele são atualizados onde os veículos estão
2. A tela de linhas onde exibe as informações sobre as linhas de ônibus.
3. A tela de paradas onde deveria exibir os pontos de parada da cidade no mapa. Mas eu não consegui finalizar devido a um problema na api que veio acontecer na madrugada do dia 4/7

## Instruções

1. Clone o projeto e na raiz do projeto execute o comando

```
npm i
```

e depois execute o

```
npm start
```

clique na letra S do seu teclado para executar o projeto com Expo Go.
O projeto independente do dispositivo ele via baixar o Expo Go e depois ele executar o projeto.

E pronto, agora só testar o app.

## observações

\*\*quando foi falo no readme do teste que não poderia usar biblioteca de terceiro, para mim já foi instantâneo usar React Native com Expo, porque é instalar e sair fazendo e como é apenas um teste foi mais fácil a decisão, eu estava entre o React Native cli e o nativo só que no nativo eu teria um dor de cabeça para consumir api e react native teria que fazer uma coisas chatas e iria consumir um pouco do tem que já era curto.

Desde eu agradeço a oportunidade, espero que eu possa evoluir no processo e contribuir com a empresa nesta ou em futuras oportunidades.

Também peço que me dê feedback para que eu venha observar o que eu errei nas minhas escolhas e observar os meu erros para que eu melhore.\*\*

