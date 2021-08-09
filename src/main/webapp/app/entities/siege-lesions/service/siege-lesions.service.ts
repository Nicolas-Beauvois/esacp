import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISiegeLesions, getSiegeLesionsIdentifier } from '../siege-lesions.model';

export type EntityResponseType = HttpResponse<ISiegeLesions>;
export type EntityArrayResponseType = HttpResponse<ISiegeLesions[]>;

@Injectable({ providedIn: 'root' })
export class SiegeLesionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/siege-lesions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(siegeLesions: ISiegeLesions): Observable<EntityResponseType> {
    return this.http.post<ISiegeLesions>(this.resourceUrl, siegeLesions, { observe: 'response' });
  }

  update(siegeLesions: ISiegeLesions): Observable<EntityResponseType> {
    return this.http.put<ISiegeLesions>(`${this.resourceUrl}/${getSiegeLesionsIdentifier(siegeLesions) as number}`, siegeLesions, {
      observe: 'response',
    });
  }

  partialUpdate(siegeLesions: ISiegeLesions): Observable<EntityResponseType> {
    return this.http.patch<ISiegeLesions>(`${this.resourceUrl}/${getSiegeLesionsIdentifier(siegeLesions) as number}`, siegeLesions, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISiegeLesions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISiegeLesions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSiegeLesionsToCollectionIfMissing(
    siegeLesionsCollection: ISiegeLesions[],
    ...siegeLesionsToCheck: (ISiegeLesions | null | undefined)[]
  ): ISiegeLesions[] {
    const siegeLesions: ISiegeLesions[] = siegeLesionsToCheck.filter(isPresent);
    if (siegeLesions.length > 0) {
      const siegeLesionsCollectionIdentifiers = siegeLesionsCollection.map(
        siegeLesionsItem => getSiegeLesionsIdentifier(siegeLesionsItem)!
      );
      const siegeLesionsToAdd = siegeLesions.filter(siegeLesionsItem => {
        const siegeLesionsIdentifier = getSiegeLesionsIdentifier(siegeLesionsItem);
        if (siegeLesionsIdentifier == null || siegeLesionsCollectionIdentifiers.includes(siegeLesionsIdentifier)) {
          return false;
        }
        siegeLesionsCollectionIdentifiers.push(siegeLesionsIdentifier);
        return true;
      });
      return [...siegeLesionsToAdd, ...siegeLesionsCollection];
    }
    return siegeLesionsCollection;
  }
}
