using Bus.Plugins.DataStore.InMemory.Interfaces;
using MeuBus.CoreBusiness.Entities;
using MeuBusuUseCases.Interfaces;

namespace MeuBusuUseCases;

public class LinhaUseCase : ILinhaUseCase
{
    private readonly IOnibusApiService _onibusApiService;

    public LinhaUseCase(IOnibusApiService onibusApiService)
    {
        _onibusApiService = onibusApiService;
    }

    public async Task<List<Linha>> ExecuteAsync(string busca)
    {
        List<Linha> linhas = new();

        var linhasDTO = await _onibusApiService.GetLinhas(busca);

        foreach (var linha in linhasDTO)
        {
            linhas.Add(new Linha
            {
                Identificador = linha.Identificador,
                Circular = linha.Circular,
                TPrimario = linha.Primario,
                TSecundario = linha.Secundario,
                SentidoTerminal = linha.Sentido,
                LetreiroPrimeiro = linha.LetreiroPrimeiro,
                LetreiroSegundo = linha.LetreiroSegundo,
            });
        }

        return linhas;
    }

}
