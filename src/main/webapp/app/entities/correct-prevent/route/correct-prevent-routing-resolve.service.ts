import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICorrectPrevent, CorrectPrevent } from '../correct-prevent.model';
import { CorrectPreventService } from '../service/correct-prevent.service';

@Injectable({ providedIn: 'root' })
export class CorrectPreventRoutingResolveService implements Resolve<ICorrectPrevent> {
  constructor(protected service: CorrectPreventService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICorrectPrevent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((correctPrevent: HttpResponse<CorrectPrevent>) => {
          if (correctPrevent.body) {
            return of(correctPrevent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CorrectPrevent());
  }
}
