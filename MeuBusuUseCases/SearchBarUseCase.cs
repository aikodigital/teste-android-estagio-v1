using Bus.Plugins.DataStore.InMemory.DTOs;
using Bus.Plugins.DataStore.InMemory.Interfaces;
using MeuBusuUseCases.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MeuBusuUseCases
{
    public class SearchBarUseCase : ISearchBarUseCase
    {
        private readonly IOnibusApiService _onibusApiService;

        public SearchBarUseCase(IOnibusApiService onibusApiService)
        {
            _onibusApiService = onibusApiService;
        }

        public async Task<List<LinhasDTO>> ExecuteAsync(string busca)
        {
            var onibus = await _onibusApiService.GetLinhas(busca);

            return onibus;

        }


    }
}
