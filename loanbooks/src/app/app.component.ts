import { Component } from '@angular/core';
import {RouterOutlet} from '@angular/router';
import { NavigationComponent } from './components/navigation/navigation.component';
import {TokenService} from './services/token.service';
import {LoginComponent} from './login/login.component';

@Component({
  selector: 'app-root',
  imports: [NavigationComponent, RouterOutlet, LoginComponent],
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'Loan Books';

  constructor(private tokenService: TokenService) {}

  isAuthenticated() {
    return this.tokenService.isAuthenticated();
  }
}
