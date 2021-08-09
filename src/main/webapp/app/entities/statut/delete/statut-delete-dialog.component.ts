import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStatut } from '../statut.model';
import { StatutService } from '../service/statut.service';

@Component({
  templateUrl: './statut-delete-dialog.component.html',
})
export class StatutDeleteDialogComponent {
  statut?: IStatut;

  constructor(protected statutService: StatutService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.statutService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
