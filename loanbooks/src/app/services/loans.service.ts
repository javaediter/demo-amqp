import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenService} from './token.service';
import {LoansComponent} from '../components/loans/loans.component';

@Injectable({
  providedIn: 'root'
})
export class LoansService {

  apiUrl = 'http://localhost:9090/api/loans';

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  getAllByBeforeDate(date: string): Observable<any>{
    const token = this.tokenService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this.apiUrl}/all-before?date=${date}`, { headers });
  }

  reverse(id: number): Observable<any>{
    const token = this.tokenService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.apiUrl}/reverse`, {'idLoan': id}, { headers });
  }

  endLoan(id: number): Observable<any>{
    const token = this.tokenService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.apiUrl}/end`, {'idLoan': id}, { headers });
  }

  createLoan(loan: object): Observable<any>{
    const token = this.tokenService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.apiUrl}/create`, loan, { headers });
  }
}
