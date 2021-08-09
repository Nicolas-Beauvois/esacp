import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRepartition, Repartition } from '../repartition.model';
import { RepartitionService } from '../service/repartition.service';

@Injectable({ providedIn: 'root' })
export class RepartitionRoutingResolveService implements Resolve<IRepartition> {
  constructor(protected service: RepartitionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRepartition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((repartition: HttpResponse<Repartition>) => {
          if (repartition.body) {
            return of(repartition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Repartition());
  }
}
