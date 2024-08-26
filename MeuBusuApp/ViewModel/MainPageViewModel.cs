using Bus.Plugins.DataStore.InMemory.DTOs;
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using MeuBus.CoreBusiness.Entities;
using MeuBusuUseCases.Interfaces;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MeuBusuApp.ViewModel
{
    public partial class MainPageViewModel : ObservableObject
    {
        private readonly IOnibusUseCase _onibusUseCase;
        private readonly ILinhaUseCase _linhaUseCase;

        public string FilterText { get; set; }
        int step = 10;
        int page = 1;

        readonly IList<Onibus> source;

        public ObservableCollection<Onibus> Onibus { get; set; }
        public ObservableCollection<Linha> Linhas { get; set; }
        public ObservableCollection<Onibus> SearchedOnibus { get; set; }

        public MainPageViewModel(IOnibusUseCase onibusUseCase, ILinhaUseCase linhaUseCase)
        {
            _onibusUseCase = onibusUseCase;
            _linhaUseCase = linhaUseCase;
            Onibus = new ObservableCollection<Onibus>();
            Linhas = new ObservableCollection<Linha>();
            source = new List<Onibus>();
        }

        public async void GetOnibus() 
        {
            Onibus.Clear();
            var onibus = await _onibusUseCase.ExecuteAsync();

            foreach (var item in onibus)
            {
                source.Add(item);
            }
            LoadMore();
        }

        [RelayCommand]
        public async void Clicked()
        {
            Onibus.Clear();

            var a = source.Where(x => x.LetreiroDestino.StartsWith(FilterText, StringComparison.OrdinalIgnoreCase));

            if (a.Count() < 1)
            {
                a = source.Where(x => x.PrefixoOnibus.StartsWith(FilterText, StringComparison.OrdinalIgnoreCase));
            }
            if (a.Count() < 1)
            {
                a = source.Where(x => x.CodigoLinha.ToString().StartsWith(FilterText, StringComparison.OrdinalIgnoreCase));
            }

                foreach (var item in a)
            {
                Onibus.Add(item);
            }
            

        }

        [RelayCommand]
        public void LoadMore()
        {
            if (source.Count > step * page)
            {
                var newpage = source.Skip(step * page).Take(step); 
                foreach (var item in newpage)
                {
                    Onibus.Add(item);
                }
                page++;
            }
        }

        [RelayCommand]
        public async Task GoToParadas(int codigoLinha)
        {
            await Shell.Current.GoToAsync($"{nameof(ParadasPage)}?Id={codigoLinha}");
        } 
    }
}
