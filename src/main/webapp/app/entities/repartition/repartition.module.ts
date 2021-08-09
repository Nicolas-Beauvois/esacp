import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RepartitionComponent } from './list/repartition.component';
import { RepartitionDetailComponent } from './detail/repartition-detail.component';
import { RepartitionUpdateComponent } from './update/repartition-update.component';
import { RepartitionDeleteDialogComponent } from './delete/repartition-delete-dialog.component';
import { RepartitionRoutingModule } from './route/repartition-routing.module';

@NgModule({
  imports: [SharedModule, RepartitionRoutingModule],
  declarations: [RepartitionComponent, RepartitionDetailComponent, RepartitionUpdateComponent, RepartitionDeleteDialogComponent],
  entryComponents: [RepartitionDeleteDialogComponent],
})
export class RepartitionModule {}
