using MeuBus.CoreBusiness.Entities;

namespace MeuBusuUseCases.Interfaces
{
    public interface IPrevisaoUseCase
    {
        Task<List<Previsao>> ExecuteAsync(int codigoLinha);
    }
}