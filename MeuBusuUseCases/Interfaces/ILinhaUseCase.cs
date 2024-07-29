using MeuBus.CoreBusiness.Entities;

namespace MeuBusuUseCases.Interfaces
{
    public interface ILinhaUseCase
    {
        Task<List<Linha>> ExecuteAsync(string busca);
    }
}