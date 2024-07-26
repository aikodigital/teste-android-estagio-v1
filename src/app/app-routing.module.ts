import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PosicoesVeiculosComponent } from './components/posicoes-veiculos/posicoes-veiculos.component';
import { LinhasOnibusComponent } from './components/linhas-onibus/linhas-onibus.component';
import { PontosParadaComponent } from './components/pontos-parada/pontos-parada.component';
import { PrevisaoChegadaComponent } from './components/previsao-chegada/previsao-chegada.component';
import { PesquisaFiltrosComponent } from './components/pesquisa-filtro/pesquisa-filtros.component';

const routes: Routes = [
  { path: 'posicoes-veiculos', component: PosicoesVeiculosComponent },
  { path: 'linhas-onibus', component: LinhasOnibusComponent },
  { path: 'pontos-parada', component: PontosParadaComponent },
  { path: 'previsao-chegada', component: PrevisaoChegadaComponent },
  { path: 'pesquisa-filtros', component: PesquisaFiltrosComponent },
  { path: '', redirectTo: '/posicoes-veiculos', pathMatch: 'full' },
  { path: '**', redirectTo: '/posicoes-veiculos' } // Rota curinga para redirecionar para uma rota padrão
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
