import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { IpCheckService } from '../../services/ip-check.service';

@Component({
  selector: 'app-statistics-info',
  imports: [CommonModule],
  templateUrl: './statistics-info.component.html',
  styleUrl: './statistics-info.component.css'
})
export class StatisticsInfoComponent {

  maxDistance?: number;
  minDistance?: number;
  avgDistance?: number;
  error?: string;

  constructor(private ipCheckService: IpCheckService, private router: Router) {
    this.loadStats();
  }

  loadStats() {
    this.ipCheckService.getStat('max').subscribe({
      next: value => this.maxDistance = value,
      error: () => this.error = 'Error loading max'
    });

    this.ipCheckService.getStat('min').subscribe({
      next: value => this.minDistance = value,
      error: () => this.error = 'Error loading min'
    });

    this.ipCheckService.getStat('avg').subscribe({
      next: value => this.avgDistance = value,
      error: () => this.error = 'Error loading avg'
    });
  }

  goBack() {
    this.router.navigate(['']);
  }

  hasStats(): boolean {
    return !!(this.maxDistance || this.minDistance || this.avgDistance);
  }

}
