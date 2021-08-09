import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrigineLesions } from '../origine-lesions.model';

@Component({
  selector: 'jhi-origine-lesions-detail',
  templateUrl: './origine-lesions-detail.component.html',
})
export class OrigineLesionsDetailComponent implements OnInit {
  origineLesions: IOrigineLesions | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ origineLesions }) => {
      this.origineLesions = origineLesions;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
