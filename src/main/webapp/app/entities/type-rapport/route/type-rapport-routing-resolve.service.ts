import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITypeRapport, TypeRapport } from '../type-rapport.model';
import { TypeRapportService } from '../service/type-rapport.service';

@Injectable({ providedIn: 'root' })
export class TypeRapportRoutingResolveService implements Resolve<ITypeRapport> {
  constructor(protected service: TypeRapportService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypeRapport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((typeRapport: HttpResponse<TypeRapport>) => {
          if (typeRapport.body) {
            return of(typeRapport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypeRapport());
  }
}
