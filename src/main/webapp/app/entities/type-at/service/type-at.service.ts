import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypeAt, getTypeAtIdentifier } from '../type-at.model';

export type EntityResponseType = HttpResponse<ITypeAt>;
export type EntityArrayResponseType = HttpResponse<ITypeAt[]>;

@Injectable({ providedIn: 'root' })
export class TypeAtService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/type-ats');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typeAt: ITypeAt): Observable<EntityResponseType> {
    return this.http.post<ITypeAt>(this.resourceUrl, typeAt, { observe: 'response' });
  }

  update(typeAt: ITypeAt): Observable<EntityResponseType> {
    return this.http.put<ITypeAt>(`${this.resourceUrl}/${getTypeAtIdentifier(typeAt) as number}`, typeAt, { observe: 'response' });
  }

  partialUpdate(typeAt: ITypeAt): Observable<EntityResponseType> {
    return this.http.patch<ITypeAt>(`${this.resourceUrl}/${getTypeAtIdentifier(typeAt) as number}`, typeAt, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeAt>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeAt[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTypeAtToCollectionIfMissing(typeAtCollection: ITypeAt[], ...typeAtsToCheck: (ITypeAt | null | undefined)[]): ITypeAt[] {
    const typeAts: ITypeAt[] = typeAtsToCheck.filter(isPresent);
    if (typeAts.length > 0) {
      const typeAtCollectionIdentifiers = typeAtCollection.map(typeAtItem => getTypeAtIdentifier(typeAtItem)!);
      const typeAtsToAdd = typeAts.filter(typeAtItem => {
        const typeAtIdentifier = getTypeAtIdentifier(typeAtItem);
        if (typeAtIdentifier == null || typeAtCollectionIdentifiers.includes(typeAtIdentifier)) {
          return false;
        }
        typeAtCollectionIdentifiers.push(typeAtIdentifier);
        return true;
      });
      return [...typeAtsToAdd, ...typeAtCollection];
    }
    return typeAtCollection;
  }
}
