import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICriticite, Criticite } from '../criticite.model';
import { CriticiteService } from '../service/criticite.service';

@Injectable({ providedIn: 'root' })
export class CriticiteRoutingResolveService implements Resolve<ICriticite> {
  constructor(protected service: CriticiteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICriticite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((criticite: HttpResponse<Criticite>) => {
          if (criticite.body) {
            return of(criticite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Criticite());
  }
}
