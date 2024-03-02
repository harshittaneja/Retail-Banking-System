import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { CustomerService } from 'src/app/_services/customer.service';

@Component({
  selector: 'app-account-transactions',
  templateUrl: './account-transactions.component.html',
  styleUrls: ['./account-transactions.component.css'],
})
export class AccountTransactionsComponent implements OnInit {
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
  constructor(private customerService:CustomerService,private authenticationService:AuthenticationService) {}

  ngOnInit(): void {
    console.log(this.authenticationService.userValue)
    this.customerService
    .viewTransactions(this.authenticationService.userValue.username)
    .subscribe((response) => {
      this.transaction = response;
      console.log(response);
      
    });
  }
}
