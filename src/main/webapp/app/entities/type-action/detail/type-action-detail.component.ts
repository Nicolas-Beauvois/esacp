import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeAction } from '../type-action.model';

@Component({
  selector: 'jhi-type-action-detail',
  templateUrl: './type-action-detail.component.html',
})
export class TypeActionDetailComponent implements OnInit {
  typeAction: ITypeAction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAction }) => {
      this.typeAction = typeAction;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
