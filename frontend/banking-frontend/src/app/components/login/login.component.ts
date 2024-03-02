import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { NavbarService } from 'src/app/_services/navbar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authenticationService:AuthenticationService,private router:Router,private nav:NavbarService) { }
  error:string;


  ngOnInit(): void {
    this.nav.hide();
  }
  onSubmit(f: NgForm) {
    console.log(f.value.username,f.value.password);
    this.authenticationService
    .login(f.value.username, f.value.password)
    .pipe(first())
    .subscribe({
      next: () => {
       
        this.router.navigateByUrl('/home');
        console.log("Authentication Successfull");
      },
      error: (error) => {
        this.error = error;
        // this.loading = false;
        console.log(error);
      },
    });
  }

}
