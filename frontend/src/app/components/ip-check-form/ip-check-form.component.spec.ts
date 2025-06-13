import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IpCheckFormComponent } from './ip-check-form.component';

describe('IpCheckFormComponent', () => {
  let component: IpCheckFormComponent;
  let fixture: ComponentFixture<IpCheckFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IpCheckFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IpCheckFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
