import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { CustomerService } from 'src/app/_services/customer.service';

@Component({
  selector: 'app-account-transfer',
  templateUrl: './account-transfer.component.html',
  styleUrls: ['./account-transfer.component.css']
})
export class AccountTransferComponent implements OnInit {

  constructor(private customerService:CustomerService) { }
  msg:string;
  ngOnInit(): void {
  }
  onTransfer(f: NgForm) {
    // console.log(f.value);
    this.customerService
      .transfer(f.value.sourceAccountNumber, f.value.destinationAccountNumber,f.value.amount)
      .subscribe((response) => {
        if(response.status=='SUCCESSFULL'){
          this.msg = `Transaction Successfull`;
        }else{
          this.msg=`Insufficient Balance`;
        }
        
      });
  }
}
