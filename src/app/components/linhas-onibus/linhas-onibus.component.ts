import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-linhas-onibus',
  templateUrl: './linhas-onibus.component.html',
  styleUrls: ['./linhas-onibus.component.css']
})
export class LinhasOnibusComponent implements OnInit {
  busLines: any[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getBusLines().subscribe((data: { lines: any[]; }) => {
      this.busLines = data.lines;
    });
  }
}