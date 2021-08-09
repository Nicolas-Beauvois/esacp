import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MailComponent } from './list/mail.component';
import { MailDetailComponent } from './detail/mail-detail.component';
import { MailUpdateComponent } from './update/mail-update.component';
import { MailDeleteDialogComponent } from './delete/mail-delete-dialog.component';
import { MailRoutingModule } from './route/mail-routing.module';

@NgModule({
  imports: [SharedModule, MailRoutingModule],
  declarations: [MailComponent, MailDetailComponent, MailUpdateComponent, MailDeleteDialogComponent],
  entryComponents: [MailDeleteDialogComponent],
})
export class MailModule {}
