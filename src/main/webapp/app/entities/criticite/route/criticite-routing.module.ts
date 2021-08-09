import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CriticiteComponent } from '../list/criticite.component';
import { CriticiteDetailComponent } from '../detail/criticite-detail.component';
import { CriticiteUpdateComponent } from '../update/criticite-update.component';
import { CriticiteRoutingResolveService } from './criticite-routing-resolve.service';

const criticiteRoute: Routes = [
  {
    path: '',
    component: CriticiteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CriticiteDetailComponent,
    resolve: {
      criticite: CriticiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CriticiteUpdateComponent,
    resolve: {
      criticite: CriticiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CriticiteUpdateComponent,
    resolve: {
      criticite: CriticiteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(criticiteRoute)],
  exports: [RouterModule],
})
export class CriticiteRoutingModule {}
