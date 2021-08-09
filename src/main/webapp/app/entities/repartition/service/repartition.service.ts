import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRepartition, getRepartitionIdentifier } from '../repartition.model';

export type EntityResponseType = HttpResponse<IRepartition>;
export type EntityArrayResponseType = HttpResponse<IRepartition[]>;

@Injectable({ providedIn: 'root' })
export class RepartitionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/repartitions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(repartition: IRepartition): Observable<EntityResponseType> {
    return this.http.post<IRepartition>(this.resourceUrl, repartition, { observe: 'response' });
  }

  update(repartition: IRepartition): Observable<EntityResponseType> {
    return this.http.put<IRepartition>(`${this.resourceUrl}/${getRepartitionIdentifier(repartition) as number}`, repartition, {
      observe: 'response',
    });
  }

  partialUpdate(repartition: IRepartition): Observable<EntityResponseType> {
    return this.http.patch<IRepartition>(`${this.resourceUrl}/${getRepartitionIdentifier(repartition) as number}`, repartition, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRepartition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRepartition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRepartitionToCollectionIfMissing(
    repartitionCollection: IRepartition[],
    ...repartitionsToCheck: (IRepartition | null | undefined)[]
  ): IRepartition[] {
    const repartitions: IRepartition[] = repartitionsToCheck.filter(isPresent);
    if (repartitions.length > 0) {
      const repartitionCollectionIdentifiers = repartitionCollection.map(repartitionItem => getRepartitionIdentifier(repartitionItem)!);
      const repartitionsToAdd = repartitions.filter(repartitionItem => {
        const repartitionIdentifier = getRepartitionIdentifier(repartitionItem);
        if (repartitionIdentifier == null || repartitionCollectionIdentifiers.includes(repartitionIdentifier)) {
          return false;
        }
        repartitionCollectionIdentifiers.push(repartitionIdentifier);
        return true;
      });
      return [...repartitionsToAdd, ...repartitionCollection];
    }
    return repartitionCollection;
  }
}
