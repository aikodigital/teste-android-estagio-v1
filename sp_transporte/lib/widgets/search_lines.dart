// ignore_for_file: prefer_const_constructors, use_key_in_widget_constructors, library_private_types_in_public_api, avoid_print

import 'package:flutter/material.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:provider/provider.dart';
import 'package:sp_transporte/services/services.dart';
import 'package:sp_transporte/utils/provider.dart';

class SearchLines extends StatefulWidget {
  final void Function(String)? onSuggestionSelected;

  const SearchLines({super.key, this.onSuggestionSelected});
  @override
  _SearchLinesState createState() => _SearchLinesState();
}

class _SearchLinesState extends State<SearchLines> {
  final TextEditingController _controller = TextEditingController();
  List<Map<String, dynamic>> _placeList = [];

  Future<void> getSuggestions(String input) async {
    String baseUrl = 'https://aiko-olhovivo-proxy.aikodigital.io';
    SpTransService().authenticate();
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/Linha/Buscar?termosBusca=$input'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
      );

      if (response.statusCode == 200) {
        final dynamic responseData = jsonDecode(response.body);

        if (responseData is List) {
          setState(() {
            _placeList = responseData.cast<Map<String, dynamic>>();
          });
        } else {
          throw Exception('Falha ao carregar dados');
        }
      } else {
        throw Exception(
            'Falha ao carregar dados da linha: ${response.statusCode}');
      }
    } catch (e) {
      print('Error fetching data: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        TypeAheadField(
          textFieldConfiguration: TextFieldConfiguration(
            controller: _controller,
            decoration: InputDecoration(
              hintText: 'Exemplo: 8000, Barra Funda ou Ramos',
              labelText: 'Buscar linhas',
              border: OutlineInputBorder(),
            ),
          ),
          suggestionsCallback: (String pattern) async {
            await getSuggestions(pattern);
            return _placeList
                .map((place) =>
                    '(${place['cl']}) ${place['tp']} -> ${place['ts']}')
                .toList();
          },
          itemBuilder: (BuildContext context, suggestion) {
            return ListTile(
              title: Text(suggestion),
            );
          },
          onSuggestionSelected: (suggestion) {
            final regex = RegExp(r'\((.*?)\)');
            final match = regex.firstMatch(suggestion);
            if (match != null) {
              final linhaValue = match.group(1);
              Provider.of<LinhaProvider>(context, listen: false)
                  .setLinha(linhaValue!);
            }
            setState(() {
              _controller.text = suggestion;
            });
            if (widget.onSuggestionSelected != null) {
              widget.onSuggestionSelected!(suggestion);
            }
          },
          noItemsFoundBuilder: (BuildContext context) {
            return Padding(
              padding: const EdgeInsets.all(10.0),
              child: Text(
                '...',
                style: TextStyle(fontSize: 14),
              ),
            );
          },
        ),
      ],
    );
  }
}
