import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RepartitionComponent } from '../list/repartition.component';
import { RepartitionDetailComponent } from '../detail/repartition-detail.component';
import { RepartitionUpdateComponent } from '../update/repartition-update.component';
import { RepartitionRoutingResolveService } from './repartition-routing-resolve.service';

const repartitionRoute: Routes = [
  {
    path: '',
    component: RepartitionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RepartitionDetailComponent,
    resolve: {
      repartition: RepartitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RepartitionUpdateComponent,
    resolve: {
      repartition: RepartitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RepartitionUpdateComponent,
    resolve: {
      repartition: RepartitionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(repartitionRoute)],
  exports: [RouterModule],
})
export class RepartitionRoutingModule {}
