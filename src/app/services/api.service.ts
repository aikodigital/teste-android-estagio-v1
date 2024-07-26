import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://api.olhovivo.sptrans.com.br/v2.1';
  private apiToken = '2725581b6b2315fed3bca9f87667ebdab80ee8f299423fe6f687841455037ae3';

  constructor(private http: HttpClient) { }

  authenticate(): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = { token: this.apiToken };
    return this.http.post(`${this.apiUrl}/Login/Autenticar`, body, { headers });
  }

  getVehiclePositions(): Observable<any> {
    return new Observable(observer => {
      this.authenticate().subscribe(authenticated => {
        if (authenticated) {
          this.http.get(`${this.apiUrl}/Posicao`).subscribe(data => {
            observer.next(data);
            observer.complete();
          });
        } else {
          observer.error('Authentication failed');
        }
      });
    });
  }

  getBusLines(): Observable<any> {
    return new Observable(observer => {
      this.authenticate().subscribe(authenticated => {
        if (authenticated) {
          this.http.get(`${this.apiUrl}/Linha`).subscribe(data => {
            observer.next(data);
            observer.complete();
          });
        } else {
          observer.error('Authentication failed');
        }
      });
    });
  }

  getStops(): Observable<any> {
    return new Observable(observer => {
      this.authenticate().subscribe(authenticated => {
        if (authenticated) {
          this.http.get(`${this.apiUrl}/Parada`).subscribe(data => {
            observer.next(data);
            observer.complete();
          });
        } else {
          observer.error('Authentication failed');
        }
      });
    });
  }

  getArrivalPredictions(stopId: number): Observable<any> {
    return new Observable(observer => {
      this.authenticate().subscribe(authenticated => {
        if (authenticated) {
          this.http.get(`${this.apiUrl}/Previsao?codigoParada=${stopId}`).subscribe(data => {
            observer.next(data);
            observer.complete();
          });
        } else {
          observer.error('Authentication failed');
        }
      });
    });
  }
}
