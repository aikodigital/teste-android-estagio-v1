import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-pontos-parada',
  templateUrl: './pontos-parada.component.html',
  styleUrls: ['./pontos-parada.component.css']
})
export class PontosParadaComponent implements OnInit {
  stops: any[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getStops().subscribe((data: { stops: any[]; }) => {
      this.stops = data.stops;
    });
  }
}
