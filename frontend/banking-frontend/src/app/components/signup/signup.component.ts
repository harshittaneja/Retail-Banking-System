import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { first } from 'rxjs';
import { AuthenticationService } from 'src/app/_services/authentication.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent implements OnInit {
  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}
  error: string;
  roles: string[] = [];
  msg:string;

  ngOnInit(): void {}
  onSubmit(f: NgForm) {
    console.log(f.value)
    if (f.value.admin) {
      this.roles.push('ADMIN');
    }
    if (f.value.user) {
      this.roles.push('USER');
    }

    this.authenticationService
      .create(f.value.name, f.value.username, f.value.password, this.roles)
      .pipe(first())
      .subscribe({
        next: () => {
          this.router.navigateByUrl('/signup');
          this.msg='Creation Successfull';
        },
        error: (error) => {
          this.error = error;
          // this.loading = false;
          console.log(error);
        },
      });
  }
}
