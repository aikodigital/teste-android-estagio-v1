
using Bus.Plugins.DataStore.InMemory.DTOs;

namespace MeuBusuUseCases.Interfaces
{
    public interface ISearchBarUseCase
    {
        Task<List<LinhasDTO>> ExecuteAsync(string filterText);
    }
}