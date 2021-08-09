import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypeAt, TypeAt } from '../type-at.model';
import { TypeAtService } from '../service/type-at.service';

@Injectable({ providedIn: 'root' })
export class TypeAtRoutingResolveService implements Resolve<ITypeAt> {
  constructor(protected service: TypeAtService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeAt> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typeAt: HttpResponse<TypeAt>) => {
          if (typeAt.body) {
            return of(typeAt.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeAt());
  }
}
