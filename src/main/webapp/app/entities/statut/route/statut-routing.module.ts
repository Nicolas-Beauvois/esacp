import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StatutComponent } from '../list/statut.component';
import { StatutDetailComponent } from '../detail/statut-detail.component';
import { StatutUpdateComponent } from '../update/statut-update.component';
import { StatutRoutingResolveService } from './statut-routing-resolve.service';

const statutRoute: Routes = [
  {
    path: '',
    component: StatutComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StatutDetailComponent,
    resolve: {
      statut: StatutRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StatutUpdateComponent,
    resolve: {
      statut: StatutRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StatutUpdateComponent,
    resolve: {
      statut: StatutRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(statutRoute)],
  exports: [RouterModule],
})
export class StatutRoutingModule {}
