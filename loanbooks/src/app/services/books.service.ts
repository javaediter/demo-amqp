import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private apiUrl = 'http://localhost:9090/api/books';

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  getBooks(title: string): Observable<any> {
    const token = this.tokenService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this.apiUrl}/all?title=${title}`, { headers });
  }

  getBookById(id: number): Observable<any> {
    const token = this.tokenService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers });
  }

  putBook(book: object){
    const token = this.tokenService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.put<any>(`${this.apiUrl}/update`, book, { headers });
  }
}
