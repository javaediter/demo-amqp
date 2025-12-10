import { Component } from '@angular/core';
import {ConsumerService} from '../../services/consumer.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-logs',
  imports: [CommonModule],
  templateUrl: './logs.component.html'
})
export class LogsComponent {
  dis = 'disabled';
  numPage = 0;
  sizePage = 10;
  logs: any[] = [];

  constructor(private consumerService: ConsumerService) {
  }

  nextPage(){
    this.numPage++;
    this.loadData();
    if(this.logs.length == 0 && this.numPage > 0){
      this.numPage--;
    }
  }

  backPage(){
    this.numPage--;
    if(this.numPage == 0){
      this.dis = 'disabled';
    }
    this.loadData();
  }

  loadData(){
    this.consumerService.getLogs(this.numPage, this.sizePage).subscribe({
      next: (result: any) => this.logs = result,
      error: (err) => {},
      complete: () => {
        if(this.numPage > 0){
          this.dis = '';
        }
      }
    });
  }

  ngOnInit() {
    this.loadData();
  }
}
