import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiegeLesions } from '../siege-lesions.model';

@Component({
  selector: 'jhi-siege-lesions-detail',
  templateUrl: './siege-lesions-detail.component.html',
})
export class SiegeLesionsDetailComponent implements OnInit {
  siegeLesions: ISiegeLesions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siegeLesions }) => {
      this.siegeLesions = siegeLesions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
