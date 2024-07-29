class BusLine{
  int id;
  bool isCircular;
  String firstLabel;
  int secondLabel;
  int lineDirection;
  String busLabelFromSecundaryToPrincipalTerminal;
  String busLabelFromPrincipalToSecundaryTerminal;

  BusLine(
      {required this.id,
      required this.isCircular,
      required this.firstLabel,
      required this.secondLabel,
      required this.lineDirection,
      required this.busLabelFromSecundaryToPrincipalTerminal, 
      required this.busLabelFromPrincipalToSecundaryTerminal});

  factory BusLine.fromJson(Map<String, dynamic> responseData) {
    return BusLine(
        id: responseData['cl'],
        isCircular: responseData['lc'],
        firstLabel: responseData['lt'],
        secondLabel: responseData['tl'],
        lineDirection: responseData['sl'],
        busLabelFromPrincipalToSecundaryTerminal: responseData['tp'],
        busLabelFromSecundaryToPrincipalTerminal: responseData['ts']);
  }
}