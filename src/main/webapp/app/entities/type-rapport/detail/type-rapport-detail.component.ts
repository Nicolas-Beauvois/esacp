import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeRapport } from '../type-rapport.model';

@Component({
  selector: 'jhi-type-rapport-detail',
  templateUrl: './type-rapport-detail.component.html',
})
export class TypeRapportDetailComponent implements OnInit {
  typeRapport: ITypeRapport | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeRapport }) => {
      this.typeRapport = typeRapport;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
