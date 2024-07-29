# Teste Android

![Aiko](imagens/aiko.png)

## O Desafio

O objetivo é criar um aplicativo que exiba dados sobre o transporte público da cidade de São Paulo, consultando a [API **Olho Vivo**](api.md) que provê informações em tempo real do monitoramento da frota de ônibus da cidade de São Paulo.

## Requisitos Implementados

* **Posições dos veículos**: Exibir no mapa onde os veículos estavam na sua última atualização.

* **Linhas**: Exibir informações sobre as linhas de ônibus.

* **Paradas**: Exibir os pontos de parada da cidade no mapa.

* **Previsão de chegada**: Dado uma parada informar a previsão de chegada de cada veículo que passe pela parada selecionada.

* **Pesquisa e Filtros**: Permitir que o usuário pesquise e filtre esses dados, interagindo com a interface.

## Tecnologias Utilizadas

* **Flutter**: Framework principal utilizado para o desenvolvimento da interface do usuário. <br>
* **Dart**: Linguagem de programação utilizada com o Flutter. <br>
* **OlhoVivo**: Api que provê informações em tempo real do monitoramento da frota de ônibus da cidade de São Paulo.
* **Google Maps**: Utilizado para definir a posição de ônibus e paradas

## Estrutura do Projeto

implementacao/ <br>
├── lib/<br>
│   ├── components/  - Componentes reutilizáveis da UI. <br>
│   ├── models/  - Modelos do app. <br>
│   ├── pages/  - Páginas do app. <br>
│   ├── services/  - Chamadas a API. <br> 
│   ├── main.dart<br>
├── assets/<br>
│   ├── images/ - Imagens usadas no app <br>
├── pubspec.yaml<br>
└── README.md<br>

## Uso

Para rodar o aplicativo, siga os passos abaixo:

1. **Instale as Dependências**: É necessário dar um **get packages** no `pubspec.yaml`. Execute o seguinte comando no terminal:

   ```bash
   flutter pub get

2. **Configurar a API do Google Maps**:
   - Para que o aplicativo funcione corretamente, é necessário adicionar a chave da sua API do Google Maps.
   - Para obte-la você pode seguir esse [**tutorial**](https://pub.dev/packages/google_maps_flutter).
   - Navegue até o diretório `android > app > src > main` e abra o arquivo chamado `AndroidManifest.xml`.
   - Adicione as seguintes linhas dentro da tag `<application>`:

     ```xml
     <meta-data 
         android:name="com.google.android.geo.API_KEY"
         android:value="[SUA CHAVE AQUI]"/>
     ```

     Certifique-se de substituir `[SUA CHAVE AQUI]` pela sua chave de API do Google Maps.

3. **Executar o Aplicativo**: Após as configurações, você pode rodar o aplicativo Flutter com o seguinte comando:

   ```bash
   flutter run

Agora o aplicativo estará pronto para exibir informações sobre o transporte público da cidade de São Paulo, utilizando a API Olho Vivo e o Google Maps no emulador que desejar.

## Download do app

Se prefir você pode baixar o apk.

Apk disponível em: https://drive.google.com/file/d/1gTLYOLlf9SabbSW0vTYYvb4DLLdBrGfG/view?usp=sharing

## Desenvolvido por Mateus de Almeida

Se tiver alguma dúvida ou sugestão, sinta-se à vontade para entrar em contato!

### Contato
- GitHub: https://github.com/Mateuszinnn
- LinkedIn: https://www.linkedin.com/in/mateusalmeidadias/
- Email: mateusdealmeida.trab@gmail.com

