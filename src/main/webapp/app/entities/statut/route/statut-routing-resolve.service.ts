import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStatut, Statut } from '../statut.model';
import { StatutService } from '../service/statut.service';

@Injectable({ providedIn: 'root' })
export class StatutRoutingResolveService implements Resolve<IStatut> {
  constructor(protected service: StatutService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatut> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((statut: HttpResponse<Statut>) => {
          if (statut.body) {
            return of(statut.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Statut());
  }
}
