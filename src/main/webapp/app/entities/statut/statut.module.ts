import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StatutComponent } from './list/statut.component';
import { StatutDetailComponent } from './detail/statut-detail.component';
import { StatutUpdateComponent } from './update/statut-update.component';
import { StatutDeleteDialogComponent } from './delete/statut-delete-dialog.component';
import { StatutRoutingModule } from './route/statut-routing.module';

@NgModule({
  imports: [SharedModule, StatutRoutingModule],
  declarations: [StatutComponent, StatutDetailComponent, StatutUpdateComponent, StatutDeleteDialogComponent],
  entryComponents: [StatutDeleteDialogComponent],
})
export class StatutModule {}
