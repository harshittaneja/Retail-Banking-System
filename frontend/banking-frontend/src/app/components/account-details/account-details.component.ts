import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { CustomerService } from 'src/app/_services/customer.service';
import { NavbarService } from 'src/app/_services/navbar.service';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {

  loading:boolean=true;
  customerDetails:{accountId:number,customerId:number,accountType:String,balance:number}[
    
  ];

  constructor(private customerService:CustomerService,private authenticationService:AuthenticationService,private router:Router,private nav:NavbarService) {
    this.nav.show();
   }

  ngOnInit(): void {
  
    const userValue=this.authenticationService.userValue;
   
    this.customerService.getAccountDetails(userValue.username).subscribe((response)=>{
     
      this.customerDetails=response;
      this.loading=false;
      console.log(this.customerDetails)
    })



  }

}
