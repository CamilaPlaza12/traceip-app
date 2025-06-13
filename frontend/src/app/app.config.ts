import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { IpCheckService } from './services/ip-check.service';
import { HttpClientModule } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
   providers: [
    provideRouter(routes),
    importProvidersFrom(HttpClientModule),
    IpCheckService,
  ]
};
