using Bus.Plugins.DataStore.InMemory.Interfaces;
using MeuBus.CoreBusiness.Entities;
using MeuBusuApp.Models;
using MeuBusuUseCases.Interfaces;

namespace MeuBusuUseCases
{
    public class ParadaUseCase : IParadaUseCase
    {
        private readonly IOnibusApiService _onibusApiService;

        public ParadaUseCase(IOnibusApiService onibusApiService)
        {
            _onibusApiService = onibusApiService;
        }

        public async Task<List<Paradas>> ExecuteAsync(int codigoLinha)
        {
            List<Paradas> paradas = new List<Paradas>();

            var parada = await _onibusApiService.GetParada(codigoLinha);

            foreach (var item in parada)
            {
                paradas.Add(new Paradas
                {
                    Idetificador = item.Idetificador,
                    Nome = item.Nome,
                    Endereco = item.Endereco,
                    Localizacao = new Localizacao(item.Latitude, item.Longitude)
                }); ;
            }

            return paradas;

        }
    }
}
