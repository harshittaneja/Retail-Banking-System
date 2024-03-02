import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs';
import { AuthenticationService } from 'src/app/_services/authentication.service';

@Component({
  selector: 'app-create-customer',
  templateUrl: './create-customer.component.html',
  styleUrls: ['./create-customer.component.css'],
})
export class CreateCustomerComponent implements OnInit {
  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}
  error: string = '';
  msg: string = '';
  roles:string[]=[];
  ngOnInit(): void {}

  onSubmit(f: NgForm) {
    this.authenticationService
      .createCustomer(
        f.value.name,
        f.value.username,
        f.value.address,
        f.value.dob,
        f.value.panNumber
      )
      .subscribe((response) => {
        console.log(response);
        
        this.roles.push("CUSTOMER");
        this.authenticationService.create(f.value.name,response.customerId,f.value.password,this.roles).subscribe((reponse)=>{
          this.msg = `Customer has been created successfully with the account numbers ${response.accountNumbers[0]} , ${response.accountNumbers[1]} .Your customerid is :${response.customerId} and password is ${f.value.password}`;

        })
      });
  }
}
