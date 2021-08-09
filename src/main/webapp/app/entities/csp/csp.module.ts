import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CspComponent } from './list/csp.component';
import { CspDetailComponent } from './detail/csp-detail.component';
import { CspUpdateComponent } from './update/csp-update.component';
import { CspDeleteDialogComponent } from './delete/csp-delete-dialog.component';
import { CspRoutingModule } from './route/csp-routing.module';

@NgModule({
  imports: [SharedModule, CspRoutingModule],
  declarations: [CspComponent, CspDetailComponent, CspUpdateComponent, CspDeleteDialogComponent],
  entryComponents: [CspDeleteDialogComponent],
})
export class CspModule {}
