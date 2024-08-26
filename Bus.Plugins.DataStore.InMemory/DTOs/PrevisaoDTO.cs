using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace Bus.Plugins.DataStore.InMemory.DTOs
{
    public class PrevisaoDTO
    { 
        [JsonPropertyName("hr")]
        public string hr { get; set; }

        [JsonPropertyName("ps")]
        public List<OnibusPrevisao> ps { get; set; }

        public class OnibusPrevisao { 

            [JsonPropertyName("cp")]
            public int cp { get; set; }

            [JsonPropertyName("np")]
            public string np { get; set; }

            [JsonPropertyName("py")]
            public double py { get; set; }

            [JsonPropertyName("px")]
            public double px { get; set; }

            [JsonPropertyName("vs")]
            public List<Parada> vs { get; set; }
    } 

    public class Parada
    {
        [JsonPropertyName("p")]
        public string p { get; set; }

        [JsonPropertyName("t")]
        public string t { get; set; }

        [JsonPropertyName("a")]
        public bool a { get; set; }

        [JsonPropertyName("ta")]
        public DateTime ta { get; set; }

        [JsonPropertyName("py")]
        public double py { get; set; }

        [JsonPropertyName("px")]
        public double px { get; set; }
    }
}
}
