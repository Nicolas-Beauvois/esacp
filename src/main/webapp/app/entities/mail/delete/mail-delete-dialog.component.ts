import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMail } from '../mail.model';
import { MailService } from '../service/mail.service';

@Component({
  templateUrl: './mail-delete-dialog.component.html',
})
export class MailDeleteDialogComponent {
  mail?: IMail;

  constructor(protected mailService: MailService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mailService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
