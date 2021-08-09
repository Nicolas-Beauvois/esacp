import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFicheSuiviSante } from '../fiche-suivi-sante.model';

@Component({
  selector: 'jhi-fiche-suivi-sante-detail',
  templateUrl: './fiche-suivi-sante-detail.component.html',
})
export class FicheSuiviSanteDetailComponent implements OnInit {
  ficheSuiviSante: IFicheSuiviSante | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ficheSuiviSante }) => {
      this.ficheSuiviSante = ficheSuiviSante;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
