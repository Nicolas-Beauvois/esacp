import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEtapeValidation } from '../etape-validation.model';
import { EtapeValidationService } from '../service/etape-validation.service';

@Component({
  templateUrl: './etape-validation-delete-dialog.component.html',
})
export class EtapeValidationDeleteDialogComponent {
  etapeValidation?: IEtapeValidation;

  constructor(protected etapeValidationService: EtapeValidationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.etapeValidationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
