using Bus.Plugins.DataStore.InMemory.Interfaces;
using MeuBus.CoreBusiness.Entities;
using MeuBusuApp.Models;
using MeuBusuUseCases.Interfaces;

namespace MeuBusuUseCases
{
    public class OnibusUseCase : IOnibusUseCase
    {
        private readonly IOnibusApiService _onibusApiService;

        public OnibusUseCase(IOnibusApiService onibusApiService)
        {
            _onibusApiService = onibusApiService;
        }

        public async Task<List<Onibus>> ExecuteAsync()
        {
            var dto = await _onibusApiService.GetOnibus();

            List<Onibus> list = new List<Onibus>();

            foreach (var item in dto.Lands)
            {
                foreach (var veiculo in item.Veiculos)
                {
                    list.Add(new Onibus
                    {
                        PrefixoOnibus = veiculo.PrefixoOnibus.ToString(),
                        PCD = veiculo.PCD,
                        CodigoLinha = item.CodigoLinha,
                        DataLocalizacao = veiculo.DataLocalizacao,
                        Localizacao = new Localizacao(veiculo.Latitude, veiculo.Longitude),
                        LetreiroDestino = item.LetreiroDestino
                    });
                }
            }

            return list;
        }
    }
}
