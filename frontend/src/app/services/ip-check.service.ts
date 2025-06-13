import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TraceIpResponse } from '../models/trace-ip-response.model';
import { Observable } from 'rxjs';
import { of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IpCheckService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getTraceInfo(ip: string): Observable<TraceIpResponse> {
    return this.http.get<TraceIpResponse>(`${this.baseUrl}/ip/${ip}`);
  }

  getMockTraceInfo(ip: string): Observable<TraceIpResponse> {
  const mockData: TraceIpResponse = {
    ip: ip,
    countryInfo: {
      name: "Spain",
      isoCode: "ES",
      languages: ["Spanish"],
      currentTimes: [
        "2025-06-12T17:31:21.608178800Z[UTC]",
        "2025-06-12T18:31:21.608178800+01:00[UTC+01:00]",
        "2025-06-12T17:31:21.608178800Z[UTC]"

      ],
      distanceFromBuenosAiresKm: 9993.69,
      localCurrency: "EUR",
      dollarExchangeRate: 1.156832
    }
  };

  return of(mockData);
}

  getStat(type: 'min' | 'max' | 'avg'): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/stats/${type}`);
  }
}
