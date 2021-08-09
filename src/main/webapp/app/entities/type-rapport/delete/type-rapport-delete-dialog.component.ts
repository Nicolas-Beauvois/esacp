import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeRapport } from '../type-rapport.model';
import { TypeRapportService } from '../service/type-rapport.service';

@Component({
  templateUrl: './type-rapport-delete-dialog.component.html',
})
export class TypeRapportDeleteDialogComponent {
  typeRapport?: ITypeRapport;

  constructor(protected typeRapportService: TypeRapportService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeRapportService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
