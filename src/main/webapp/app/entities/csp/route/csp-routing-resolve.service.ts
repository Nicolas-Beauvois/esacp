import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICsp, Csp } from '../csp.model';
import { CspService } from '../service/csp.service';

@Injectable({ providedIn: 'root' })
export class CspRoutingResolveService implements Resolve<ICsp> {
  constructor(protected service: CspService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICsp> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((csp: HttpResponse<Csp>) => {
          if (csp.body) {
            return of(csp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Csp());
  }
}
