import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtapeValidation } from '../etape-validation.model';

@Component({
  selector: 'jhi-etape-validation-detail',
  templateUrl: './etape-validation-detail.component.html',
})
export class EtapeValidationDetailComponent implements OnInit {
  etapeValidation: IEtapeValidation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etapeValidation }) => {
      this.etapeValidation = etapeValidation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
