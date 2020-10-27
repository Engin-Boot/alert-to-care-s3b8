import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetAlertComponent } from './get-alert.component';

describe('GetAlertComponent', () => {
  let component: GetAlertComponent;
  let fixture: ComponentFixture<GetAlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetAlertComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GetAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
