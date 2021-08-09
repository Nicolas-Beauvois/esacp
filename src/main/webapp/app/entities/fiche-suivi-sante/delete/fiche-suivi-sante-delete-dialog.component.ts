import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFicheSuiviSante } from '../fiche-suivi-sante.model';
import { FicheSuiviSanteService } from '../service/fiche-suivi-sante.service';

@Component({
  templateUrl: './fiche-suivi-sante-delete-dialog.component.html',
})
export class FicheSuiviSanteDeleteDialogComponent {
  ficheSuiviSante?: IFicheSuiviSante;

  constructor(protected ficheSuiviSanteService: FicheSuiviSanteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ficheSuiviSanteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
