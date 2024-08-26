using Bus.Plugins.DataStore.InMemory.DTOs;

namespace Bus.Plugins.DataStore.InMemory.Interfaces
{
    public interface IOnibusApiService
    {
        Task Authenticate();
        Task<List<LinhasDTO>> GetLinhas(string busca);
        Task<OnibusDTO> GetOnibus();
        Task<List<ParadasDTO>> GetParada(int codigoLinha);
        Task<PrevisaoDTO> GetPrevisao(int codigoLinha);
    }
}