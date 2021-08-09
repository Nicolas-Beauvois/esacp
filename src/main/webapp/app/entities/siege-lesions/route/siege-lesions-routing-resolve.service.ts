import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISiegeLesions, SiegeLesions } from '../siege-lesions.model';
import { SiegeLesionsService } from '../service/siege-lesions.service';

@Injectable({ providedIn: 'root' })
export class SiegeLesionsRoutingResolveService implements Resolve<ISiegeLesions> {
  constructor(protected service: SiegeLesionsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISiegeLesions> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((siegeLesions: HttpResponse<SiegeLesions>) => {
          if (siegeLesions.body) {
            return of(siegeLesions.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SiegeLesions());
  }
}
