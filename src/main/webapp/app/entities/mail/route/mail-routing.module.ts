import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MailComponent } from '../list/mail.component';
import { MailDetailComponent } from '../detail/mail-detail.component';
import { MailUpdateComponent } from '../update/mail-update.component';
import { MailRoutingResolveService } from './mail-routing-resolve.service';

const mailRoute: Routes = [
  {
    path: '',
    component: MailComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MailDetailComponent,
    resolve: {
      mail: MailRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MailUpdateComponent,
    resolve: {
      mail: MailRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MailUpdateComponent,
    resolve: {
      mail: MailRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(mailRoute)],
  exports: [RouterModule],
})
export class MailRoutingModule {}
