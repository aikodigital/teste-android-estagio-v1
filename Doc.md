# Estrutura do Projeto

## Pacotes e Arquivos Principais

- **com.example.transportesp**: Pacote principal do aplicativo.
- **ui**: Contém as classes relacionadas à interface do usuário, como fragmentos e adaptadores.
- **data**: Contém as classes de dados que representam as informações manipuladas pelo aplicativo.
- **http**: Contém a interface da API e a configuração do Retrofit para comunicação com a API Olho Vivo.

## Principais Componentes

- **MainActivity**: A atividade principal que hospeda os fragmentos de navegação.
- **HomeFragment**: Fragmento que exibe o mapa com a localização dos veículos.
- **SearchFragment**: Fragmento que permite a busca de linhas de ônibus.
- **ArrivalPredictionFragment**: Fragmento que exibe as previsões de chegada dos veículos em um ponto de ônibus.
- **RetrofitInstance**: Classe singleton que fornece a instância do Retrofit configurada.
- **OlhoVivoApi**: Interface que define os endpoints da API Olho Vivo usados no aplicativo.

## Layouts

- **activity_main.xml**: Layout da atividade principal que contém o `FragmentContainerView` e o `BottomNavigationView`.
- **fragment_home.xml**: Layout do fragmento `HomeFragment` que contém o mapa.
- **fragment_search.xml**: Layout do fragmento `SearchFragment` que contém o campo de busca e a lista de resultados.
- **item_line.xml**: Layout do item de lista usado para exibir informações sobre uma linha de ônibus na `RecyclerView`.

## Dependências Utilizadas

- **Retrofit**: Biblioteca para facilitar a comunicação com APIs RESTful.
- **Google Maps**: API utilizada para exibir um mapa dentro do aplicativo, mostrando a localização dos veículos em tempo real.
- **Material Design**: Biblioteca utilizada para garantir que a interface do usuário siga as diretrizes de design da Google, proporcionando uma aparência moderna e consistente.
- **Coroutines**: Utilizadas para gerenciar tarefas assíncronas, como a autenticação e a busca de dados na API, de forma eficiente e sem bloquear a interface do usuário.

## Funcionalidades

### Autenticação

O aplicativo realiza a autenticação com a API Olho Vivo utilizando um token.

### Mapa com Localização de Veículos

O `HomeFragment` exibe um mapa com a localização atual dos veículos de transporte público.

### Busca de Linhas de Ônibus

O `SearchFragment` permite que os usuários busquem linhas de ônibus por número ou nome.

### Previsão de Chegada

O `ArrivalPredictionFragment` exibe previsões de chegada dos veículos em um ponto de ônibus específico, incluindo o tempo estimado de chegada.

## Como Rodar o Projeto

### Configuração do Ambiente

1. Instale o Android Studio.
2. Configure o SDK do Android.
3. Clone o repositório do projeto.

### Configuração do Projeto

1. Abra o projeto no Android Studio.
2. Configure a API Key do Google Maps no arquivo `google_maps_api.xml`.

### Execução do Projeto

1. Conecte um dispositivo Android ou configure um emulador.
2. Execute o projeto através do Android Studio.

### Capturas de Tela

<img src="https://github.com/OSIELJ/teste-android-estagio-v1/assets/99986952/171ff4d9-2153-486a-96ce-e921620002ad" alt="Screenshot 1" width="170"/>
<img src="https://github.com/OSIELJ/teste-android-estagio-v1/assets/99986952/85452903-d1b5-4271-a0f0-db1e079c9fb4" alt="Screenshot 2" width="170"/>
<img src="https://github.com/OSIELJ/teste-android-estagio-v1/assets/99986952/1bd641e9-fa8d-4f8c-b7ce-a705dd9f492b" alt="Screenshot 3" width="170"/>
<img src="https://github.com/OSIELJ/teste-android-estagio-v1/assets/99986952/74238cf2-840b-4035-9f35-8b71f7b922e2" alt="Screenshot 4" width="170"/>
