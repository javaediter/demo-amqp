import {AfterViewInit, Component} from '@angular/core';
import { LoansService } from '../../services/loans.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

declare var bootstrap: any;

@Component({
  selector: 'app-loans',
  imports: [CommonModule, FormsModule],
  templateUrl: './loans.component.html'
})
export class LoansComponent implements AfterViewInit {
  loans: any[] = [];
  message: string = '';
  date: string = '';
  loanSelected: any = {};
  modalReverse: any;
  modalEnd: any;

  constructor(private loanService: LoansService) {
  }

  ngAfterViewInit() {
    const reverseModal = document.getElementById('reverseModal');
    this.modalReverse = new bootstrap.Modal(reverseModal);

    const endModal = document.getElementById('endModal');
    this.modalEnd = new bootstrap.Modal(endModal);
  }

  selectLoan(loan: any){
    this.loanSelected = loan;
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
      complete: () => this.modalReverse.hide()
    })
  }

  endLoan(id: number) {
    this.loanService.endLoan(id).subscribe({
      next: data => this.searchLoans(),
      error: error => this.message = error.message,
      complete: () => this.modalEnd.hide()
    })
  }
}
