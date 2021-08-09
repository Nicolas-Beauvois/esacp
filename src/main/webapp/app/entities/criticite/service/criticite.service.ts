import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICriticite, getCriticiteIdentifier } from '../criticite.model';

export type EntityResponseType = HttpResponse<ICriticite>;
export type EntityArrayResponseType = HttpResponse<ICriticite[]>;

@Injectable({ providedIn: 'root' })
export class CriticiteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/criticites');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(criticite: ICriticite): Observable<EntityResponseType> {
    return this.http.post<ICriticite>(this.resourceUrl, criticite, { observe: 'response' });
  }

  update(criticite: ICriticite): Observable<EntityResponseType> {
    return this.http.put<ICriticite>(`${this.resourceUrl}/${getCriticiteIdentifier(criticite) as number}`, criticite, {
      observe: 'response',
    });
  }

  partialUpdate(criticite: ICriticite): Observable<EntityResponseType> {
    return this.http.patch<ICriticite>(`${this.resourceUrl}/${getCriticiteIdentifier(criticite) as number}`, criticite, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICriticite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICriticite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCriticiteToCollectionIfMissing(
    criticiteCollection: ICriticite[],
    ...criticitesToCheck: (ICriticite | null | undefined)[]
  ): ICriticite[] {
    const criticites: ICriticite[] = criticitesToCheck.filter(isPresent);
    if (criticites.length > 0) {
      const criticiteCollectionIdentifiers = criticiteCollection.map(criticiteItem => getCriticiteIdentifier(criticiteItem)!);
      const criticitesToAdd = criticites.filter(criticiteItem => {
        const criticiteIdentifier = getCriticiteIdentifier(criticiteItem);
        if (criticiteIdentifier == null || criticiteCollectionIdentifiers.includes(criticiteIdentifier)) {
          return false;
        }
        criticiteCollectionIdentifiers.push(criticiteIdentifier);
        return true;
      });
      return [...criticitesToAdd, ...criticiteCollection];
    }
    return criticiteCollection;
  }
}
