import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPieceJointes, PieceJointes } from '../piece-jointes.model';
import { PieceJointesService } from '../service/piece-jointes.service';

@Injectable({ providedIn: 'root' })
export class PieceJointesRoutingResolveService implements Resolve<IPieceJointes> {
  constructor(protected service: PieceJointesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPieceJointes> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((pieceJointes: HttpResponse<PieceJointes>) => {
          if (pieceJointes.body) {
            return of(pieceJointes.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PieceJointes());
  }
}
