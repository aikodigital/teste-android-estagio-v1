# Teste Android

![Aiko](imagens/aiko.png)

Neste teste serão avaliados seus conhecimentos e a metodologia aplicada no desenvolvimento de aplicações mobile Android.

# Teste Android

Esse projeto foi desenvolvido em React Native

## Funcionalidades

* **Paradas** O aplicativo mostra as paradas com o ícone verde. Cada ícone pode ser clicado para abrir as previsões de chegada dos ônibus selecionados.

* **Previsão de Chegada:** Através do clique no ícone de parada é possível ver a previsão de chegada dos ônibus na parada selecionada.

<br>
<br>
<img src="../teste-android-estagio-v1/imagens/Screenshot_1722300677.png" alt="Descrição da Imagem" width="220" height="500">

<img src="../teste-android-estagio-v1/imagens/Screenshot_1722300632.png" alt="Descrição da Imagem" width="220" height="500">
<br>
<br>

* **Pesquisa de Linha:** A pesquisa de Linha filtra as linhas com base no input do usuário, fazendo uma pesquisa a cada mudança no texto. Os resultados da pesquisa são clicáveis levando, onde ao clicar os ônibus podemos ver a posição dos veículos.

<br>


<img src="../teste-android-estagio-v1/imagens/Screenshot_1722300673.png" alt="Descrição da Imagem" width="220" height="500">

<img src="../teste-android-estagio-v1/imagens/Screenshot_1722300641.png" alt="Descrição da Imagem" width="220" height="500">

<br>
<br>

* **Posições dos Veículos:** A localização dos veículos podem ser vistos através da pesquisa de linha. 
> Optei por colocar dessa forma, pois o Android Studio estava tendo dificuldades para renderizar tantos veículos na tela.

<br>
<br>

<img src="../teste-android-estagio-v1/imagens/Screenshot_1722300658.png" alt="Descrição da Imagem" width="220" height="500">

<br>
<br>

## Decisões:

Por morar em Manaus, tive que colocar a localização inicial fixada no estádio do Morumbi, para conseguir desenvolver as funcionalidades do app.

A barra de pesquisa na tela de Home, foi necessária para manter uma interface mais limpa, assim abrindo um Modal tanto para pesquisar as linhas, quanto para as previsões de chegada.

### Configurar o Ambiente

Certifique-se de que a outra pessoa tenha o Node.js e npm (ou yarn) instalados em sua máquina. Eles podem instalar o Node.js, que inclui o npm, [aqui](https://nodejs.org/). O yarn pode ser instalado globalmente com:

```sh
npm install -g yarn
```

### Clonar o Repositório

A outra pessoa deve clonar o repositório do projeto para sua máquina local. Se o projeto estiver hospedado em um serviço como GitHub, eles podem usar o seguinte comando:

```sh
git clone <URL-do-repositório>
```

### Instalar Dependências

Instale as bibliotecas necessárias usando npm ou yarn. Abra o terminal no diretório do projeto e execute:

```sh
npm install axios expo-location expo-localization react-native-maps react-native-vector-icons
```

### Configurar o Projeto

### Para Expo:

Certifique-se de que o Expo CLI está instalado globalmente:

```sh
npm install -g expo-cli
```

Inicie o projeto com Expo:

```sh
expo start
```

Isso abrirá o Expo Developer Tools no navegador e permitirá que eles executem o aplicativo no simulador ou em um dispositivo físico usando o QR code gerado.
`

## Sobre mim:

email: davipdocarmo@gmail.com
linkedin: https://www.linkedin.com/in/araujo-davi/