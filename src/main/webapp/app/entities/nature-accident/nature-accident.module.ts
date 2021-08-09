import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NatureAccidentComponent } from './list/nature-accident.component';
import { NatureAccidentDetailComponent } from './detail/nature-accident-detail.component';
import { NatureAccidentUpdateComponent } from './update/nature-accident-update.component';
import { NatureAccidentDeleteDialogComponent } from './delete/nature-accident-delete-dialog.component';
import { NatureAccidentRoutingModule } from './route/nature-accident-routing.module';

@NgModule({
  imports: [SharedModule, NatureAccidentRoutingModule],
  declarations: [
    NatureAccidentComponent,
    NatureAccidentDetailComponent,
    NatureAccidentUpdateComponent,
    NatureAccidentDeleteDialogComponent,
  ],
  entryComponents: [NatureAccidentDeleteDialogComponent],
})
export class NatureAccidentModule {}
