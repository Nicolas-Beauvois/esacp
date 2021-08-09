import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRepartition } from '../repartition.model';
import { RepartitionService } from '../service/repartition.service';

@Component({
  templateUrl: './repartition-delete-dialog.component.html',
})
export class RepartitionDeleteDialogComponent {
  repartition?: IRepartition;

  constructor(protected repartitionService: RepartitionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.repartitionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
