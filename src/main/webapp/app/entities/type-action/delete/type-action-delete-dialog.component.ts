import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypeAction } from '../type-action.model';
import { TypeActionService } from '../service/type-action.service';

@Component({
  templateUrl: './type-action-delete-dialog.component.html',
})
export class TypeActionDeleteDialogComponent {
  typeAction?: ITypeAction;

  constructor(protected typeActionService: TypeActionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typeActionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
