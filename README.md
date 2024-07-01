# BusLive

Meu sistema de monitoramento de ônibus em São Paulo utiliza a API do Olho Vivo para oferecer informações em tempo real sobre a localização e o status dos ônibus na cidade. Essa tecnologia permite aos usuários acompanhar de forma eficiente e precisa a movimentação dos ônibus, facilitando o planejamento de viagens e melhorando a experiência dos passageiros.

## Funcionalidades

- Visualização em Tempo Real: O sistema proporciona uma visualização dinâmica da localização atual de todos os ônibus em operação na cidade de São Paulo. Utilizando dados atualizados da API do Olho Vivo, os usuários podem ver onde cada ônibus está em tempo real, cada atualização é feita de minuto em minuto (60 segundos).

- Detalhes dos Ônibus: Além da localização, o sistema fornece detalhes sobre cada ônibus, como número da linha e destino. Essas informações são essenciais para os passageiros planejarem suas viagens com precisão.

- Detalhes das Paradas: Além da localização, o sistema fornece detalhes sobre cada parada, como qual a linha, destino e horário dos próximos ônibus. Essas informações são essenciais para os passageiros planejarem suas viagens com precisão.

- Mapa Interativo: Um mapa interativo permite que os usuários visualizem os ônibus em um contexto geográfico. Os ônibus são representados por marcadores que mostram informações relevantes ao serem clicados, facilitando a navegação e a compreensão da rede de transporte público.

## Benefícios para os Usuários:

- Planejamento de Viagens Eficiente: Os usuários podem verificar o status dos ônibus antes de sair, economizando tempo e evitando esperas desnecessárias.

- Melhoria na Experiência do Passageiro: Com acesso a informações precisas e atualizadas, os passageiros podem viajar com mais confiança e conveniência.

- Redução do Impacto Ambiental: Ao otimizar o uso do transporte público, o sistema contribui para a redução do tráfego e das emissões de carbono na cidade.

## Screenshots

- SplashScreen

<img src="https://github.com/AnthonySaDev/aiko-challenge/blob/main/images/splash.png" alt="Splash Screen" width="500">

- Onboard

<img src="https://github.com/AnthonySaDev/aiko-challenge/blob/main/images/onboard.png" alt="Onboard Screen" width="500">

- Dashboard

<img src="https://github.com/AnthonySaDev/aiko-challenge/blob/main/images/dashboard.png" alt="Dashboard Screen" width="500">

- Dashboard - Rotas

<img src="https://github.com/AnthonySaDev/aiko-challenge/blob/main/images/dashbord-rotas.png" alt="Dashboard Rotas Screen" width="500">

- Dashboard - Corredores

<img src="https://github.com/AnthonySaDev/aiko-challenge/blob/main/images/dashboard-corredores.png" alt="Dashboard Corredores Screen" width="500">

- Detalhes dos Corredores

<img src="https://github.com/AnthonySaDev/aiko-challenge/blob/main/images/detalhes.png" alt="Detalhes dos Corredores Screen" width="500">

- Listagem das Linhas para a Rota selecionada

<img src="https://github.com/AnthonySaDev/aiko-challenge/blob/main/images/linhas.png" alt="Listagem das Linhas Screen" width="500">

- Monitoramento de Ônibus

<img src="https://github.com/AnthonySaDev/aiko-challenge/blob/main/images/monitoramento.png" alt="Monitoramento de Ônibus Screen" width="500">

## Demonstração

[Assista ao vídeo de demonstração](https://youtu.be/9MHbP2RgKfI) do aplicativo BusLive.

## Aprendizados

Durante o desenvolvimento deste projeto, adquiri valiosos conhecimentos e habilidades que foram fundamentais para a implementação de funcionalidades robustas e eficientes. Algumas das principais lições aprendidas incluem:

- Atualização em Tempo Real: Implementar atualizações em tempo real foi essencial para garantir que os dados exibidos no aplicativo refletissem com precisão a localização e o status atualizados dos ônibus. A integração com a API do Olho Vivo permitiu receber e processar informações, proporcionando uma experiência de usuário mais dinâmica e atualizada.

- Filtragem de Dados em Requisições: A habilidade de filtrar e processar dados diretamente nas requisições à API foi crucial para otimizar o desempenho e a relevância das informações apresentadas aos usuários. Implementar filtros eficazes permitiu personalizar as consultas de acordo com as necessidades específicas do usuário, melhorando a eficiência e a usabilidade do aplicativo.

- Animações Fluidas: A integração de animações fluidas foi crucial para proporcionar uma experiência de usuário mais agradável e interativa. A utilização de bibliotecas como react-native-reanimated e moti permitiu criar transições suaves entre telas e efeitos visuais que aumentaram a usabilidade e a atratividade do aplicativo.

- Roteamento Navegacional: Dominar técnicas avançadas de roteamento foi fundamental para garantir a navegabilidade intuitiva dentro do aplicativo. A utilização do expo-router possibilitou implementar fluxos de navegação claros e eficientes, adaptados às necessidades específicas de cada tela e funcionalidade e permitindo o envio de parâmetros de Rota.
