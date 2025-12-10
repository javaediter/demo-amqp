import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BooksService } from '../../services/books.service';

@Component({
  selector: 'app-books',
  imports: [CommonModule, FormsModule],
  templateUrl: './books.component.html'
})
export class BooksComponent {
  title: string = '';
  message = '';
  books: any[] = [];

  constructor(private booksService: BooksService) { }

  searchBooks() {
    this.booksService.getBooks(this.title).subscribe({
      next: data => {
        this.books = data;
        this.message = '';
      },
      error: err => {
        this.message = err.message;
      }
    });
  }

  updateStateBook(id: number) {
    this.booksService.getBookById(id).subscribe({
      next: book => {
        const requestUpdateBook = {
          "id": book.id
        }
        this.booksService.putBook(requestUpdateBook).subscribe(
          {
            next: (resp) => this.searchBooks(),
            error: (error) => {
              this.message = error.message;
            }
          }
        );
      }
    })
  }
}
