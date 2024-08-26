using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Bus.Plugins.DataStore.InMemory.DTOs
{
    public class LinhasDTO
    {
        [JsonPropertyName("cl")]
        public int Identificador { get; set; }

        [JsonPropertyName("lc")]
        public bool Circular { get; set; }

        [JsonPropertyName("lt")]
        public string LetreiroPrimeiro { get; set; }

        [JsonPropertyName("sl")]
        public int LetreiroSegundo { get; set; }

        [JsonPropertyName("tl")]
        public int Sentido { get; set; }

        [JsonPropertyName("tp")]
        public string Primario { get; set; }

        [JsonPropertyName("ts")]
        public string Secundario { get; set; }
    }
}
