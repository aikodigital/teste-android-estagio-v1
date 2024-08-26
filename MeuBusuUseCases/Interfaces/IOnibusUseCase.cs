using MeuBus.CoreBusiness.Entities;

namespace MeuBusuUseCases.Interfaces
{
    public interface IOnibusUseCase
    {
        Task<List<Onibus>> ExecuteAsync();
    }
}