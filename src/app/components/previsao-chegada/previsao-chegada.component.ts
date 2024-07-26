import { Component, OnInit, Input } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-previsao-chegada',
  templateUrl: './previsao-chegada.component.html',
  styleUrls: ['./previsao-chegada.component.css']
})
export class PrevisaoChegadaComponent implements OnInit {
  @Input()
  stopId!: number;
  predictions: any[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getArrivalPredictions(this.stopId).subscribe((data: { predictions: any[]; }) => {
      this.predictions = data.predictions;
    });
  }
}
