import 'package:flutter/material.dart';
import '../services/api_service.dart';

class BusSearchDelegate extends SearchDelegate {
  final ApiService apiService;

  BusSearchDelegate({required this.apiService});

  @override
  List<Widget> buildActions(BuildContext context) {
    return [
      IconButton(
        icon: Icon(Icons.clear),
        onPressed: () {
          query = '';
        },
      ),
    ];
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      icon: Icon(Icons.arrow_back),
      onPressed: () {
        close(context, null);
      },
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    return FutureBuilder(
      future: apiService.fetchBusLines(query),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(child: Text('Erro ao buscar linhas'));
        } else {
          final lines = snapshot.data as List<dynamic>;
          return ListView.builder(
            itemCount: lines.length,
            itemBuilder: (context, index) {
              final line = lines[index];
              return ListTile(
                title: Text('Linha ${line['lt']} - ${line['tp']}'),
                onTap: () {
                  close(context, line);
                },
              );
            },
          );
        }
      },
    );
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    return Container();
  }
}
