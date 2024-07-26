import { HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { LinhasOnibusComponent } from "./components/linhas-onibus/linhas-onibus.component";
import { PesquisaFiltrosComponent } from "./components/pesquisa-filtro/pesquisa-filtros.component";
import { PontosParadaComponent } from "./components/pontos-parada/pontos-parada.component";
import { PosicoesVeiculosComponent } from "./components/posicoes-veiculos/posicoes-veiculos.component";
import { PrevisaoChegadaComponent } from "./components/previsao-chegada/previsao-chegada.component";
import { ApiService } from "./services/api.service";
import { AppComponent } from "../app.component";
import { AppRoutingModule } from "./app-routing.module";


@NgModule({
  declarations: [
    AppComponent,
    PosicoesVeiculosComponent,
    LinhasOnibusComponent,
    PontosParadaComponent,
    PrevisaoChegadaComponent,
    PesquisaFiltrosComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [ApiService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
