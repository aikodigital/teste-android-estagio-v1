import { Routes } from "@angular/router";
import { LinhasOnibusComponent } from "./components/linhas-onibus/linhas-onibus.component";
import { PontosParadaComponent } from "./components/pontos-parada/pontos-parada.component";
import { PosicoesVeiculosComponent } from "./components/posicoes-veiculos/posicoes-veiculos.component";
import { PrevisaoChegadaComponent } from "./components/previsao-chegada/previsao-chegada.component";
import { PesquisaFiltrosComponent } from "./components/pesquisa-filtro/pesquisa-filtros.component";


export const routes: Routes = [
    { path: 'posicoes-veiculos', component: PosicoesVeiculosComponent },
    { path: 'linhas-onibus', component: LinhasOnibusComponent },
    { path: 'pontos-parada', component: PontosParadaComponent },
    { path: 'previsao-chegada', component: PrevisaoChegadaComponent },
    { path: 'pesquisa-filtros', component: PesquisaFiltrosComponent },
    { path: '', redirectTo: '/posicoes-veiculos', pathMatch: 'full' }
];
