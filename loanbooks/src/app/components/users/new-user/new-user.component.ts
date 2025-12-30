import { Component } from '@angular/core';
import {UsersService} from '../../../services/users.service';
import {FormsModule, NgForm} from '@angular/forms';
import {Router} from '@angular/router';
import {NgForOf} from '@angular/common';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-new-user',
  imports: [
    FormsModule,
    NgForOf,
    CommonModule,
  ],
  templateUrl: './new-user.component.html'
})
export class NewUserComponent {
  message! :string;
  roles: any[] = [];
  rolesSelected: any[] = [];

  constructor(private userService: UsersService, private router: Router) {
  }

  takeRole(event: Event) {
    event.preventDefault();
    const checkbox = event.target as HTMLInputElement;

    if (checkbox.checked) {
      this.rolesSelected.push(checkbox.value);
    }else{
      this.rolesSelected = this.rolesSelected.filter(role => role != checkbox.value).slice(0);
    }
  }

  submitNewUser(form: NgForm) {
    const user = {
      "username": form.value.username,
      "password": form.value.password,
      "active": true,
      "roles": this.rolesSelected
    }

    this.userService.postUser(user).subscribe({
      next: response => this.message = response.status,
      error: err => this.message = err,
      complete: () => this.router.navigate(['users/users'])
    })
  }

  loadRoles() {
    this.userService.getRoles().subscribe({
      next: response => this.roles = response,
      error: err => this.message = err,
    })
  }

  ngOnInit() {
    this.loadRoles();
  }
}
