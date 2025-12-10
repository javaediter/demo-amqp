import { Component } from '@angular/core';
import { BooksComponent } from './components/books/books.component';
import { LoginComponent } from "./login/login.component";
import { NewLoanComponent } from './components/loans/new-loan/new-loan.component';
import { LoansComponent } from './components/loans/loans.component';
import { LogsComponent } from './components/logs/logs.component';

@Component({
  selector: 'app-root',
  imports: [BooksComponent, LoginComponent, LoansComponent, NewLoanComponent, LogsComponent],
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'Loan Books';
}
