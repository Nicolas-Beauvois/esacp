import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrioriteComponent } from '../list/priorite.component';
import { PrioriteDetailComponent } from '../detail/priorite-detail.component';
import { PrioriteUpdateComponent } from '../update/priorite-update.component';
import { PrioriteRoutingResolveService } from './priorite-routing-resolve.service';

const prioriteRoute: Routes = [
  {
    path: '',
    component: PrioriteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrioriteDetailComponent,
    resolve: {
      priorite: PrioriteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrioriteUpdateComponent,
    resolve: {
      priorite: PrioriteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrioriteUpdateComponent,
    resolve: {
      priorite: PrioriteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(prioriteRoute)],
  exports: [RouterModule],
})
export class PrioriteRoutingModule {}
