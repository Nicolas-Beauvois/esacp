import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypeRapport, getTypeRapportIdentifier } from '../type-rapport.model';

export type EntityResponseType = HttpResponse<ITypeRapport>;
export type EntityArrayResponseType = HttpResponse<ITypeRapport[]>;

@Injectable({ providedIn: 'root' })
export class TypeRapportService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/type-rapports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typeRapport: ITypeRapport): Observable<EntityResponseType> {
    return this.http.post<ITypeRapport>(this.resourceUrl, typeRapport, { observe: 'response' });
  }

  update(typeRapport: ITypeRapport): Observable<EntityResponseType> {
    return this.http.put<ITypeRapport>(`${this.resourceUrl}/${getTypeRapportIdentifier(typeRapport) as number}`, typeRapport, {
      observe: 'response',
    });
  }

  partialUpdate(typeRapport: ITypeRapport): Observable<EntityResponseType> {
    return this.http.patch<ITypeRapport>(`${this.resourceUrl}/${getTypeRapportIdentifier(typeRapport) as number}`, typeRapport, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeRapport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeRapport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTypeRapportToCollectionIfMissing(
    typeRapportCollection: ITypeRapport[],
    ...typeRapportsToCheck: (ITypeRapport | null | undefined)[]
  ): ITypeRapport[] {
    const typeRapports: ITypeRapport[] = typeRapportsToCheck.filter(isPresent);
    if (typeRapports.length > 0) {
      const typeRapportCollectionIdentifiers = typeRapportCollection.map(typeRapportItem => getTypeRapportIdentifier(typeRapportItem)!);
      const typeRapportsToAdd = typeRapports.filter(typeRapportItem => {
        const typeRapportIdentifier = getTypeRapportIdentifier(typeRapportItem);
        if (typeRapportIdentifier == null || typeRapportCollectionIdentifiers.includes(typeRapportIdentifier)) {
          return false;
        }
        typeRapportCollectionIdentifiers.push(typeRapportIdentifier);
        return true;
      });
      return [...typeRapportsToAdd, ...typeRapportCollection];
    }
    return typeRapportCollection;
  }
}
