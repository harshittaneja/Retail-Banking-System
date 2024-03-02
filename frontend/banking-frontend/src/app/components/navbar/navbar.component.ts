import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { NavbarService } from 'src/app/_services/navbar.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public nav: NavbarService,private authenticationService:AuthenticationService) { }

  ngOnInit(): void {
  }

  onLogout(){
    this.authenticationService.logout();
  }

  

}
