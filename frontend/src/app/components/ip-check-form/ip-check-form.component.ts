import { Component} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IpCheckService } from '../../services/ip-check.service';
import { Router } from '@angular/router';
import { TraceIpResponse } from '../../models/trace-ip-response.model';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-ip-check-form',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  providers: [IpCheckService],
  templateUrl: './ip-check-form.component.html',
  styleUrls: ['./ip-check-form.component.css']
})

export class IpCheckFormComponent {
  ipAddress = '';
  traceData?: TraceIpResponse;
  statResult?: number;
  error?: string;
  formVisible = true;
  loading = false;

  constructor(private traceService: IpCheckService, private router: Router) {}

  searchIp() {
    this.loading = true;
    this.traceService.getTraceInfo(this.ipAddress).subscribe({
      next: data => {
        this.traceData = data;
        this.error = undefined;
        this.formVisible = false;
      },
      error: err => {
        this.error = 'IP inválida o error de servidor';
        this.traceData = undefined;
      },
      complete: () => {
        this.loading = false;
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

  goToStats() {
    this.router.navigate(['/statistics']);
  }


}
