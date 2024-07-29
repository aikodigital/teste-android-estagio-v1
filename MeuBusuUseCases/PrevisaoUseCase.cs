using Bus.Plugins.DataStore.InMemory.DTOs;
using Bus.Plugins.DataStore.InMemory.Interfaces;
using MeuBus.CoreBusiness.Entities;
using MeuBusuUseCases.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MeuBusuUseCases
{
    public class PrevisaoUseCase : IPrevisaoUseCase
    {
        private readonly IOnibusApiService _onibusApiService;

        public PrevisaoUseCase(IOnibusApiService onibusApiService)
        {
            _onibusApiService = onibusApiService;
        }

        public async Task<List<Previsao>> ExecuteAsync(int codigoLinha)
        {
            List<Previsao> previsoes = new();
            var previsao = await _onibusApiService.GetPrevisao(codigoLinha);


            foreach (var item in previsao.ps)
            {
                foreach (var a in item.vs)
                {
                    previsoes.Add(new Previsao
                    {
                        cp = item.cp,
                        np = item.np,
                        pyParada = item.py,
                        pxParada = item.px,
                        p = a.p,
                        t = a.t,
                        a = a.a,
                        ta = a.ta,
                        pyOnibus = a.py,
                        pxOnibus = a.px
                    });
                }
            }
            return previsoes;
        }
    }
}
