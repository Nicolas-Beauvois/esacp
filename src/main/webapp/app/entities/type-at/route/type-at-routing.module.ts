import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypeAtComponent } from '../list/type-at.component';
import { TypeAtDetailComponent } from '../detail/type-at-detail.component';
import { TypeAtUpdateComponent } from '../update/type-at-update.component';
import { TypeAtRoutingResolveService } from './type-at-routing-resolve.service';

const typeAtRoute: Routes = [
  {
    path: '',
    component: TypeAtComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeAtDetailComponent,
    resolve: {
      typeAt: TypeAtRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeAtUpdateComponent,
    resolve: {
      typeAt: TypeAtRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeAtUpdateComponent,
    resolve: {
      typeAt: TypeAtRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typeAtRoute)],
  exports: [RouterModule],
})
export class TypeAtRoutingModule {}
