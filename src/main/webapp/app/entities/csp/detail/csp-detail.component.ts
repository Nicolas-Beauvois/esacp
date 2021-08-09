import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICsp } from '../csp.model';

@Component({
  selector: 'jhi-csp-detail',
  templateUrl: './csp-detail.component.html',
})
export class CspDetailComponent implements OnInit {
  csp: ICsp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ csp }) => {
      this.csp = csp;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
