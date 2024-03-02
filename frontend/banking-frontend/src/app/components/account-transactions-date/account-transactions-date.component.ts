import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { CustomerService } from 'src/app/_services/customer.service';
import { NavbarService } from 'src/app/_services/navbar.service';

@Component({
  selector: 'app-account-transactions-date',
  templateUrl: './account-transactions-date.component.html',
  styleUrls: ['./account-transactions-date.component.css'],
})
export class AccountTransactionsDateComponent implements OnInit {
  loading:boolean=true;
  transaction: {
    transactionId: number;
    trsansactionStatus: string;
    transactionType: string;
    transactionDate: string;
    transactionMethod: string;
    counterparties: string;
    amountOfTransaction: number;
    accountId: number;
  }[];
  msg: string;
  constructor(
    private authenticationService: AuthenticationService,
    private customerService: CustomerService,
    private nav:NavbarService
  ) {}

  ngOnInit(): void {
    this.nav.show();
  }
  onClick(f: NgForm) {
    // console.log(f.value);
   
    this.customerService
      .viewTransactionsByDate(f.value.accountNumber,f.value.startdate,f.value.enddate)
      .subscribe((response) => {
        this.transaction = response;
        this.loading=false;
        
      });
  }
}
