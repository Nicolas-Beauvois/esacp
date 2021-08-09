import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICriticite } from '../criticite.model';
import { CriticiteService } from '../service/criticite.service';

@Component({
  templateUrl: './criticite-delete-dialog.component.html',
})
export class CriticiteDeleteDialogComponent {
  criticite?: ICriticite;

  constructor(protected criticiteService: CriticiteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.criticiteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
