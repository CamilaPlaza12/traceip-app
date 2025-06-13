import { Component } from '@angular/core';
import { IpCheckFormComponent } from './components/ip-check-form/ip-check-form.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [IpCheckFormComponent],
  template: `<app-ip-check-form />`,
})
export class AppComponent {}
