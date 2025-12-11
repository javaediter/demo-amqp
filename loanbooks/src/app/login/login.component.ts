import { Component } from '@angular/core';
import { FormsModule, NgForm } from "@angular/forms";
import { TokenService } from '../services/token.service';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  success: boolean = false;

  constructor(private loginService: LoginService, private tokenService: TokenService, private router: Router) { }

  ngOnInit() {
    this.success = true;
  }

  login(form: NgForm) {
    const username = form.value.email;
    const password = form.value.password;
    this.loginService.login(username, password).subscribe({
      next: (result: any) => {
        {
          this.tokenService.clearToken();
          const token = result.token;
          this.tokenService.setToken(token);
        }
      },
      error: (error) => {
        this.success = false;
      },
      complete: () => {
        this.router.navigate(['home']);
      }
    });
  }
}
