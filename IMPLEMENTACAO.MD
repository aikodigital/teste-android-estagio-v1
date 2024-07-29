## Features:

### Filtro de pessoa com PCD:

- Deve adicionar uma tela para configurar se deve retornar apenas onibus com auxilio para pessoas com PCD ✅

### Marcadores no mapa:
    No mapa deve ser mostrado marcadores de pontos de parada e ônibus com icones customizados para facilitar a legibilidade.

    Os marcadores de ônibus devem ser atualizados a cada 15 segundos
- Ao pressionar sobre o marcador de ponto de parada, deve mostrar na tela uma lista com todos os ônibus que passam por ele e a previsão de chegada em minutos destes. ✅

### Implementar uma listagem de linhas de ônibus filtrando eles por nome:
- Ao filtrar pelo nome será mostrado cards contendo o terminal de chegada e terminal de partida ✅

### Dependências:
[Flutter Map](https://pub.dev/packages/flutter_map) para visualizar o mapa utilizando tile providers.

[flutter Map Supercluster](https://pub.dev/packages/flutter_map_supercluster) para agrupar marcadores dentro de um raio especificado, evitando poluição visual.

[Flutter Bloc](https://pub.dev/packages/flutter_bloc) para facilitar a integração do padrão BloC para gerenciamento de estado.

[Provider](https://pub.dev/packages/provider) para facilitar a injeção do BloC na árvore de widgets.
