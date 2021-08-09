import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureAccident } from '../nature-accident.model';

@Component({
  selector: 'jhi-nature-accident-detail',
  templateUrl: './nature-accident-detail.component.html',
})
export class NatureAccidentDetailComponent implements OnInit {
  natureAccident: INatureAccident | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureAccident }) => {
      this.natureAccident = natureAccident;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
