import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICriticite } from '../criticite.model';

@Component({
  selector: 'jhi-criticite-detail',
  templateUrl: './criticite-detail.component.html',
})
export class CriticiteDetailComponent implements OnInit {
  criticite: ICriticite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criticite }) => {
      this.criticite = criticite;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
