using MeuBusuApp.ViewModel;

namespace MeuBusuApp
{
    public partial class MainPage : ContentPage
    {
        private readonly MainPageViewModel _mainPageViewModel;

        public MainPage(MainPageViewModel mainPageViewModel)
        {
            InitializeComponent();
            _mainPageViewModel = mainPageViewModel;
            this.BindingContext = _mainPageViewModel;
        }

        public MainPage()
        {      
        }

        protected override void OnAppearing()
        {   
            base.OnAppearing();
            _mainPageViewModel.GetOnibus();
        }
    }

}
