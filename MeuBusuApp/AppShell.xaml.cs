namespace MeuBusuApp
{
    public partial class AppShell : Shell
    {
        public AppShell()
        {
            InitializeComponent();
            Routing.RegisterRoute(nameof(ParadasPage), typeof(ParadasPage));
            Routing.RegisterRoute(nameof(PrevisaoPage), typeof(PrevisaoPage));
            Routing.RegisterRoute(nameof(MapView), typeof(MapView));    
        }
    }
}
