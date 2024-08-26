using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace MeuBus.CoreBusiness.Entities
{
    public class Linha
    {
        public int Identificador { get; set; }

        public bool Circular { get; set; }

        public string LetreiroPrimeiro { get; set; }

        public int LetreiroSegundo { get; set; }

        public int SentidoTerminal { get; set; }

        public string TPrimario { get; set; }

        public string TSecundario { get; set; }

        public string SearchText => throw new NotImplementedException();
    }
}
