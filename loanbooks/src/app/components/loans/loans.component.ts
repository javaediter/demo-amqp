import { Component } from '@angular/core';
import { LoansService } from '../../services/loans.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-loans',
  imports: [CommonModule, FormsModule],
  templateUrl: './loans.component.html'
})
export class LoansComponent {
  loans: any[] = [];
  message: string = '';
  date: string = '';

  constructor(private loanService: LoansService) {
  }

  searchLoans() {
    this.loanService.getAllByBeforeDate(`${this.date} 23:59:59`).subscribe({
      next: data => {
        this.loans = data;
        this.message = '';
      },
      error: error => this.message = error.message,
    });
  }

  reverseLoans(id: number) {
    this.loanService.reverse(id).subscribe({
      next: data => this.searchLoans(),
      error: error => this.message = error.message,
    })
  }

  endLoan(id: number) {
    this.loanService.endLoan(id).subscribe({
      next: data => this.searchLoans(),
      error: error => this.message = error.message,
    })
  }
}
