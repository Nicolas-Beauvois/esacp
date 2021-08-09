import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureAccidentComponent } from '../list/nature-accident.component';
import { NatureAccidentDetailComponent } from '../detail/nature-accident-detail.component';
import { NatureAccidentUpdateComponent } from '../update/nature-accident-update.component';
import { NatureAccidentRoutingResolveService } from './nature-accident-routing-resolve.service';

const natureAccidentRoute: Routes = [
  {
    path: '',
    component: NatureAccidentComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureAccidentDetailComponent,
    resolve: {
      natureAccident: NatureAccidentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureAccidentUpdateComponent,
    resolve: {
      natureAccident: NatureAccidentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureAccidentUpdateComponent,
    resolve: {
      natureAccident: NatureAccidentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureAccidentRoute)],
  exports: [RouterModule],
})
export class NatureAccidentRoutingModule {}
