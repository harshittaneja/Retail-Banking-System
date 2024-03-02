import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  constructor(private router: Router, private http: HttpClient) {}

  getAccountDetails(customerId: string) {
    return this.http.get<any>(
      `http://localhost:9001/account/getCustomerAccounts/` + customerId
    );
  }

  deposit(accountId: number, amount: number) {
    return this.http.post<any>(
      `http://localhost:9001/account/deposit/` + accountId + `/` + amount,
      {}
    );
  }
  withdraw(accountId: number, amount: number) {
    return this.http.post<any>(
      `http://localhost:9001/account/withdraw/` + accountId + `/` + amount,
      {}
    );
  }
  viewTransactions(customerId: string) {
    return this.http.get<any>(
      `http://localhost:9001/account/getAllTransactions/${customerId}`
    );
  }

  viewTransactionsByDate(
    accountId: number,
    startDate: string,
    endDate: string
  ) {
    return this.http.get<any>(
      `http://localhost:9003/transaction/getAllTransactions/${startDate}/${endDate}/${accountId}`
    );
  }

  transfer(
    sourceAccountId: number,
    destinationAccount: number,
    amount: number
  ) {
    return this.http.post<any>(
      `http://localhost:9001/account/transfer/${sourceAccountId}/${destinationAccount}/${amount}`,
      {}
    );
  }


  getUserDetails(
    userId:string
  ) {
    return this.http.get<any>(
      `http://localhost:9004/customer/getCustomerDetails/${userId};`
    );
  }
}
