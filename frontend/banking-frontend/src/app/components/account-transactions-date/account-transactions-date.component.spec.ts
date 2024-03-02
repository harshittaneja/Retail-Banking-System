import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountTransactionsDateComponent } from './account-transactions-date.component';

describe('AccountTransactionsDateComponent', () => {
  let component: AccountTransactionsDateComponent;
  let fixture: ComponentFixture<AccountTransactionsDateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountTransactionsDateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountTransactionsDateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
