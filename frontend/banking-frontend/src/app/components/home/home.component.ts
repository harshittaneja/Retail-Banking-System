import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/_services/authentication.service';
import { NavbarService } from 'src/app/_services/navbar.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private authenticationService:AuthenticationService,private nav:NavbarService) { }
  isAdmin:boolean=false;
  ngOnInit(): void {
    this.nav.show();
    if(this.authenticationService.userValue.roles.includes("ADMIN")){
      this.isAdmin=true;
    }
    
  }
 

}
