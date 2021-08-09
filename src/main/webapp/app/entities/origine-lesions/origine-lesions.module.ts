import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrigineLesionsComponent } from './list/origine-lesions.component';
import { OrigineLesionsDetailComponent } from './detail/origine-lesions-detail.component';
import { OrigineLesionsUpdateComponent } from './update/origine-lesions-update.component';
import { OrigineLesionsDeleteDialogComponent } from './delete/origine-lesions-delete-dialog.component';
import { OrigineLesionsRoutingModule } from './route/origine-lesions-routing.module';

@NgModule({
  imports: [SharedModule, OrigineLesionsRoutingModule],
  declarations: [
    OrigineLesionsComponent,
    OrigineLesionsDetailComponent,
    OrigineLesionsUpdateComponent,
    OrigineLesionsDeleteDialogComponent,
  ],
  entryComponents: [OrigineLesionsDeleteDialogComponent],
})
export class OrigineLesionsModule {}
