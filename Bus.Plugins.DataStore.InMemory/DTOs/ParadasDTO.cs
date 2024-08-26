using System.Text.Json.Serialization;

namespace Bus.Plugins.DataStore.InMemory.DTOs
{

    public class ParadasDTO
    {

        [JsonPropertyName("cp")]
        public int Idetificador { get; set; }

        [JsonPropertyName("np")]
        public string Nome { get; set; }
        
        [JsonPropertyName("ed")]
        public string Endereco { get; set; }

        [JsonPropertyName("py")]
        public double Latitude { get; set; }

        [JsonPropertyName("px")]
        public double Longitude { get; set; }


    }
}