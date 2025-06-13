import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IpCheckService } from '../../services/ip-check.service';
import { TraceIpResponse } from '../../models/trace-ip-response.model';

@Component({
  selector: 'app-ip-check-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ip-check-form.component.html',
  styleUrls: ['./ip-check-form.component.css']
})
export class IpCheckFormComponent {
  ipAddress = '8.8.8.7';
  traceData?: TraceIpResponse;
  statResult?: number;
  error?: string;
  formVisible = true;

  constructor(private traceService: IpCheckService) {}

  searchIp() {
    this.traceService.getMockTraceInfo(this.ipAddress).subscribe({
      next: data => {
        this.traceData = data;
        this.error = undefined;
        this.formVisible = false;
      },
      error: err => {
        this.error = 'IP inválida o error de servidor';
        this.traceData = undefined;
      }
    });
  }

  consultStatistics(tipo: 'min' | 'max' | 'avg') {
    this.traceService.getStat(tipo).subscribe({
      next: result => {
        this.statResult = result;
        this.error = undefined;
      },
      error: err => {
        this.error = 'Error al consultar estadística';
      }
    });
  }

  goBack() {
    this.formVisible = true;
    this.traceData = undefined;
    this.error = undefined;
    this.ipAddress = '';
  }

  isValidIp(ip: string): boolean {
    if (!ip) return false;
    const parts = ip.split('.');
    if (parts.length !== 4) return false;
    for (const part of parts) {
      if (part.trim() === '') return false;
      const num = Number(part);
      if (isNaN(num) || num < 0 || num > 255) return false;
    }
    return true;
  }

  validateKey(event: KeyboardEvent) {
    const allowedKeys = ['0','1','2','3','4','5','6','7','8','9','.'];
    if (!allowedKeys.includes(event.key)) {
      event.preventDefault();
    }
  }



}
