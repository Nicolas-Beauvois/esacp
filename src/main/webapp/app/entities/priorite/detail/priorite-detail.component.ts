import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriorite } from '../priorite.model';

@Component({
  selector: 'jhi-priorite-detail',
  templateUrl: './priorite-detail.component.html',
})
export class PrioriteDetailComponent implements OnInit {
  priorite: IPriorite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priorite }) => {
      this.priorite = priorite;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
