using MeuBusuApp.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace MeuBus.CoreBusiness.Entities
{
    public class Onibus
    {
        public string PrefixoOnibus { get; set; }

        public int CodigoLinha { get; set; }

        public Localizacao Localizacao{ get; set; }
        
        public bool PCD { get; set; }

        public string DataLocalizacao { get; set; }

        public string LetreiroDestino { get; set; }

    }
}
