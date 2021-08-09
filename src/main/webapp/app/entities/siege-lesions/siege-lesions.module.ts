import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SiegeLesionsComponent } from './list/siege-lesions.component';
import { SiegeLesionsDetailComponent } from './detail/siege-lesions-detail.component';
import { SiegeLesionsUpdateComponent } from './update/siege-lesions-update.component';
import { SiegeLesionsDeleteDialogComponent } from './delete/siege-lesions-delete-dialog.component';
import { SiegeLesionsRoutingModule } from './route/siege-lesions-routing.module';

@NgModule({
  imports: [SharedModule, SiegeLesionsRoutingModule],
  declarations: [SiegeLesionsComponent, SiegeLesionsDetailComponent, SiegeLesionsUpdateComponent, SiegeLesionsDeleteDialogComponent],
  entryComponents: [SiegeLesionsDeleteDialogComponent],
})
export class SiegeLesionsModule {}
