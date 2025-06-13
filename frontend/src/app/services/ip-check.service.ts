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
    return this.http.get<TraceIpResponse>(`${this.baseUrl}/trace-ip/api/country-info`, {
      params: { ip }
    });
  }

  getStat(type: 'min' | 'max' | 'avg'): Observable<number> {
    const urlMap = {
      max: `${this.baseUrl}/stats/max-distance`,
      min: `${this.baseUrl}/stats/min-distance`,
      avg: `${this.baseUrl}/stats/average-distance`
    };
    return this.http.get<number>(urlMap[type]);
  }

}
