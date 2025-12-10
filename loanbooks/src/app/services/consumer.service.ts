import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenService} from './token.service';

@Injectable({
  providedIn: 'root'
})
export class ConsumerService {
  apiUrl = 'http://localhost:9091/api-consumer/loans';

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  getLogs(numPage: number, sizePage: number): Observable<any>{
    const token = this.tokenService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.apiUrl}/all?num=${numPage}&size=${sizePage}`, { headers })
  }
}
