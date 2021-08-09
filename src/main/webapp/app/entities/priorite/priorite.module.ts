import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PrioriteComponent } from './list/priorite.component';
import { PrioriteDetailComponent } from './detail/priorite-detail.component';
import { PrioriteUpdateComponent } from './update/priorite-update.component';
import { PrioriteDeleteDialogComponent } from './delete/priorite-delete-dialog.component';
import { PrioriteRoutingModule } from './route/priorite-routing.module';

@NgModule({
  imports: [SharedModule, PrioriteRoutingModule],
  declarations: [PrioriteComponent, PrioriteDetailComponent, PrioriteUpdateComponent, PrioriteDeleteDialogComponent],
  entryComponents: [PrioriteDeleteDialogComponent],
})
export class PrioriteModule {}
