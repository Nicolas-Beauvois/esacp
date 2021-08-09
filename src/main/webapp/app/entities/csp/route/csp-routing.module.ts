import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CspComponent } from '../list/csp.component';
import { CspDetailComponent } from '../detail/csp-detail.component';
import { CspUpdateComponent } from '../update/csp-update.component';
import { CspRoutingResolveService } from './csp-routing-resolve.service';

const cspRoute: Routes = [
  {
    path: '',
    component: CspComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CspDetailComponent,
    resolve: {
      csp: CspRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CspUpdateComponent,
    resolve: {
      csp: CspRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CspUpdateComponent,
    resolve: {
      csp: CspRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cspRoute)],
  exports: [RouterModule],
})
export class CspRoutingModule {}
