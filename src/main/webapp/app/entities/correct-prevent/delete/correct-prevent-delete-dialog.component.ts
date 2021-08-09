import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICorrectPrevent } from '../correct-prevent.model';
import { CorrectPreventService } from '../service/correct-prevent.service';

@Component({
  templateUrl: './correct-prevent-delete-dialog.component.html',
})
export class CorrectPreventDeleteDialogComponent {
  correctPrevent?: ICorrectPrevent;

  constructor(protected correctPreventService: CorrectPreventService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.correctPreventService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
