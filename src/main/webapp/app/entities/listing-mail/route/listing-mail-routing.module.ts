import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ListingMailComponent } from '../list/listing-mail.component';
import { ListingMailDetailComponent } from '../detail/listing-mail-detail.component';
import { ListingMailUpdateComponent } from '../update/listing-mail-update.component';
import { ListingMailRoutingResolveService } from './listing-mail-routing-resolve.service';

const listingMailRoute: Routes = [
  {
    path: '',
    component: ListingMailComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ListingMailDetailComponent,
    resolve: {
      listingMail: ListingMailRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ListingMailUpdateComponent,
    resolve: {
      listingMail: ListingMailRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ListingMailUpdateComponent,
    resolve: {
      listingMail: ListingMailRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(listingMailRoute)],
  exports: [RouterModule],
})
export class ListingMailRoutingModule {}
