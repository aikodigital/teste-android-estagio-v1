using MeuBusuApp.Models;

namespace MeuBus.CoreBusiness.Entities
{
    public class Paradas 
    {
        public int Idetificador { get; set; }

        public string Nome { get; set; }

        public string Endereco { get; set; }

        public Localizacao Localizacao{ get; set; }

    }
}