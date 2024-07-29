# Documentação do Projeto de Teste Android - Aiko

---

### **Nome do Projeto:** SP Movement

**Versão:** 1.0.0

### Objetivo

O objetivo deste projeto é desenvolver um aplicativo que exiba dados em tempo real sobre o transporte público da cidade de São Paulo, utilizando a API Olho Vivo. A aplicação deve permitir aos usuários visualizar a posição dos veículos, informações sobre as linhas de ônibus, pontos de parada, previsão de chegada e realizar pesquisas e filtros.

### Arquitetura

**Arquitetura MVVM com o Flutter Modular**

O projeto segue a arquitetura MVVM (Model-View-ViewModel) utilizando o Flutter Modular para injeção de dependências e navegação.

- **Model**: Representa os dados da aplicação e é responsável por gerenciar as regras de negócio. Ele contém a lógica para buscar, armazenar e manipular dados, frequentemente interagindo com serviços externos, como a API Olho Vivo.
- **View(Pages)**: A camada de visualização que apresenta os dados ao usuário. No Flutter, isso envolve a construção de widgets.
- **ViewModel(Controller/Store)**: Atua como um intermediário entre a View e o Model. Ele mantém a lógica de apresentação, manipula os dados recebidos do Model e expõe esses dados de uma forma que a View possa consumir facilmente. Utiliza o Mobx para gerenciar o estado reativo, garantindo que as atualizações de dados sejam refletidas automaticamente na interface do usuário.

O **Flutter Modular** é utilizado para organizar o código em módulos, facilitando a manutenção e escalabilidade do aplicativo. Ele gerencia a injeção de dependências e a navegação entre as diferentes partes da aplicação, promovendo um design desacoplado e modular.

### Tecnologias Utilizadas

- **Flutter**: Framework principal para o desenvolvimento do aplicativo.
- **Dio**: Biblioteca para requisições HTTP.
- **Mobx**: Biblioteca para gerenciamento de estado.
- **Flutter Modular**: Framework para injeção de dependência e navegação.
- **Flutter Map**: Biblioteca para integração de mapas.
- **Latlong2**: Biblioteca para manipulação de coordenadas geográficas.
- **Dio Cookie Manager**: Gerenciamento de cookies para Dio.
- **Persistent Bottom Nav Bar**: Implementação de barra de navegação persistente.

### Detalhes de Tecnologia + Motivo de Utilização

- **Flutter**: Escolhido pela sua capacidade de desenvolver aplicações nativas de alta performance com uma única base de código para Android e iOS, além de oferecer uma vasta biblioteca de widgets.
- **Dio**: Utilizado pela sua flexibilidade e facilidade na realização de requisições HTTP, além de possuir uma boa integração com gerenciadores de estado como Mobx.
- **Mobx**: Selecionado por sua simplicidade e reatividade no gerenciamento de estado, permitindo uma atualização eficiente da interface do usuário.
- **Flutter Modular**: Facilita a organização do código e a injeção de dependências, seguindo o padrão de arquitetura MVVM.
- **Flutter Map**: Permite a integração de mapas de forma eficiente e personalizável, essencial para exibir a posição dos veículos e pontos de parada.
- **Latlong2**: Utilizada para manipulação precisa de coordenadas geográficas.
- **Dio Cookie Manager**: Gerencia cookies automaticamente, simplificando a autenticação e outras operações que dependem de cookies.
- **Persistent Bottom Nav Bar**: Mantém a barra de navegação visível e funcional durante a navegação entre diferentes telas do aplicativo.

### Serviços Externos Utilizados

- **API Olho Vivo**: Fornece dados em tempo real sobre a frota de ônibus de São Paulo, incluindo posições dos veículos, informações sobre linhas, pontos de parada e previsões de chegada.