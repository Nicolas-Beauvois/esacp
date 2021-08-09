import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ListingMailComponent } from './list/listing-mail.component';
import { ListingMailDetailComponent } from './detail/listing-mail-detail.component';
import { ListingMailUpdateComponent } from './update/listing-mail-update.component';
import { ListingMailDeleteDialogComponent } from './delete/listing-mail-delete-dialog.component';
import { ListingMailRoutingModule } from './route/listing-mail-routing.module';

@NgModule({
  imports: [SharedModule, ListingMailRoutingModule],
  declarations: [ListingMailComponent, ListingMailDetailComponent, ListingMailUpdateComponent, ListingMailDeleteDialogComponent],
  entryComponents: [ListingMailDeleteDialogComponent],
})
export class ListingMailModule {}
