import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { CustomerService } from 'src/app/_services/customer.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  loading: boolean = true;
  customerId: string;

  userDetails:{
    customerId:number;
    address:string;
    dob:string;
    panNumber:string;
    username:string;
  };

  constructor(
    private customerService: CustomerService,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.customerId = this.authenticationService.userValue.username;
    this.customerService
      .getUserDetails(this.customerId)
      .subscribe((response) => {
        this.userDetails=response;
        this.loading=false;
      });
      
  }
}
