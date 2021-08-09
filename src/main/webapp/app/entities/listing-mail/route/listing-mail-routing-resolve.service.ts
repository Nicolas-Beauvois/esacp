import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IListingMail, ListingMail } from '../listing-mail.model';
import { ListingMailService } from '../service/listing-mail.service';

@Injectable({ providedIn: 'root' })
export class ListingMailRoutingResolveService implements Resolve<IListingMail> {
  constructor(protected service: ListingMailService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IListingMail> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((listingMail: HttpResponse<ListingMail>) => {
          if (listingMail.body) {
            return of(listingMail.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ListingMail());
  }
}
