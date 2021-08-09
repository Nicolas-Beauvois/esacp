import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypeActionComponent } from './list/type-action.component';
import { TypeActionDetailComponent } from './detail/type-action-detail.component';
import { TypeActionUpdateComponent } from './update/type-action-update.component';
import { TypeActionDeleteDialogComponent } from './delete/type-action-delete-dialog.component';
import { TypeActionRoutingModule } from './route/type-action-routing.module';

@NgModule({
  imports: [SharedModule, TypeActionRoutingModule],
  declarations: [TypeActionComponent, TypeActionDetailComponent, TypeActionUpdateComponent, TypeActionDeleteDialogComponent],
  entryComponents: [TypeActionDeleteDialogComponent],
})
export class TypeActionModule {}
