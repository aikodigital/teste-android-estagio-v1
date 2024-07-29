import 'domain/bus_line.dart';

class  BusLineModel extends BusLine {
  BusLineModel({
     codeLine,
    lineCircular,
     letter,
    codeLetter,
     direction,
    letterStartEnd,
    letterEndStart,
  }): super(
    codeLine: codeLine,
    lineCircular: lineCircular,
    letter: letter,
    codeLetter: codeLetter,
    direction: direction,
    letterStartEnd: letterStartEnd,
    letterEndStart: letterEndStart,
  );

  factory BusLineModel.fromJson(Map<String, dynamic> json){
    return BusLineModel(
      codeLine: json['cl'],
      lineCircular: json['lc'],
      letter: json['lt'],
      codeLetter: json['tl'],
      direction: json['sl'],
      letterStartEnd: json['ts'],
      letterEndStart: json['tp'],
    );
  }

}