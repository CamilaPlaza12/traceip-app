import { CountryInfo } from './country-info.model';

export interface TraceIpResponse {
  ip: string;
  countryInfo: CountryInfo;
}
