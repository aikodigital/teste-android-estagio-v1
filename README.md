# Transporte Fácil SP

## Abrindo o Projeto

Esse é um novo projeto [**React Native**](https://reactnative.dev), o bootstrap foi feito usando [`@react-native-community/cli`](https://github.com/react-native-community/cli).
>**Note**: Antes de prosseguir, garanta que você tenha seguido as instruções de [React Native - Environment Setup](https://reactnative.dev/docs/environment-setup) até o passo "Creating a new application".

Após o clonar o projeto, abra-o no VS Code, navegue até a raiz do projeto e instale as dependências:
```bash
npm install
```
Depois execute o seguinte comando:
```bash
npm run android
```
Se tudo estiver configurado corretamente, você verá o aplicado rodando no Android Emulator após algum tempo.

## Descrição do Projeto

O Transporte Fácil SP tem como objetivo exibir dados do transporte público rodoviário da cidade de São Paulo. Consultando a API Olho Vivo, o app permite aos usuários consultar linhas e paradas, oferecendo uma estimativa do horário de chegada dos ônibus nas paradas e sua visualização em um mapa.

## Telas, Funcionalidades e Comentários

Fiz um design bem simples para o aplicativo por conta da restrição de tempo e optei por usar branco e vermelho por serem as cores da SPTrans.
Aqui na tela principal do aplicativo temos duas opções: realizar uma busca por linha ou por parada.

<img src="/documentation_images/MainScreen1.png" alt="Tela Principal 1" width="300"/>

Ao selecionar a primeira opção, somos direcionados para a nossa primeira funcionalidade. Aqui o usuário pode buscar por linhas da cidade de São Paulo utilizando o código da linha e/ou o nome do ponto de partida/destino. Todas as linhas que se encaixam naquilo que foi pesquisado pelo usuário são exibidas junto com seus códigos, chegada, destino e sentido. Cada linha listada é acompanhada por dois botões. Um leva o usuário para uma tela que exibirá as paradas pelas quais a linha passa. O outro leva o usuário para uma tela onde é exibido um mapa onde são exibidas todas as paradas e todos ônibus em circulação daquela linha.

<img src="/documentation_images/SearchLineScreen1.png" alt="Buscar Linhas 1" width="300"/>     <img src="/documentation_images/SearchLineScreen2.png" alt="Buscar Linhas 2" width="300"/>     <img src="/documentation_images/SearchLineScreen3.png" alt="Buscar Linhas 3" width="300"/>

Ao clicar em Ver Paradas, a lista de todas as paradas daquela linha é exibida. Cada parada é exibida com seu nome, código e uma estimativa de chegada de todos os ônibus que estão à caminho de lá. Se uma parada não tiver nome, o código dela é exibido como nome. Aqui também tem um botão para ser direcionado ao mapa da linha, para que o usuário possa acompanhar o deslocamento do ônibus que ele já verificou estar à caminho.

<img src="/documentation_images/LineDetailsScreen1.png" alt="Paradas da Linha 1" width="300"/>

A tela do Mapa da Linha foi feita não apenas com a API Olho Vivo mas também com a API do Google Maps. Cada parada da linha selecionada é exibida com um marcador vermelho e cada ônibus em atividade é exibido com um marcador azul. Ao clicar em um marcador é possível obter mais informações sobre ele, como por exemplo o Nome e Código de uma parada e o Código de um veículo e se ele é ou não acessível à pessoas com deficiência. O ponto inicial do mapa é a coordenada padrão da cidade de São Paulo. Eu também fiz uma função para o mapa recarregar a cada 30 segundos, atualizando a posição dos veículos para permitir aos usuários melhor acompanahr seu movimento.

<img src="/documentation_images/LineMapScreen1.png" alt="Mapa da Linha 1" width="300"/>     <img src="/documentation_images/LineMapScreen2.png" alt="Mapa da Linha 2" width="300"/>     <img src="/documentation_images/LineMapScreen4.png" alt="Mapa da Linha 4" width="300"/>

Toda a busca por paradas é praticamente idêntica à busca por linhas. O campo de pesquisa aceita o nome e/ou o endereço das paradas. Todas as paradas que se encaixam naquilo que foi pesquisado pelo usuário são exibidas junto com seu nome, código e endereço. Cada parada listada é acompanha por um botão que leva o usuário para uma tela que exibirá todas as linhas que passam pela parada selecionada.

<img src="/documentation_images/SearchStopScreen1.png" alt="Buscar Paradas 1" width="300"/>     <img src="/documentation_images/SearchStopScreen2.png" alt="Buscar Paradas 2" width="300"/>

Todas as linhas são exibidas com seu código, origem e destino. Além disso, todos os veículos, de cada uma das linhas que estão à caminho da parada selecionada, terão sua estimativa de chegada na parada exibidas juntamente com as informações sobre acessibilidade a pessoas com deficiência.

<img src="/documentation_images/StopDetailsScreen1.png" alt="Linhas da Parada 1" width="300"/>
