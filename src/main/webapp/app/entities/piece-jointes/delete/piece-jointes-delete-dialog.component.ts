import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPieceJointes } from '../piece-jointes.model';
import { PieceJointesService } from '../service/piece-jointes.service';

@Component({
  templateUrl: './piece-jointes-delete-dialog.component.html',
})
export class PieceJointesDeleteDialogComponent {
  pieceJointes?: IPieceJointes;

  constructor(protected pieceJointesService: PieceJointesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pieceJointesService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
