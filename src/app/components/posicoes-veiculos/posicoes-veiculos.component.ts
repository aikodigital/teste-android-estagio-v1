import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-posicoes-veiculos',
  templateUrl: './posicoes-veiculos.component.html',
  styleUrls: ['./posicoes-veiculos.component.css']
})
export class PosicoesVeiculosComponent implements OnInit {
  vehicles: any[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getVehiclePositions().subscribe((data: { vehicles: any[]; }) => {
      this.vehicles = data.vehicles;
    });
  }
}