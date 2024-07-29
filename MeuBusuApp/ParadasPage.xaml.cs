using MeuBusuApp.ViewModel;

namespace MeuBusuApp;

public partial class ParadasPage : ContentPage
{
    private readonly ParadasViewModel _paradasViewModel;

    public ParadasPage()
	{
	}

    public ParadasPage(ParadasViewModel paradasViewModel)
    {
        InitializeComponent();
        _paradasViewModel = paradasViewModel;
        this.BindingContext = _paradasViewModel;
    }

}