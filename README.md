# SPBusão

Aplicativo que aproveita a <a href= "https://www.sptrans.com.br/desenvolvedores/api-do-olho-vivo-guia-de-referencia/documentacao-api/#docApi-previsao">Api da SPTrans</a> para acessar dados sobre ônibus, pontos de parada, linhas e previsões de chegada em São Paulo, fornecendo essas informações em mapas e listas.

## Features
<ul>
 <li>Exibe no mapa os pontos de parada e as posições atuais dos ônibus.
 <li>Ao clicar em uma parada, mostra a previsão de chegada dos ônibus por linha.
 <li>Permite filtrar no mapa os pontos de parada e os ônibus.
 <li>Pesquisa e apresenta informações sobre as linhas de ônibus.
</ul>

## Tecnologias Utilizadas
<ul>
  <li> Requisições HTTP: Retrofit2
  <li> Arquitetura: MVVM
  <li> Mapa: SDK Maps
  <li> Injeção de dependência: Koin
  <li> Framework de Interface do Usuário: Jetpack Compose
  <li> Ferramenta de Análise Estática de Código: Kotlin Linter (lintKotlin)
</ul>

## Comandos
- `gradlew build`: Compilação do aplicativo.
- `gradlew lintKotlin`: Verificação de conformidade com as regras de codificação Kotlin.