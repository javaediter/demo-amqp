import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import {BooksService} from '../../../services/books.service';
import {FormsModule, NgForm} from '@angular/forms';
import {LoansService} from '../../../services/loans.service';

@Component({
  selector: 'app-new-loan',
  imports: [CommonModule, FormsModule],
  templateUrl: './new-loan.component.html'
})
export class NewLoanComponent {
  books: any[] = [];
  idBook: number = 0;
  loan: object = {};

  constructor(private bookService: BooksService, private loanService: LoansService) {
  }

  searchBooksByTitle(event: Event) {
    event.preventDefault();
    const inputTitle = event.target as HTMLInputElement;
    if (inputTitle.value.length > 3) {
      this.bookService.getBooks(inputTitle.value).subscribe({
        next: (result) => this.books = result,
        error: (error) => {}
      });
    } else {
      this.books = []
    }
  }

  selectBook(event: Event) {
    event.preventDefault();
    const option = event.target as HTMLSelectElement;
    this.idBook = parseInt(option.value);
  }

  submitLoan(form: NgForm) {
    this.loan = {
      "idBook": this.idBook,
      "idPerson": form.value.idPerson,
      "firstName": form.value.firstName,
      "lastName": form.value.lastName
    };

    this.loanService.createLoan(this.loan).subscribe({
      next: (result) => {},
      error: (error) => {},
      complete: () => {
        this.idBook = 0;
        this.loan = {};
        this.books = [];
        form.reset();
      }
    });
  }
}
