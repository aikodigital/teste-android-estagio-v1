import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../models/curridor_model.dart';
import '../screens/map_screen.dart';
import '../services/olho_vivo_service.dart';

class CorridorTab extends StatefulWidget {
  @override
  _CorridorTabState createState() => _CorridorTabState();
}

class _CorridorTabState extends State<CorridorTab> {
  bool _isLoading = false;
  List<CurridorModel> _curridors = [];

  @override
  void initState() {
    super.initState();
    _loadCorridors();
  }

  void _loadCorridors() async {
    setState(() {
      _isLoading = true;
    });

    try {
      final api = Provider.of<OlhoVivoService>(context, listen: false);
      await api.authenticate();
      final corridor = await api.getCorridors();

      setState(() {
        _curridors = corridor;
        _isLoading = false;
      });
    } catch (e) {
      print('Erro ao buscar corredores: $e');
      setState(() {
        _isLoading = false;
      });
    }
  }

  void _showBusStop(CurridorModel curridor) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => MapScreen(curridor: curridor),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
        itemCount: _curridors.length,
        itemBuilder: (context, index) {
          final corredores = _curridors[index];
          return Column(
            children: [
              ListTile(
                title: Text('Nome: ${corredores.name}'),
                subtitle: Text('Corredor: ${corredores.id}'),
                onTap: () => _showBusStop(corredores),
              ),
              if (index < _curridors.length - 1) const Divider(),
            ],
          );
        },
      ),
    );
  }
}
