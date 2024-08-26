using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MeuBus.CoreBusiness.Entities
{
    public class Previsao
    {
        public string hr { get; set; }
        public int cp { get; set; }
        public string np { get; set; }
        public double pyParada { get; set; }
        public double pxParada { get; set; }
        public string p { get; set; }
        public string t { get; set; }
        public bool a { get; set; }
        public DateTime ta { get; set; }
        public double pyOnibus { get; set; }
        public double pxOnibus { get; set; }
    }

}

