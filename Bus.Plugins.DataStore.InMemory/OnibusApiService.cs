using Bus.Plugins.DataStore.InMemory.DTOs;
using Bus.Plugins.DataStore.InMemory.Interfaces;
using System.Text.Json;

namespace Bus.Plugins.DataStore.InMemory
{
    public class OnibusApiService : IOnibusApiService
    {
        const string token = "c0cd0bc813965fe10fe1b61b4bc9e61832d48a8131988f1926f406dcd3b86f44";

        private readonly HttpClient _client;

        public OnibusApiService(HttpClient client)
        {
            _client = client;
        }

        public async Task Authenticate()
        {
            var message = await _client.PostAsync($"https://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token={token}"
                , new StringContent(""));

            var cookies = message.Headers.GetValues("Set-Cookie");

            _client.DefaultRequestHeaders.Add("Cookies", cookies.FirstOrDefault().Split(";")[0]);

        }

        public async Task<OnibusDTO> GetOnibus()
        {
            await Authenticate();

            var response = await _client.GetAsync("https://api.olhovivo.sptrans.com.br/v2.1/Posicao");

            var stream = await response.Content.ReadAsStreamAsync();

            var dto = await JsonSerializer.DeserializeAsync<OnibusDTO>(stream);

            return dto;
        }

        public async Task<List<ParadasDTO>> GetParada(int codigoLinha)
        {
            var response = await _client.
                GetAsync($"https://api.olhovivo.sptrans.com.br/v2.1/Parada/BuscarParadasPorLinha?codigoLinha={codigoLinha}");
            var stream = await response.Content.ReadAsStreamAsync();

            var dto = await JsonSerializer.DeserializeAsync<List<ParadasDTO>>(stream);

            return dto;
        }

        public async Task<List<LinhasDTO>> GetLinhas(string busca)
        {
            var response = await _client.GetAsync($"https://api.olhovivo.sptrans.com.br/v2.1/Linha/Buscar?termosBusca={busca}");

            var stream = await response.Content.ReadAsStreamAsync();

            var dto = await JsonSerializer.DeserializeAsync<List<LinhasDTO>>(stream);

            return dto;

        }

        public async Task<PrevisaoDTO> GetPrevisao(int codigoLinha)
        {

            try
            {
                var response = await _client.GetAsync($"https://api.olhovivo.sptrans.com.br/v2.1/Previsao/Linha?codigoLinha={codigoLinha}");

                var stream = await response.Content.ReadAsStreamAsync();

                var dto = await JsonSerializer.DeserializeAsync<PrevisaoDTO>(stream);

                return dto;
            }
            catch (Exception ex) 
            {
                return new PrevisaoDTO();
            }


        }
    }
}
