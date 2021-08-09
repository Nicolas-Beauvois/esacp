import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPriorite } from '../priorite.model';
import { PrioriteService } from '../service/priorite.service';

@Component({
  templateUrl: './priorite-delete-dialog.component.html',
})
export class PrioriteDeleteDialogComponent {
  priorite?: IPriorite;

  constructor(protected prioriteService: PrioriteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prioriteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
