import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPieceJointes } from '../piece-jointes.model';

@Component({
  selector: 'jhi-piece-jointes-detail',
  templateUrl: './piece-jointes-detail.component.html',
})
export class PieceJointesDetailComponent implements OnInit {
  pieceJointes: IPieceJointes | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pieceJointes }) => {
      this.pieceJointes = pieceJointes;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
