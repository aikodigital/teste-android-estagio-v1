import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-pesquisa-filtros',
  templateUrl: './pesquisa-filtros.component.html',
  styleUrls: ['./pesquisa-filtros.component.css']
})
export class PesquisaFiltrosComponent {
  @Output() searchTerm = new EventEmitter<string>();
  query: string = '';

  onSearch(): void {
    this.searchTerm.emit(this.query);
  }
}