import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CareComponentComponent } from './care-component.component';

describe('CareComponentComponent', () => {
  let component: CareComponentComponent;
  let fixture: ComponentFixture<CareComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CareComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CareComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
