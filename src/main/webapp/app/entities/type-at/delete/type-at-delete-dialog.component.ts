import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeAt } from '../type-at.model';
import { TypeAtService } from '../service/type-at.service';

@Component({
  templateUrl: './type-at-delete-dialog.component.html',
})
export class TypeAtDeleteDialogComponent {
  typeAt?: ITypeAt;

  constructor(protected typeAtService: TypeAtService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeAtService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
