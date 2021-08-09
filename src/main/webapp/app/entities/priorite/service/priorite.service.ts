import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPriorite, getPrioriteIdentifier } from '../priorite.model';

export type EntityResponseType = HttpResponse<IPriorite>;
export type EntityArrayResponseType = HttpResponse<IPriorite[]>;

@Injectable({ providedIn: 'root' })
export class PrioriteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/priorites');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(priorite: IPriorite): Observable<EntityResponseType> {
    return this.http.post<IPriorite>(this.resourceUrl, priorite, { observe: 'response' });
  }

  update(priorite: IPriorite): Observable<EntityResponseType> {
    return this.http.put<IPriorite>(`${this.resourceUrl}/${getPrioriteIdentifier(priorite) as number}`, priorite, { observe: 'response' });
  }

  partialUpdate(priorite: IPriorite): Observable<EntityResponseType> {
    return this.http.patch<IPriorite>(`${this.resourceUrl}/${getPrioriteIdentifier(priorite) as number}`, priorite, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPriorite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPriorite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPrioriteToCollectionIfMissing(prioriteCollection: IPriorite[], ...prioritesToCheck: (IPriorite | null | undefined)[]): IPriorite[] {
    const priorites: IPriorite[] = prioritesToCheck.filter(isPresent);
    if (priorites.length > 0) {
      const prioriteCollectionIdentifiers = prioriteCollection.map(prioriteItem => getPrioriteIdentifier(prioriteItem)!);
      const prioritesToAdd = priorites.filter(prioriteItem => {
        const prioriteIdentifier = getPrioriteIdentifier(prioriteItem);
        if (prioriteIdentifier == null || prioriteCollectionIdentifiers.includes(prioriteIdentifier)) {
          return false;
        }
        prioriteCollectionIdentifiers.push(prioriteIdentifier);
        return true;
      });
      return [...prioritesToAdd, ...prioriteCollection];
    }
    return prioriteCollection;
  }
}
