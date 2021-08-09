import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INatureAccident } from '../nature-accident.model';
import { NatureAccidentService } from '../service/nature-accident.service';

@Component({
  templateUrl: './nature-accident-delete-dialog.component.html',
})
export class NatureAccidentDeleteDialogComponent {
  natureAccident?: INatureAccident;

  constructor(protected natureAccidentService: NatureAccidentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureAccidentService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
