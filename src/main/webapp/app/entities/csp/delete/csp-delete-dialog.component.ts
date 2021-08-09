import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICsp } from '../csp.model';
import { CspService } from '../service/csp.service';

@Component({
  templateUrl: './csp-delete-dialog.component.html',
})
export class CspDeleteDialogComponent {
  csp?: ICsp;

  constructor(protected cspService: CspService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cspService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
