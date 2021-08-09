import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PieceJointesComponent } from './list/piece-jointes.component';
import { PieceJointesDetailComponent } from './detail/piece-jointes-detail.component';
import { PieceJointesUpdateComponent } from './update/piece-jointes-update.component';
import { PieceJointesDeleteDialogComponent } from './delete/piece-jointes-delete-dialog.component';
import { PieceJointesRoutingModule } from './route/piece-jointes-routing.module';

@NgModule({
  imports: [SharedModule, PieceJointesRoutingModule],
  declarations: [PieceJointesComponent, PieceJointesDetailComponent, PieceJointesUpdateComponent, PieceJointesDeleteDialogComponent],
  entryComponents: [PieceJointesDeleteDialogComponent],
})
export class PieceJointesModule {}
