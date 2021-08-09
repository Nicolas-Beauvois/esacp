import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRepartition } from '../repartition.model';

@Component({
  selector: 'jhi-repartition-detail',
  templateUrl: './repartition-detail.component.html',
})
export class RepartitionDetailComponent implements OnInit {
  repartition: IRepartition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ repartition }) => {
      this.repartition = repartition;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
