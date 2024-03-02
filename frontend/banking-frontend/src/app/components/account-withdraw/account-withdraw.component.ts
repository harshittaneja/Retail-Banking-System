import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CustomerService } from 'src/app/_services/customer.service';

@Component({
  selector: 'app-account-withdraw',
  templateUrl: './account-withdraw.component.html',
  styleUrls: ['./account-withdraw.component.css'],
})
export class AccountWithdrawComponent implements OnInit {
  msg: string;
  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {}
  onWithdraw(f: NgForm) {
    this.customerService
      .withdraw(f.value.accountId, f.value.amount)
      .subscribe((response) => {
        if(response.status=='SUCCESSFULL'){
          this.msg = `Account id :${f.value.accountId} successfully debited with ${f.value.amount}`;
        }else{
          this.msg='Insufficient Balance';
        }
        
        
      });
  }
}
