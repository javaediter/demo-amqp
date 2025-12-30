import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './components/home/home.component';
import { BooksComponent } from './components/books/books.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { LogsComponent } from './components/logs/logs.component';
import { LoansComponent } from './components/loans/loans.component';
import { NewLoanComponent } from './components/loans/new-loan/new-loan.component';
import { AuthGuardianService } from './services/auth-guardian.service';
import { UsersComponent } from './components/users/users.component';
import {NewUserComponent} from './components/users/new-user/new-user.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: HomeComponent, canActivate:[AuthGuardianService] },
  { path: 'home', component: HomeComponent, canActivate:[AuthGuardianService] },
  { path: 'books', component: BooksComponent, canActivate:[AuthGuardianService] },
  { path: 'logs', component: LogsComponent, canActivate:[AuthGuardianService] },
  { path: 'users', children: [
      {path: 'users', component: UsersComponent, canActivate:[AuthGuardianService]},
      {path: 'new', component: NewUserComponent, canActivate:[AuthGuardianService]}
    ] },
  { path: 'loans', children: [
      { path: 'search', component: LoansComponent, canActivate:[AuthGuardianService] },
      { path: 'new', component: NewLoanComponent, canActivate:[AuthGuardianService] }
    ]
  },
  { path: '**', component: NotfoundComponent }
];
