import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypeActionComponent } from '../list/type-action.component';
import { TypeActionDetailComponent } from '../detail/type-action-detail.component';
import { TypeActionUpdateComponent } from '../update/type-action-update.component';
import { TypeActionRoutingResolveService } from './type-action-routing-resolve.service';

const typeActionRoute: Routes = [
  {
    path: '',
    component: TypeActionComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeActionDetailComponent,
    resolve: {
      typeAction: TypeActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeActionUpdateComponent,
    resolve: {
      typeAction: TypeActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeActionUpdateComponent,
    resolve: {
      typeAction: TypeActionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typeActionRoute)],
  exports: [RouterModule],
})
export class TypeActionRoutingModule {}
