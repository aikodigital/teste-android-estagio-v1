# README - SP Movement

---

## SP Movement

**Versão:** 1.0.0

### Objetivo

O SP Movement é um aplicativo desenvolvido para exibir dados em tempo real sobre o transporte público da cidade de São Paulo, utilizando a API Olho Vivo. A aplicação permite aos usuários visualizar a posição dos veículos, informações sobre as linhas de ônibus, pontos de parada, previsão de chegada e realizar pesquisas e filtros.

---

### Requisitos

- Flutter SDK 3.22.3
- Dart SDK
- Conexão com a internet para consumir a API Olho Vivo

---

### Instalação

1. **Clone o repositório**

    ```bash
    git clone https://github.com/jramuss/teste-android-estagio-v1
    cd sp_movement
    
    git checkout teste/josue-ramos
    ```

2. **Instale as dependências**

   Certifique-se de que você tem o Flutter instalado na versão 3.22.3. Para verificar a versão do Flutter instalada, execute:

    ```bash
    flutter --version
    ```

   Se a versão instalada não for a 3.22.3, você pode baixar a versão correta do Flutter aqui.

   Depois de instalar a versão correta do Flutter, execute o seguinte comando para instalar as dependências do projeto:

    ```bash
    flutter pub get
    ```


---

### Execução

1. **Execute o aplicativo**

   Conecte um dispositivo Android ou inicie um emulador. Em seguida, execute o seguinte comando para iniciar o aplicativo:

    ```bash
    flutter run
    ```


---

### Estrutura do Projeto

- **<module>/**
    - **models/**: Contém as classes de modelo.
    - **views/**: Contém os widgets da interface do usuário.
    - **stores/**: Contém a lógica de apresentação e gerenciamento de estado utilizando Mobx.
    - **repository/**: Contém a comunicação com o serviço externo para interagir com a API Olho Vivo.
    - **<module>.module/**: Configurações de injeção de dependência e gerenciamento das rotas.

---

### Tecnologias Utilizadas

- **Flutter**: Framework principal para o desenvolvimento do aplicativo.
- **Dio**: Biblioteca para requisições HTTP.
- **Mobx**: Biblioteca para gerenciamento de estado.
- **Flutter Modular**: Framework para injeção de dependência e navegação.
- **Flutter Map**: Biblioteca para integração de mapas.
- **Latlong2**: Biblioteca para manipulação de coordenadas geográficas.
- **Dio Cookie Manager**: Gerenciamento de cookies para Dio.
- **Persistent Bottom Nav Bar**: Implementação de barra de navegação persistente.

---

### Contato

Para mais informações, entre em contato com josuedasilvaramus@gmail.com.