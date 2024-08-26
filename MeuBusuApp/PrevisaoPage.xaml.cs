using MeuBusuApp.ViewModel;

namespace MeuBusuApp;

public partial class PrevisaoPage : ContentPage
{
    private readonly PrevisaoViewModel _previsaoViewModel;

    public PrevisaoPage()
	{
		
	}
    public PrevisaoPage(PrevisaoViewModel previsaoViewModel)
    {
        InitializeComponent(); 
        _previsaoViewModel = previsaoViewModel;
        this.BindingContext = _previsaoViewModel;
    }


}