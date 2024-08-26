using Bus.Plugins.DataStore.InMemory.Interfaces;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using MeuBus.CoreBusiness.Entities;
using MeuBusuUseCases;
using MeuBusuUseCases.Interfaces;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MeuBusuApp.ViewModel
{
    [QueryProperty(nameof(Itinerario), ("Id"))]
    public partial class ParadasViewModel : ObservableObject
    {
        private readonly IParadaUseCase _paradaUseCase;

        public ObservableCollection<Paradas> Paradas { get; set; }

        public ParadasViewModel(IParadaUseCase paradaUseCase)
        {

            Paradas = new ObservableCollection<Paradas>();
            _paradaUseCase = paradaUseCase;
        }

        public int codigoLinha { get; set; }

        public string Itinerario { set 
            {
                int.TryParse(value, out var codigoLinha);
                GetItinerario(codigoLinha);
                this.codigoLinha = codigoLinha;
            } 
        }

        public async void GetItinerario(int codigoLinha)
        {
            var previsoes = await _paradaUseCase.ExecuteAsync(codigoLinha);

            foreach (var item in previsoes)
            {
                Paradas.Add(item);
            }

            if(previsoes.Count == 0) 
            {
                await Application.Current.MainPage.DisplayAlert("Alert", "Itinerario não encontrado", "OK");
                await Shell.Current.GoToAsync("..");
            }

        }

        [RelayCommand]
        public async Task GoToPrevisao()
        {
            await Shell.Current.GoToAsync($"{nameof(PrevisaoPage)}?Id={codigoLinha}");
        }

        [RelayCommand]
        public async Task GoToMap()
        {       
            var navigationParameter = new ShellNavigationQueryParameters
                {
                    { "Itinerario", Paradas.ToList()}
                };
            await Shell.Current.GoToAsync($"{nameof(MapView)}", navigationParameter);
        }

    }
}
