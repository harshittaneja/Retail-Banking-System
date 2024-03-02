import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-card',
  templateUrl: './home-card.component.html',
  styleUrls: ['./home-card.component.css']
})
export class HomeCardComponent implements OnInit {

  @Input() text:string;
  @Input() title:string;
  @Input() link:string;
  @Input() btnText:string;
  constructor() { }

  ngOnInit(): void {
  }

}
