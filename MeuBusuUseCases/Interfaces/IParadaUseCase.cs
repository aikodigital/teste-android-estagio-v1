using MeuBus.CoreBusiness.Entities;

namespace MeuBusuUseCases.Interfaces
{
    public interface IParadaUseCase
    {
        Task<List<Paradas>> ExecuteAsync(int codigoLinha);
    }
}