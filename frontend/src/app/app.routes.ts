import { Routes } from '@angular/router';
import { IpCheckFormComponent } from './components/ip-check-form/ip-check-form.component';
import { StatisticsInfoComponent } from './components/statistics-info/statistics-info.component';

export const routes: Routes = [
  { path: '', component: IpCheckFormComponent },
  { path: 'statistics', component: StatisticsInfoComponent }
];
