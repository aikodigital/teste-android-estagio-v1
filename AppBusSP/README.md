# BusSP

Aplicativo desenvolvido para o teste de estágio desenvolvedor android da Aiko.

## Descrição

O BusSP foi criado para exibir dados do transporte público de São Paulo, como base na API Olho Vivo, os mapas são gerados utilizando a API do Google Maps. Entre as principais funcionalidades temos:

- Buscar por Linhas;
- Buscar por Paradas;
- A previsão de chegada dos ônibus a parada, com a visualização das paradas no mapa;
- A visualização dos ônibus no mapa, sendo atualizado a cada um minuto.

## Requisitos

1. Android Studio:

* Microsoft® Windows® 8/10/11 de 64 bits
* Arquitetura de CPU x86_64; Intel Core de segunda geração ou mais recente ou CPU AMD com suporte a
* Hipervisor do Windows
* Pelo menos 8 GB de RAM
* Mínimo de 8 GB de espaço em disco disponível (ambiente de desenvolvimento integrado + SDK do
* Android + Android Emulator)
* Resolução de tela mínima de 1.280 x 800

2. SDK

3. JVM

## Estrutura do Projeto

```plaintext
├── App
├── data
│   ├── di
│   │   └── RepositoryModule.kt
│   ├── remote
│   │   ├── di
│   │   │   └── NetworkModule
│   │   ├── remote
│   │   │   └── response
│   │   │       ├── Corredor.kt
│   │   │       ├── Empresa.kt
│   │   │       ├── Linha.kt
│   │   │       ├── Parada.kt
│   │   │       ├── Posicao.kt
│   │   │       ├── PrevisaoChegada.kt
│   │   │       └── PrevisaoChegadaLinha.kt
│   │   ├── RemoteDataSource
│   │   └── TransService
│   ├── repository
│   │   ├── TransRepository
│   │   └── TransRepositoryImpl
├── model
├── ui
│   ├── main
│   │   ├── activity
│   │   │   ├── CorredoresActivity
│   │   │   ├── EmpresasActivity
│   │   │   ├── InforOnibusActivity
│   │   │   └── ParadasActivity
│   │   ├── adapter
│   │   │   ├── CorredorAdapter
│   │   │   ├── LinhasAdapter
│   │   │   ├── ParadasAdapter
│   │   │   └── PrevisaoParadaAdapter
│   │   ├── viewmodel
│   │   │   ├── AuthenticationViewModel
│   │   │   ├── BuscarLinhaViewModel
│   │   │   ├── CorredorViewModel
│   │   │   ├── EmpresaViewModel
│   │   │   ├── ParadasViewModel
│   │   │   ├── PosicaoViewModel
│   │   │   └── PrevisaoViewModel
│   ├── utils
│   │   └── dialogs
│   │       └── Dialogs
│   └── MainActivity
├── AndroidManifest.xml
└── res
```

## Funcionalidaes

1. Buscar Linhas: realizar a buscar pelas linhas, ao abrir o aplicativo a
   função `performReverseGeocoding` realizar a buscar pelo nome da localidade pela latitude e
   longitude. Ao digitar a linha, uma lista de linhas serão exibidas, ao clicar, a tela com as
   paradas e criada.

[b1](/imagens/Screenshot_20240702_200835.png) [b2](/imagens/Screenshot_20240702_200946.png) [b3](/imagens/Screenshot_20240702_201012.png) [b4](/imagens/Screenshot_20240702_201027.png)

2. Buscar Paradas: realizar a buscar pelas paradas, podendo ser por: nome; endereço, código e
   corredor. Foi utilizado um filtro para alterar o resultado. Os dados da parada são exibidos numa
   lista horizontal, contendo o código da parada, um ícone da representação no mapa e o endereço.

[p1](/imagens/p1.png) [p2](/imagens/p2.png) [p3](/imagens/p3.png)

3. Informações sobre ônibus: buscar a previsão dos ônibus com base na parada, mostrando os dados da
   linha e do ônibus. Mostra na tela as informações de trajetória dos ônibus no mapa, para a
   atualização das posições foi utilizado um Coroutines.

[i1](/imagens/i1.png) [i2](/imagens/i2.png) [i3](/imagens/i3.png)

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais
detalhes.
