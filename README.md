# Info Bus

O Info Bus é um Aplicativo Android que possibilita a vizualização de dados sobre os sitema de ônibus da cidade de São Paulo através da API [API **Olho Vivo**](api.md)
aqui vão algumas soluções implementadas pelo App:

* Vizualizar a listagem de todos os Veículos(Ônibus) Ativos em São Paulo.
* Pesquisar veículos por determinada Linha.
* Encontar previsões de chegada para determinada Parada.
* Vizualizar endereço da parada no Mapa.
* Vizualizar no Mapa onde Os veículos de determinada linha estão.

Este projeto foi desenvolvido com o objetivo de proporcionar uma solução flexível e de fácil utilização para vizualização mais fácil sobre horários e posição dos ônibus na cidade.

## instruções:

* Efetue um cadastro de Usuário com Login e Senha
* Ao logar, uma tela com um TabLayout estará visível com duas opcões para você arrastar e se encaminhar na tela.
* Na primeira tela é possível buscar(Botão) para vizualizar a listagem de ônibus ativos no determinado momento e seu prefixo. 
* Na primeira tela no botão para abrir o mapa, você terá como digitar uma determinada linha e ele irá plotar no Mapa quais véiculos e onde eles estão no momento.
* Na Segunda tela você poderá pesquisar pelo número da sua parada, a previsão de chegada dos ônibus na sua parada digitada.
* Na Segunda tela para ver no mapa, você poderá encontrar a localização da sua parada.
* Caso esqueça a senha do cadastro, va para recuperar conta -> digite seu e-mail -> espere uma mensagem para recuperação de conta chegar ao seu email.

## 🚀 Tecnologias e Arquitetura Utilizadas

### 🏛️ Arquitetura
- **MVVM (Model-View-ViewModel)**: A aplicação adota a arquitetura MVVM para manter uma separação limpa entre a View, ViewModel e Model.
- **Clean Code**: Nosso código segue os princípios de Clean Code, tornando-o fácil de ler e manter.

### 📚 Bibliotecas e Frameworks
- **Retrofit**: Utilizei Retrofit para realizar chamadas de API de maneira eficiente e segura.
- **Google Maps API**: Utilizei o Google Maps API para ter acesso ao mapa da google, para facilitar a demonstração das informações obtidas.
- **API OlhoVivo SPTrans**: Utilizei da API da SPTrans para acessar informações em tempo real do sistema de integração de ônibus da cidade de São Paulo
- **Firebase**: Utilizei o firebase para implementação do real Time Database e do Authentication, para autenticação e manuseamento da conta do usuário 
- **LiveData**: LiveData é empregado para notificar mudanças de dados na ViewModel, garantindo uma UI suave e reativa.
- **Navigation**: O Navigation Component facilita a navegação entre telas, tornando o fluxo de navegação mais gerenciável, incluindo a navegação entre Fragments.
- **Lifecycle**: O Android Lifecycle é integrado para gerenciar ciclos de vida de componentes, garantindo um comportamento correto em relação às atividades e fragmentos.

🤝 Contribuindo
Sinta-se à vontade para contribuir com o meu projeto! para mais informações mande um Email para jpedro.rpessoa@gmail.com 
