
# SP no Ponto  

Aplicativo que combina a API do Google Maps e do Olho Vivo  
escrito em Kotlin.  

## Build

Para rodar o app é preciso configurar duas chaves:  
#### 1) Chave da API OlhoVivo
Pode ser obtida por esse [link](http://www.sptrans.com.br/desenvolvedores/api-do-olho-vivo-guia-de-referencia/)  
Em seguida, deve-se adicionar ao arquivo **local.properties** a  
propriedade **O_KEY=<Sua chave>**. É importante destacar que  
o nome não pode ser alterado.

#### 2) API do Google Maps
Pode ser obtida seguindo as intruções do [GCP](https://developers.google.com/maps/documentation/android-sdk/get-api-key).
Em seguida, deve-se adicionar ao arquivo **local.properties** a propriedade **MAPS_API_KEY=<Sua chave>**. É  
importante destacar que o nome não pode ser alterado.  
Além disso, são necessárias duas chaves SHA-1 para a API  
do Google Maps: uma para debug, outra para release.

#### 3) Arquivo key.properties
É necessário criar um arquivo chamado **key.properties** na raíz  
do projeto e inserir as seguintes informações:  
keyAlias=<Valor>  
keyPassword=<Sua senha>  
storeFile=<Local da chave>  
storePassword=<Sua senha>  
  
A chave pode ser obtida de acordo com a [documentação do Android](https://developer.android.com/studio/publish/app-signing)  

  
Com essas duas API e os arquivos **local.properties** e **key.properties**  corretamente configurados, o app está pronto para rodar.  
A build final de release tem por volta de 1.7MB


## Testes

Foram adicionados alguns testes ao app, a maioria deles sendo de **usecases**.


## Tecnologias e bibliotecas

#### Coroutines e Flows

O app usa fortemente as [Coroutines](https://developer.android.com/kotlin/coroutines) em conjunto com [Flows](https://developer.android.com/kotlin/flow) para estruturar os usecases, juntos aos view models. As coroutines permitem executar trabalho em outra thread,  
que não a Main Thread. Dessa forma, o app permanece responsivo durante as calls das APIs.
Além disso, as coroutines foram fundamentais para desenvolver  
a feature de atualizar automaticamente as informações do mapa.
#### Injenção de dependência com Hilt

As dependências das diversas classes são injetadas usando a biblioteca [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)

#### Jetpack Compose
O app foi construído com [Jetpack Compose](https://developer.android.com/jetpack/compose). Dessa forma, há apenas uma **Activity**  
e nenhum fragmento, uma vez que a UI foi escrita em Kotlin. Vale mencionar  
que o **composable** **ListItem** ainda está em modo experimental e, por isso,  
algumas funções têm a anotação **@ExperimentalMaterialApi**.

#### Plugin Kotlin data class file from JSON
Esse plugin do Android Studio permite converter a resposta em JSON das APIs  
em data classes, que podem ser encontradas em **data/models** 
## Estrutura
O app é estrurado em 3 diferentes camadas principais:  
data, domain e presentation.

#### Data
Responsável pela fonte das informações, seja por meio de APIs ou banco de  
dados locais. Além disso, é responsável também pelos **models** obtidos das  
APIs com ajuda do Plugin.

#### Domain
Responsável pelas entidades, abstrações e usecases.

#### Presentation
Responsável pela UI.

#### di
Arquivo contendo o container para gerar as dependências do **Hilt**.

#### common
Arquivos comuns, no caso **Exceptions** customizadas.

#### Constants
Responsável pelas constantes do projeto.


## Demonstração em vídeo

<a href="https://firebasestorage.googleapis.com/v0/b/dartplayer-94fe5.appspot.com/o/Aiko%2Faiko1.mp4?alt=media" target="_blank">
<img src="https://firebasestorage.googleapis.com/v0/b/dartplayer-94fe5.appspot.com/o/Aiko%2Faiko.webp?alt=media" alt="Demonstração em vídeo" width="250" height="auto" />
</a>

