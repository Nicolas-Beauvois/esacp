import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrigineLesionsComponent } from '../list/origine-lesions.component';
import { OrigineLesionsDetailComponent } from '../detail/origine-lesions-detail.component';
import { OrigineLesionsUpdateComponent } from '../update/origine-lesions-update.component';
import { OrigineLesionsRoutingResolveService } from './origine-lesions-routing-resolve.service';

const origineLesionsRoute: Routes = [
  {
    path: '',
    component: OrigineLesionsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrigineLesionsDetailComponent,
    resolve: {
      origineLesions: OrigineLesionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrigineLesionsUpdateComponent,
    resolve: {
      origineLesions: OrigineLesionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrigineLesionsUpdateComponent,
    resolve: {
      origineLesions: OrigineLesionsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(origineLesionsRoute)],
  exports: [RouterModule],
})
export class OrigineLesionsRoutingModule {}
