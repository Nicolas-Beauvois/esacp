import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrigineLesions } from '../origine-lesions.model';
import { OrigineLesionsService } from '../service/origine-lesions.service';

@Component({
  templateUrl: './origine-lesions-delete-dialog.component.html',
})
export class OrigineLesionsDeleteDialogComponent {
  origineLesions?: IOrigineLesions;

  constructor(protected origineLesionsService: OrigineLesionsService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.origineLesionsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
