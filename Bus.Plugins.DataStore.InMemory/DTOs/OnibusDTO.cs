using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Bus.Plugins.DataStore.InMemory.DTOs
{
    public class OnibusDTO
    {
        [JsonPropertyName("hr")]
        public string HorarioReferencia { get; set; } = string.Empty;

        [JsonPropertyName("l")]
        public List<LandsBetween> Lands{ get; set; } = new List<LandsBetween>();

        public class LandsBetween
        {
            [JsonPropertyName("lt0")]
            public string LetreiroDestino { get; set; }

            [JsonPropertyName("sl")]
            public int SentidoDeOperacao { get; set; }

            [JsonPropertyName("cl")]
            public int CodigoLinha { get; set; }

            [JsonPropertyName("vs")]
            public List<Onibus> Veiculos{ get; set; } = new List<Onibus>();
        }

        public class Onibus
        {
            [JsonPropertyName("p")]
            public int PrefixoOnibus { get; set; }

            [JsonPropertyName("a")]
            public bool PCD { get; set; }

            [JsonPropertyName("py")]
            public double Latitude { get; set; }

            [JsonPropertyName("px")]
            public double Longitude { get; set; }

            [JsonPropertyName("ta")]
            public string DataLocalizacao { get; set; }
        }
    }
}
