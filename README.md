## 🦾 DESAFIO 
**Proposta para a fase técnica do processo seletivo para Estágio na Aiko Digital:** Construir uma aplicação mobile que integre a API da SPTrans e implemente recursos úteis para os usuários, conforme direcionamentos repassados pela empresa.

## 🛠️ TECNOLOGIAS 
- **Expo (v51.0.0):** IDE para desenvolvimento de aplicativos mobile em Android, iOS, entre outros.
- **Bibliotecas principais:**
  - [React Native](https://www.npmjs.com/package/react-native)
  - React Navigation ([native](https://www.npmjs.com/package/@react-navigation/native), [native-stack](https://www.npmjs.com/package/@react-navigation/native-stack), [drawer](https://www.npmjs.com/package/react-native-drawer), [bottom-tabs](https://www.npmjs.com/package/@react-navigation/bottom-tabs))
  - [React Native Swipe Gestures](https://www.npmjs.com/package/react-native-swipe-gestures)
  - [React Native Gesture Handler](https://www.npmjs.com/package/react-native-gesture-handler)
  - [Axios](https://www.npmjs.com/package/react-native-axios)
  - Demais bibliotecas detalhadas no [`package.json`](https://github.com/FeedUp-Hub/FeedUp-Mobile/blob/main/package.json)

## 📐 ARQUITETURA 
O aplicativo foi construído utilizando uma arquitetura componentizada conforme estrutura abaixo:
  - _assets_ (imagens, logos e ícones utilizados no aplicativo)
    - _images_
    - _logos_
  - _config_ (serviços para integração com API)
    - _services_
  - _layouts_ (implementações das telas do aplicativo).
  - _routes_ (implementações as rotas de navegação do menu fixo no rodapé (bottom menu) do projeto e o menu lateral (drawer menu)).
  - _styles_ (implementações dos estilos utilizados pelo aplicativo).
  - _App.jsx_ (implementação do estado inicial do aplicativo, direcionando as rotas de navegações (routes)).
  - _Package.json_ (biblioteas e dependências)

## 📋 RECURSOS  
- **Loading**
  - Tela de carregamento de aplicativo, implementado com a criação de tela de splash.
- **Navegação**
  - Menu no rodapé para acesso as páginas principais (Tempo real, Linhas, Paradas, Previsões), implementado com Bottom Tab Navigator.
  - Menu lateral para acesso a páginas secundárias (Velocidade), implementado com Drawer Navigator.
  - Configurações de API pública em arquivo ConfigAPI.js, separando a implementação da tela com recuros de autenticação e definições das chamadas.
- **Refresh Automático**
  - O recurso de chamada da API a cada 5 segundos foi adicionado na 1ª página do aplicativo, assim o usuário conseguirá acompanhar o movimento da linha de ônibus buscada em near real-time.

## 🖼️ TELAS  
- **Splash + Onboarding:** Telas de carregamento inicial do aplicativo e carregamento da tela de onboarding.

  <img src="https://github.com/fsaantiago/teste-fernando-santiago/blob/teste/fernando-santiago/assets/images/splash_onboarding.gif" width="200" height="400">

- **Tempo Real:** Tela inicial do aplicativo que renderiza um mapa mostra a posição em tempo real dos veículos conforme pesquisa pela linha de ônibus. O mapa é atualizado a cada 5 segundos.

<img src="https://github.com/fsaantiago/teste-fernando-santiago/blob/teste/fernando-santiago/assets/images/posicoes_veiculos_realtime.gif"  width="200" height="400">

- **Linhas:** Tela que permite ao usuário pesquisar pela linha de ônibus e receber as informações detalhadas da linha, incluindo um botão para acessar as paradas possíveis de cada linha.
  
<img src="https://github.com/fsaantiago/teste-fernando-santiago/blob/teste/fernando-santiago/assets/images/linhas.gif"  width="200" height="400">

- **Paradas:** Tela que renderiza um mapa e mostra as paradas disponíveis para a linha pesquisada.

<img src="https://github.com/fsaantiago/teste-fernando-santiago/blob/teste/fernando-santiago/assets/images/paradas.gif"  width="200" height="400">

- **Previsões:** Tela que permite ao usuário descobrir quais os próximos horários disponíveis para a linha de ônibus pesquisada.

<img src="https://github.com/fsaantiago/teste-fernando-santiago/blob/teste/fernando-santiago/assets/images/previsao.gif"  width="200" height="400">

- **Corredores:** Opção disponível via menu lateral, onde usuário pode ter acesso aos corredores da cidade.

<img src="https://github.com/fsaantiago/teste-fernando-santiago/blob/teste/fernando-santiago/assets/images/corredores.gif"  width="200" height="400">

## 🚀 EXECUTANDO O PROJETO  
Para executar o aplicativo localmente é necessário seguir os passos abaixo:

`1. Realize um clone desse repositório na sua máquina local, garantindo que ele seja movido para um diretório próprio.`

`2. Abrir snack.expo.dev e importar os arquivos de código`

`3. Instalar as dependências conforme bibliotecas descritas em package.json.`
    
`4. O aplicativo será exibido na tela do emulador`
    
`7. Parâmetros utilizados nas pesquisas:`

    Linha: 8000
    Parada: 2506
    Previsão: 1273
    
## ✅ TESTES  


## 🧑‍💻 DESENVOLVEDOR  
Fenando Santiago ([Linkedin](https://www.linkedin.com/in/fernando-santiago/)) / Contato: fernando.santiago770@gmail.com / (31) 98741-3780
