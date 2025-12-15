import { CommonModule } from '@angular/common';
import {AfterViewInit, Component} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BooksService } from '../../services/books.service';

declare var bootstrap: any;

@Component({
  selector: 'app-books',
  imports: [CommonModule, FormsModule],
  templateUrl: './books.component.html'
})
export class BooksComponent implements AfterViewInit {
  title: string = '';
  message = '';
  books: any[] = [];
  bookSelected: any = {};
  modalLock: any;
  modalUnlock: any;

  constructor(private booksService: BooksService) { }

  ngAfterViewInit(): void {
    const mdlLock = document.getElementById('lockModal');
    this.modalLock = new bootstrap.Modal(mdlLock);

    const mdlUnlock = document.getElementById('unlockModal');
    this.modalUnlock = new bootstrap.Modal(mdlUnlock);
  }

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

  selectBook(book: any) {
    this.bookSelected = book;
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
      },
      error: err => {},
      complete: () => {
        this.modalLock.hide();
        this.modalUnlock.hide();
      }
    })
  }
}
