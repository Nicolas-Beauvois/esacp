import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISiegeLesions } from '../siege-lesions.model';
import { SiegeLesionsService } from '../service/siege-lesions.service';

@Component({
  templateUrl: './siege-lesions-delete-dialog.component.html',
})
export class SiegeLesionsDeleteDialogComponent {
  siegeLesions?: ISiegeLesions;

  constructor(protected siegeLesionsService: SiegeLesionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.siegeLesionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
