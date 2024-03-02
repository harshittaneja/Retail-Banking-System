import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountDepositComponent } from './components/account-deposit/account-deposit.component';
import { AccountDetailsComponent } from './components/account-details/account-details.component';
import { AccountTransactionsDateComponent } from './components/account-transactions-date/account-transactions-date.component';
import { AccountTransactionsComponent } from './components/account-transactions/account-transactions.component';
import { AccountTransferComponent } from './components/account-transfer/account-transfer.component';
import { AccountWithdrawComponent } from './components/account-withdraw/account-withdraw.component';
import { CreateCustomerComponent } from './components/create-customer/create-customer.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { AuthGuard } from './_helpers/auth.guard';

const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'signup', component: SignupComponent,canActivate: [AuthGuard],  },
  { path: 'home', component: HomeComponent,canActivate: [AuthGuard], },
  { path: 'home/create-customer', component: CreateCustomerComponent ,canActivate: [AuthGuard] },
  { path: 'home/account-details', component: AccountDetailsComponent ,canActivate: [AuthGuard] },
  { path: 'home/account-deposit', component: AccountDepositComponent ,canActivate: [AuthGuard] },
  { path: 'home/account-withdraw', component: AccountWithdrawComponent ,canActivate: [AuthGuard] },
  {
    path: 'home/account-transactions',
    component: AccountTransactionsComponent,
    canActivate: [AuthGuard] 
  },
  { path: 'home/account-transfer', component: AccountTransferComponent ,canActivate: [AuthGuard] },
  {
    path: 'home/account-transactions-date',
    component: AccountTransactionsDateComponent,
    canActivate: [AuthGuard] 
  },
  {
    path: 'home/user-details',
    component: UserDetailsComponent,
    canActivate: [AuthGuard] 
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
