import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/bus_stop_model.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:olho_vivo_sp/widgets/bus_stop_list_item_info.dart';
import 'package:provider/provider.dart';

class BusStopListItem extends StatefulWidget {
  final BusStopModel busStop;

  const BusStopListItem({super.key, required this.busStop});

  @override
  State<BusStopListItem> createState() => _BusStopListItemState();
}

class _BusStopListItemState extends State<BusStopListItem> {
  late ApiService apiService;
  bool _isVisible = false;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final apiService = Provider.of<ApiService>(context);

    return Column(
      children: [
        Card(
          child: ListTile(
            leading: const Icon(Icons.bus_alert),
            trailing: IconButton(
              icon: const Icon(Icons.remove_red_eye),
              onPressed: () {
                setState(() {
                  _isVisible = !_isVisible;
                });
              },
            ),
            title: Text('${widget.busStop.code} ${widget.busStop.name}'),
            subtitle: Text(widget.busStop.address),
          ),
        ),
        Visibility(
          visible: _isVisible,
          child: FutureBuilder(
            future: apiService.getArrivalForecast(
              busStopCode: widget.busStop.code.toString(),
            ),
            builder: (ctx, snp) => BusStopListItemInfo(snp: snp),
          ),
        ),
      ],
    );
  }
}
