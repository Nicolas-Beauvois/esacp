import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EtapeValidationComponent } from './list/etape-validation.component';
import { EtapeValidationDetailComponent } from './detail/etape-validation-detail.component';
import { EtapeValidationUpdateComponent } from './update/etape-validation-update.component';
import { EtapeValidationDeleteDialogComponent } from './delete/etape-validation-delete-dialog.component';
import { EtapeValidationRoutingModule } from './route/etape-validation-routing.module';

@NgModule({
  imports: [SharedModule, EtapeValidationRoutingModule],
  declarations: [
    EtapeValidationComponent,
    EtapeValidationDetailComponent,
    EtapeValidationUpdateComponent,
    EtapeValidationDeleteDialogComponent,
  ],
  entryComponents: [EtapeValidationDeleteDialogComponent],
})
export class EtapeValidationModule {}
