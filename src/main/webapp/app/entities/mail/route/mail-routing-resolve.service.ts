import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMail, Mail } from '../mail.model';
import { MailService } from '../service/mail.service';

@Injectable({ providedIn: 'root' })
export class MailRoutingResolveService implements Resolve<IMail> {
  constructor(protected service: MailService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMail> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((mail: HttpResponse<Mail>) => {
          if (mail.body) {
            return of(mail.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mail());
  }
}
