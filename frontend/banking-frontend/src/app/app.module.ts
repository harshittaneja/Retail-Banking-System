import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { HomeComponent } from './components/home/home.component';
import { HomeCardComponent } from './components/home-card/home-card.component';
import { CreateCustomerComponent } from './components/create-customer/create-customer.component';
import { AccountDetailsComponent } from './components/account-details/account-details.component';
import { JwtInterceptor } from './_helpers/jwt.interceptor';
import { AccountDepositComponent } from './components/account-deposit/account-deposit.component';
import { AccountWithdrawComponent } from './components/account-withdraw/account-withdraw.component';
import { AccountTransactionsComponent } from './components/account-transactions/account-transactions.component';
import { AccountTransferComponent } from './components/account-transfer/account-transfer.component';
import { AccountTransactionsDateComponent } from './components/account-transactions-date/account-transactions-date.component';
import { AccountTransactionsTableComponent } from './components/account-transactions-table/account-transactions-table.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { NavbarComponent } from './components/navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    HomeComponent,
    HomeCardComponent,
    CreateCustomerComponent,
    AccountDetailsComponent,
    AccountDepositComponent,
    AccountWithdrawComponent,
    AccountTransactionsComponent,
    AccountTransferComponent,
    AccountTransactionsDateComponent,
    AccountTransactionsTableComponent,
    UserDetailsComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },],
  bootstrap: [AppComponent]
})
export class AppModule { }
