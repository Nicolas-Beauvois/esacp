import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FicheSuiviSanteComponent } from '../list/fiche-suivi-sante.component';
import { FicheSuiviSanteDetailComponent } from '../detail/fiche-suivi-sante-detail.component';
import { FicheSuiviSanteUpdateComponent } from '../update/fiche-suivi-sante-update.component';
import { FicheSuiviSanteRoutingResolveService } from './fiche-suivi-sante-routing-resolve.service';

const ficheSuiviSanteRoute: Routes = [
  {
    path: '',
    component: FicheSuiviSanteComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FicheSuiviSanteDetailComponent,
    resolve: {
      ficheSuiviSante: FicheSuiviSanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FicheSuiviSanteUpdateComponent,
    resolve: {
      ficheSuiviSante: FicheSuiviSanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FicheSuiviSanteUpdateComponent,
    resolve: {
      ficheSuiviSante: FicheSuiviSanteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ficheSuiviSanteRoute)],
  exports: [RouterModule],
})
export class FicheSuiviSanteRoutingModule {}
