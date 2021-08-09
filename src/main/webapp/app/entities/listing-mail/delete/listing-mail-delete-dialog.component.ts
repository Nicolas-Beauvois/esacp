import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IListingMail } from '../listing-mail.model';
import { ListingMailService } from '../service/listing-mail.service';

@Component({
  templateUrl: './listing-mail-delete-dialog.component.html',
})
export class ListingMailDeleteDialogComponent {
  listingMail?: IListingMail;

  constructor(protected listingMailService: ListingMailService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.listingMailService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
