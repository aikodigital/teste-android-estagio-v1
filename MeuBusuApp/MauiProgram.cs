using MeuBusuApp.ViewModel;
using Microsoft.Extensions.Logging;
using CommunityToolkit.Maui;
using Bus.Plugins.DataStore.InMemory.Interfaces;
using Bus.Plugins.DataStore.InMemory;
using MeuBusuUseCases;
using MeuBusuUseCases.Interfaces;

namespace MeuBusuApp
{
    public static class MauiProgram
    {
        public static MauiApp CreateMauiApp()
        {
            var builder = MauiApp.CreateBuilder();
            builder
                .UseMauiApp<App>()
                .ConfigureFonts(fonts =>
                {
                    fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                    fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
                })
                .UseMauiMaps()
                .UseMauiCommunityToolkit()
                .Services.AddHttpClient<IOnibusApiService>();


#if DEBUG
            builder.Logging.AddDebug();
#endif
        


            builder.Services.AddSingleton<IOnibusApiService, OnibusApiService>();
            builder.Services.AddSingleton<ISearchBarUseCase, SearchBarUseCase>();
            builder.Services.AddSingleton<IPrevisaoUseCase, PrevisaoUseCase>();
            builder.Services.AddSingleton<IOnibusUseCase, OnibusUseCase>();
            builder.Services.AddSingleton<IParadaUseCase, ParadaUseCase>();
            builder.Services.AddSingleton<ILinhaUseCase, LinhaUseCase>();
            builder.Services.AddSingleton<MainPage>();
            builder.Services.AddSingleton<MainPageViewModel>();
            builder.Services.AddSingleton<ParadasPage>();
            builder.Services.AddSingleton<ParadasViewModel>();
            builder.Services.AddSingleton<PrevisaoPage>();
            builder.Services.AddSingleton<PrevisaoViewModel>();

            return builder.Build();
        }
    }
}
