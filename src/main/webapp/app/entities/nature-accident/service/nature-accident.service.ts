import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INatureAccident, getNatureAccidentIdentifier } from '../nature-accident.model';

export type EntityResponseType = HttpResponse<INatureAccident>;
export type EntityArrayResponseType = HttpResponse<INatureAccident[]>;

@Injectable({ providedIn: 'root' })
export class NatureAccidentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/nature-accidents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(natureAccident: INatureAccident): Observable<EntityResponseType> {
    return this.http.post<INatureAccident>(this.resourceUrl, natureAccident, { observe: 'response' });
  }

  update(natureAccident: INatureAccident): Observable<EntityResponseType> {
    return this.http.put<INatureAccident>(`${this.resourceUrl}/${getNatureAccidentIdentifier(natureAccident) as number}`, natureAccident, {
      observe: 'response',
    });
  }

  partialUpdate(natureAccident: INatureAccident): Observable<EntityResponseType> {
    return this.http.patch<INatureAccident>(
      `${this.resourceUrl}/${getNatureAccidentIdentifier(natureAccident) as number}`,
      natureAccident,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INatureAccident>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INatureAccident[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNatureAccidentToCollectionIfMissing(
    natureAccidentCollection: INatureAccident[],
    ...natureAccidentsToCheck: (INatureAccident | null | undefined)[]
  ): INatureAccident[] {
    const natureAccidents: INatureAccident[] = natureAccidentsToCheck.filter(isPresent);
    if (natureAccidents.length > 0) {
      const natureAccidentCollectionIdentifiers = natureAccidentCollection.map(
        natureAccidentItem => getNatureAccidentIdentifier(natureAccidentItem)!
      );
      const natureAccidentsToAdd = natureAccidents.filter(natureAccidentItem => {
        const natureAccidentIdentifier = getNatureAccidentIdentifier(natureAccidentItem);
        if (natureAccidentIdentifier == null || natureAccidentCollectionIdentifiers.includes(natureAccidentIdentifier)) {
          return false;
        }
        natureAccidentCollectionIdentifiers.push(natureAccidentIdentifier);
        return true;
      });
      return [...natureAccidentsToAdd, ...natureAccidentCollection];
    }
    return natureAccidentCollection;
  }
}
