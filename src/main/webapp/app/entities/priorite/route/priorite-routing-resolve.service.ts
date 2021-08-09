import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPriorite, Priorite } from '../priorite.model';
import { PrioriteService } from '../service/priorite.service';

@Injectable({ providedIn: 'root' })
export class PrioriteRoutingResolveService implements Resolve<IPriorite> {
  constructor(protected service: PrioriteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPriorite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((priorite: HttpResponse<Priorite>) => {
          if (priorite.body) {
            return of(priorite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Priorite());
  }
}
