using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using MeuBus.CoreBusiness.Entities;
using MeuBusuUseCases.Interfaces;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MeuBusuApp.ViewModel
{
    [QueryProperty(nameof(PrevisaoChegada), ("Id"))]
    public partial class PrevisaoViewModel : ObservableObject
    {
        private readonly IPrevisaoUseCase _previsaoUseCase;

        public ObservableCollection<Previsao> Previsoes { get; set; }

        public string PrevisaoChegada { set 
            {
                int.TryParse(value, out var codigoLinha);
                GetPrevisao(codigoLinha);
            } }

        public PrevisaoViewModel(IPrevisaoUseCase previsaoUseCase)
        {
            _previsaoUseCase = previsaoUseCase;
            Previsoes = new();
        }

        public async void GetPrevisao(int codigoLinha)
        {
            var a = await _previsaoUseCase.ExecuteAsync(codigoLinha);

            foreach (var item in a) 
            {
                Previsoes.Add(item);
            }
        }

        [RelayCommand]
        public async void GoToPrevisaoMap()
        {
            var navigationParameter = new ShellNavigationQueryParameters
                {
                    { "Previsao", Previsoes.ToList()}
                };
            await Shell.Current.GoToAsync($"{nameof(MapView)}", navigationParameter);
        }
    }

}
