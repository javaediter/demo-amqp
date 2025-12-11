import { Component } from '@angular/core';
import {Router, RouterModule} from "@angular/router";
import {TokenService} from '../../services/token.service';

@Component({
  selector: 'app-navigation',
    imports: [ RouterModule ],
  templateUrl: './navigation.component.html'
})
export class NavigationComponent {

  constructor(private router: Router, private tokenService: TokenService) { }

  logout() {
    this.tokenService.clearToken();
    this.router.navigate(['/']);
  }
}
