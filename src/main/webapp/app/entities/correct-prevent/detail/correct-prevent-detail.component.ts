import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICorrectPrevent } from '../correct-prevent.model';

@Component({
  selector: 'jhi-correct-prevent-detail',
  templateUrl: './correct-prevent-detail.component.html',
})
export class CorrectPreventDetailComponent implements OnInit {
  correctPrevent: ICorrectPrevent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ correctPrevent }) => {
      this.correctPrevent = correctPrevent;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
