// ignore_for_file: library_private_types_in_public_api

import 'package:flutter/material.dart';
import 'package:transportepublicosp/services/api_service.dart';

class SearchScreen extends StatefulWidget {
  const SearchScreen({super.key});

  @override
  _SearchScreenState createState() => _SearchScreenState();
}

class _SearchScreenState extends State<SearchScreen> {
  final TextEditingController _searchController = TextEditingController();
  List<Map<String, dynamic>> _searchResults = [];

  Future<void> _performSearch(String query) async {
    final apiService = ApiService();
    List<Map<String, dynamic>> results = await apiService.searchStops(query);

    setState(() {
      _searchResults = results;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: TextField(
          controller: _searchController,
          decoration: const InputDecoration(
            hintText: 'Pesquisar...',
            border: InputBorder.none,
          ),
          onChanged: (query) {
            if (query.length > 2) {
              _performSearch(query);
            } else {
              setState(() {
                _searchResults = [];
              });
            }
          },
        ),
      ),
      body: ListView.builder(
        itemCount: _searchResults.length,
        itemBuilder: (context, index) {
          final result = _searchResults[index];
          return ListTile(
            leading: const Icon(Icons.directions_bus),
            title: Text(result['stop_name']),
            subtitle: Text(result['stop_desc'] ?? ''),
            onTap: () {
              Navigator.pop(context, result);
            },
          );
        },
      ),
    );
  }
}
