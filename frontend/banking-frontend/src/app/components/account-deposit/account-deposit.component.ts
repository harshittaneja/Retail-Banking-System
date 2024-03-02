import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CustomerService } from 'src/app/_services/customer.service';

@Component({
  selector: 'app-account-deposit',
  templateUrl: './account-deposit.component.html',
  styleUrls: ['./account-deposit.component.css']
})
export class AccountDepositComponent implements OnInit {

  constructor(private customerService:CustomerService) { }
  msg:string;
  ngOnInit(): void {
  }

  onDeposit(f:NgForm){
  
    this.customerService.deposit(f.value.accountId,f.value.amount).subscribe((response)=>{
      this.msg=`Account id :${f.value.accountId} successfully credited with ${f.value.amount}`;
    })
    
  }

}
