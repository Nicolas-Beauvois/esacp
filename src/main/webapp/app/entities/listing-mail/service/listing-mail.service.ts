import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IListingMail, getListingMailIdentifier } from '../listing-mail.model';

export type EntityResponseType = HttpResponse<IListingMail>;
export type EntityArrayResponseType = HttpResponse<IListingMail[]>;

@Injectable({ providedIn: 'root' })
export class ListingMailService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/listing-mails');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(listingMail: IListingMail): Observable<EntityResponseType> {
    return this.http.post<IListingMail>(this.resourceUrl, listingMail, { observe: 'response' });
  }

  update(listingMail: IListingMail): Observable<EntityResponseType> {
    return this.http.put<IListingMail>(`${this.resourceUrl}/${getListingMailIdentifier(listingMail) as number}`, listingMail, {
      observe: 'response',
    });
  }

  partialUpdate(listingMail: IListingMail): Observable<EntityResponseType> {
    return this.http.patch<IListingMail>(`${this.resourceUrl}/${getListingMailIdentifier(listingMail) as number}`, listingMail, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IListingMail>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IListingMail[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addListingMailToCollectionIfMissing(
    listingMailCollection: IListingMail[],
    ...listingMailsToCheck: (IListingMail | null | undefined)[]
  ): IListingMail[] {
    const listingMails: IListingMail[] = listingMailsToCheck.filter(isPresent);
    if (listingMails.length > 0) {
      const listingMailCollectionIdentifiers = listingMailCollection.map(listingMailItem => getListingMailIdentifier(listingMailItem)!);
      const listingMailsToAdd = listingMails.filter(listingMailItem => {
        const listingMailIdentifier = getListingMailIdentifier(listingMailItem);
        if (listingMailIdentifier == null || listingMailCollectionIdentifiers.includes(listingMailIdentifier)) {
          return false;
        }
        listingMailCollectionIdentifiers.push(listingMailIdentifier);
        return true;
      });
      return [...listingMailsToAdd, ...listingMailCollection];
    }
    return listingMailCollection;
  }
}
