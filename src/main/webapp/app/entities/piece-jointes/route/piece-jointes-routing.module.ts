import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PieceJointesComponent } from '../list/piece-jointes.component';
import { PieceJointesDetailComponent } from '../detail/piece-jointes-detail.component';
import { PieceJointesUpdateComponent } from '../update/piece-jointes-update.component';
import { PieceJointesRoutingResolveService } from './piece-jointes-routing-resolve.service';

const pieceJointesRoute: Routes = [
  {
    path: '',
    component: PieceJointesComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PieceJointesDetailComponent,
    resolve: {
      pieceJointes: PieceJointesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PieceJointesUpdateComponent,
    resolve: {
      pieceJointes: PieceJointesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PieceJointesUpdateComponent,
    resolve: {
      pieceJointes: PieceJointesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(pieceJointesRoute)],
  exports: [RouterModule],
})
export class PieceJointesRoutingModule {}
