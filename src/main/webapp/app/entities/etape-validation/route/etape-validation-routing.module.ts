import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EtapeValidationComponent } from '../list/etape-validation.component';
import { EtapeValidationDetailComponent } from '../detail/etape-validation-detail.component';
import { EtapeValidationUpdateComponent } from '../update/etape-validation-update.component';
import { EtapeValidationRoutingResolveService } from './etape-validation-routing-resolve.service';

const etapeValidationRoute: Routes = [
  {
    path: '',
    component: EtapeValidationComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtapeValidationDetailComponent,
    resolve: {
      etapeValidation: EtapeValidationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtapeValidationUpdateComponent,
    resolve: {
      etapeValidation: EtapeValidationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtapeValidationUpdateComponent,
    resolve: {
      etapeValidation: EtapeValidationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(etapeValidationRoute)],
  exports: [RouterModule],
})
export class EtapeValidationRoutingModule {}
