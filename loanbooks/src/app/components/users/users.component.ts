import { Component } from '@angular/core';
import {UsersService} from '../../services/users.service';
import {FormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';

declare var bootstrap: any;

@Component({
  selector: 'app-users',
  imports: [
    FormsModule,
    NgForOf
  ],
  templateUrl: './users.component.html'
})
export class UsersComponent {
  users: any[] = [];
  userSelected: any = {};
  status: boolean = true;
  modalUpdate: any;
  message!: string;

  constructor(private userService: UsersService) {
  }

  loadData(){
    this.userService.getAll().subscribe({
      next: response => this.users = response,
      error: err => this.message = err,
    })
  }

  ngOnInit() {
    this.loadData();
  }

  ngAfterViewInit() {
    const updateModal = document.getElementById('updateModal');
    this.modalUpdate = new bootstrap.Modal(updateModal);
  }

  selectUser(user: any, status: boolean) {
    this.status = status;
    this.userSelected = user;
  }

  updateStatusUser() {
    this.userService.putUser({
      'username': this.userSelected.username,
      'active': this.status
    }).subscribe({
      next: response => {},
      error: err => this.message = err,
      complete: () => {
        this.message = '';
        this.modalUpdate.hide();
        this.loadData();
      }
    })
  }
}
