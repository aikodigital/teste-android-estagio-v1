using MeuBus.CoreBusiness.Entities;
using Microsoft.Maui.Controls.Maps;
using Microsoft.Maui.Maps;
using Map = Microsoft.Maui.Controls.Maps.Map;

namespace MeuBusuApp;

[QueryProperty(nameof(Itinerario), "Itinerario")]
[QueryProperty(nameof(Previsao), "Previsao")]
public partial class MapView : ContentPage
{
    public Map Map { get; set; }

    List<Paradas> itinerario;
    public List<Paradas> Itinerario
    {
        get => itinerario;
        set
        {
            itinerario = value;
            PinsParada();
        }
    }
    List<Previsao> previsao;
    public List<Previsao> Previsao
    {
        get => previsao;
        set
        {
            previsao = value;
            PinsPrevisao();
        }
    }

    public void PinsPrevisao()
    {
        foreach (var item in Previsao)
        {
            Pin pin = new Pin
            {
                Label = item.np,
                Address = string.Empty,
                Type = PinType.Place,
                Location = new Location(item.pyOnibus, item.pxOnibus)
            };
            Map.Pins.Add(pin);
        }
    }

    public MapView()
    {
        InitializeComponent();
        Location location = new Location(-23.5745336, -46.6790941);
        MapSpan mapSpan = new MapSpan(location, 0.01, 0.01);
        Map = new Map(mapSpan);
        Content = Map;
    }

    public void PinsParada()
    {
        foreach (var item in Itinerario)
        {
            Pin pin = new Pin
            {
                Label = item.Nome,
                Address = item.Endereco,
                Type = PinType.Place,
                Location = new Location(item.Localizacao.Latitude, item.Localizacao.Longitude)
            };
            Map.Pins.Add(pin);
        }
    }

}