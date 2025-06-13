import { TestBed } from '@angular/core/testing';

import { IpCheckService } from './ip-check.service';

describe('IpCheckService', () => {
  let service: IpCheckService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IpCheckService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
