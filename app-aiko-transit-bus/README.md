## DOCUMENTAÇÃO APP-AIK-TRANSIT-BUS

# Como iniciar o projeto
  Ao abrir o projeto abra o console do VSCODE e vá para a pasta destino:<br> <strong> cd app-aiko-transit-bus </strong> <br><br>
    <strong>Exculte o comando:</strong>

    
     npm run start 

Ao carregamento do terminal: <br> 
Abrir no celular: <strong>Escaneie o código QR com o aplicato ExpoGo.</strong> <br>
Para abrir no android stúdio ou programa da preferência aperte a letra A para execultar no android.

## O Aplicativo

<strong>(OBS:) O aplicativo está setado com as configurações no mapa para um região de são paulo para facilitar a visualização das paradas.</strong> <br>
Para trocar para a localização atual do usuário seguir esses passos: 

Ná pagina Home terá o componente <strong> MarkView</strong>  com a propriedade de: <strong>InitialRegion</strong> com: <strong>latitude e longitude.</strong><br>
Para realizar a troca modifique: <br>
<strong>Latitude</strong> por:  <strong>currentLocation.coords.latitude.</strong> <br> 
<strong>longitude</strong> por: <strong>currentLocation.coords.longitude </strong><br>

<br><br>

Ao abrir o aplicativo você verá a tela Home com o button superior esquerdo indicando o <strong>MENU</strong><br>
O button ao canto inferior indica uma volta de localização para sua localização atual no mapa. caso você movimente o mapa basta apertar no button para voltar a sua localização atual no mapa.<br>

O campo na parte inferior da home possui um button com " linhas e destinos " você pode apertar no button e ir para a tela de Busca onde pode buscar as linhas que desejar. <br>
Esse mesmo campo ao clicar em alguma parada exibida no mapa pelo ícone de um Onibus abrirá as linhas e horários daquela parada espécifica. <br>
Os ícones de onibus no mapa exibem as paradas conforme o range que o mapa esta sendo exibido. as paradas só são carregadas conforme o usuário movimenta o mapa para evitar requests desnecessários.<br><br> 

Ao clicar no menu ele irá abrir com três opções: mapa, rotas e buscar linha.<br>
Rotas e buscas linha levam para a mesma página. Rotas foi implementado por questão visual. não existe funcionalidade ao digitar no campo.<br>
Buscar linhas existe uma interatividade onde o usuário pode pesquisar pelo nome da linha ou pelo código da linha. 

## Paradas <br>

Ao clicar nas paradas será exibido as linhas e previsões de chegada dessa para específica.<br>
Ao clicar em uma linha dentro desse campo que será exibido, irá abrir a tela de Linhas. <br>
Nessa tela o usuário tem acesso a: proximos horários do onibus, localização atual no mapa do onibus e as paradas dessa linha específica. 

## Mapa <br>
O mapa da home tem essa funcionalidade: Se o usuário pinçar muito o mapa para cima exibirá um button para que ele aproxime mais o mapa para que as paradas sejam exibidas novamente


