import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeAt } from '../type-at.model';

@Component({
  selector: 'jhi-type-at-detail',
  templateUrl: './type-at-detail.component.html',
})
export class TypeAtDetailComponent implements OnInit {
  typeAt: ITypeAt | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAt }) => {
      this.typeAt = typeAt;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
