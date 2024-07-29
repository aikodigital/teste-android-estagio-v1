class Previsao {
  final int idParada;
  final String nomeParada;
  final List<String> numerosOnibus; 
  final List<String> horariosChegada; 

  Previsao(this.idParada, this.nomeParada, this.numerosOnibus, this.horariosChegada);

  factory Previsao.fromJson(Map<String, dynamic> data, int idParada, String nomeParada) {
    return Previsao(
      idParada,
      nomeParada,
      [data['p'].toString()],
      [data['t'].toString()], 
    );
  }

  void adicionarPrevisao(String numeroOnibus, String horarioChegada) {
    numerosOnibus.add(numeroOnibus);
    horariosChegada.add(horarioChegada);
  }
}
