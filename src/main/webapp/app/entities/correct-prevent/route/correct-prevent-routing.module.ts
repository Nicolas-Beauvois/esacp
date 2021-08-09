import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CorrectPreventComponent } from '../list/correct-prevent.component';
import { CorrectPreventDetailComponent } from '../detail/correct-prevent-detail.component';
import { CorrectPreventUpdateComponent } from '../update/correct-prevent-update.component';
import { CorrectPreventRoutingResolveService } from './correct-prevent-routing-resolve.service';

const correctPreventRoute: Routes = [
  {
    path: '',
    component: CorrectPreventComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CorrectPreventDetailComponent,
    resolve: {
      correctPrevent: CorrectPreventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CorrectPreventUpdateComponent,
    resolve: {
      correctPrevent: CorrectPreventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CorrectPreventUpdateComponent,
    resolve: {
      correctPrevent: CorrectPreventRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(correctPreventRoute)],
  exports: [RouterModule],
})
export class CorrectPreventRoutingModule {}
