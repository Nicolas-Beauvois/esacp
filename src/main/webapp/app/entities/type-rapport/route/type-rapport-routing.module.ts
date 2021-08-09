import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TypeRapportComponent } from '../list/type-rapport.component';
import { TypeRapportDetailComponent } from '../detail/type-rapport-detail.component';
import { TypeRapportUpdateComponent } from '../update/type-rapport-update.component';
import { TypeRapportRoutingResolveService } from './type-rapport-routing-resolve.service';

const typeRapportRoute: Routes = [
  {
    path: '',
    component: TypeRapportComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TypeRapportDetailComponent,
    resolve: {
      typeRapport: TypeRapportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TypeRapportUpdateComponent,
    resolve: {
      typeRapport: TypeRapportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TypeRapportUpdateComponent,
    resolve: {
      typeRapport: TypeRapportRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(typeRapportRoute)],
  exports: [RouterModule],
})
export class TypeRapportRoutingModule {}
