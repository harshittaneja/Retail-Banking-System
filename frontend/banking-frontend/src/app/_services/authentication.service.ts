import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../_models/User';
@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private userSubject: BehaviorSubject<User>;
  private user: Observable<User>;

  constructor(private router: Router, private http: HttpClient) {
    this.userSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem('user') || '{}')
    );
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): User {
    return this.userSubject.value;
  }

  login(username: string, password: string) {
    return this.http
      .post<any>(`http://localhost:9005/user/login`, {
        username,
        password,
      })
      .pipe(
        map((user) => {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('user', JSON.stringify(user));
          this.userSubject.next(user);
          return user;
        })
      );
  }

  create(name:string,username: string, password: string,roles:string[]) {
    
    return this.http
      .post<any>(`http://localhost:9005/user/save`, {
        name,
        username,
        password,
        roles,
      })
  }
  createCustomer(name:string,username: string, address: string,dob:string,panNumber:string) {
    
    return this.http
      .post<any>(`http://localhost:9004/customer/createCustomer`, {
        name,
        username,
        address,
        dob,
        panNumber
      })
  }

  logout() {
    localStorage.removeItem('user');
    // this.userSubject.next(null);
    this.router.navigate(['']);
  }


}
