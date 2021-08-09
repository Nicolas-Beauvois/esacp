import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CorrectPreventComponent } from './list/correct-prevent.component';
import { CorrectPreventDetailComponent } from './detail/correct-prevent-detail.component';
import { CorrectPreventUpdateComponent } from './update/correct-prevent-update.component';
import { CorrectPreventDeleteDialogComponent } from './delete/correct-prevent-delete-dialog.component';
import { CorrectPreventRoutingModule } from './route/correct-prevent-routing.module';

@NgModule({
  imports: [SharedModule, CorrectPreventRoutingModule],
  declarations: [
    CorrectPreventComponent,
    CorrectPreventDetailComponent,
    CorrectPreventUpdateComponent,
    CorrectPreventDeleteDialogComponent,
  ],
  entryComponents: [CorrectPreventDeleteDialogComponent],
})
export class CorrectPreventModule {}
