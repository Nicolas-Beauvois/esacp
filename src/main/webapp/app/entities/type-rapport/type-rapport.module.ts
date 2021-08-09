import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypeRapportComponent } from './list/type-rapport.component';
import { TypeRapportDetailComponent } from './detail/type-rapport-detail.component';
import { TypeRapportUpdateComponent } from './update/type-rapport-update.component';
import { TypeRapportDeleteDialogComponent } from './delete/type-rapport-delete-dialog.component';
import { TypeRapportRoutingModule } from './route/type-rapport-routing.module';

@NgModule({
  imports: [SharedModule, TypeRapportRoutingModule],
  declarations: [TypeRapportComponent, TypeRapportDetailComponent, TypeRapportUpdateComponent, TypeRapportDeleteDialogComponent],
  entryComponents: [TypeRapportDeleteDialogComponent],
})
export class TypeRapportModule {}
