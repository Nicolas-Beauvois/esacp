import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStatut, getStatutIdentifier } from '../statut.model';

export type EntityResponseType = HttpResponse<IStatut>;
export type EntityArrayResponseType = HttpResponse<IStatut[]>;

@Injectable({ providedIn: 'root' })
export class StatutService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/statuts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(statut: IStatut): Observable<EntityResponseType> {
    return this.http.post<IStatut>(this.resourceUrl, statut, { observe: 'response' });
  }

  update(statut: IStatut): Observable<EntityResponseType> {
    return this.http.put<IStatut>(`${this.resourceUrl}/${getStatutIdentifier(statut) as number}`, statut, { observe: 'response' });
  }

  partialUpdate(statut: IStatut): Observable<EntityResponseType> {
    return this.http.patch<IStatut>(`${this.resourceUrl}/${getStatutIdentifier(statut) as number}`, statut, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStatut>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStatut[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStatutToCollectionIfMissing(statutCollection: IStatut[], ...statutsToCheck: (IStatut | null | undefined)[]): IStatut[] {
    const statuts: IStatut[] = statutsToCheck.filter(isPresent);
    if (statuts.length > 0) {
      const statutCollectionIdentifiers = statutCollection.map(statutItem => getStatutIdentifier(statutItem)!);
      const statutsToAdd = statuts.filter(statutItem => {
        const statutIdentifier = getStatutIdentifier(statutItem);
        if (statutIdentifier == null || statutCollectionIdentifiers.includes(statutIdentifier)) {
          return false;
        }
        statutCollectionIdentifiers.push(statutIdentifier);
        return true;
      });
      return [...statutsToAdd, ...statutCollection];
    }
    return statutCollection;
  }
}
