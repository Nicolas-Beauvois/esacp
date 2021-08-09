import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEtapeValidation, EtapeValidation } from '../etape-validation.model';
import { EtapeValidationService } from '../service/etape-validation.service';

@Injectable({ providedIn: 'root' })
export class EtapeValidationRoutingResolveService implements Resolve<IEtapeValidation> {
  constructor(protected service: EtapeValidationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtapeValidation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((etapeValidation: HttpResponse<EtapeValidation>) => {
          if (etapeValidation.body) {
            return of(etapeValidation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EtapeValidation());
  }
}
