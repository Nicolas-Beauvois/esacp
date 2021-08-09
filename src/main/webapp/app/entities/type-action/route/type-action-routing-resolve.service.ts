import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypeAction, TypeAction } from '../type-action.model';
import { TypeActionService } from '../service/type-action.service';

@Injectable({ providedIn: 'root' })
export class TypeActionRoutingResolveService implements Resolve<ITypeAction> {
  constructor(protected service: TypeActionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeAction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typeAction: HttpResponse<TypeAction>) => {
          if (typeAction.body) {
            return of(typeAction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeAction());
  }
}
