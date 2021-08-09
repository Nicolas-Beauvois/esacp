import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SiegeLesionsComponent } from '../list/siege-lesions.component';
import { SiegeLesionsDetailComponent } from '../detail/siege-lesions-detail.component';
import { SiegeLesionsUpdateComponent } from '../update/siege-lesions-update.component';
import { SiegeLesionsRoutingResolveService } from './siege-lesions-routing-resolve.service';

const siegeLesionsRoute: Routes = [
  {
    path: '',
    component: SiegeLesionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SiegeLesionsDetailComponent,
    resolve: {
      siegeLesions: SiegeLesionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SiegeLesionsUpdateComponent,
    resolve: {
      siegeLesions: SiegeLesionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SiegeLesionsUpdateComponent,
    resolve: {
      siegeLesions: SiegeLesionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(siegeLesionsRoute)],
  exports: [RouterModule],
})
export class SiegeLesionsRoutingModule {}
