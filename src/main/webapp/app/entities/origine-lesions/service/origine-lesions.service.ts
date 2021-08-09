import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrigineLesions, getOrigineLesionsIdentifier } from '../origine-lesions.model';

export type EntityResponseType = HttpResponse<IOrigineLesions>;
export type EntityArrayResponseType = HttpResponse<IOrigineLesions[]>;

@Injectable({ providedIn: 'root' })
export class OrigineLesionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/origine-lesions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(origineLesions: IOrigineLesions): Observable<EntityResponseType> {
    return this.http.post<IOrigineLesions>(this.resourceUrl, origineLesions, { observe: 'response' });
  }

  update(origineLesions: IOrigineLesions): Observable<EntityResponseType> {
    return this.http.put<IOrigineLesions>(`${this.resourceUrl}/${getOrigineLesionsIdentifier(origineLesions) as number}`, origineLesions, {
      observe: 'response',
    });
  }

  partialUpdate(origineLesions: IOrigineLesions): Observable<EntityResponseType> {
    return this.http.patch<IOrigineLesions>(
      `${this.resourceUrl}/${getOrigineLesionsIdentifier(origineLesions) as number}`,
      origineLesions,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrigineLesions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrigineLesions[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrigineLesionsToCollectionIfMissing(
    origineLesionsCollection: IOrigineLesions[],
    ...origineLesionsToCheck: (IOrigineLesions | null | undefined)[]
  ): IOrigineLesions[] {
    const origineLesions: IOrigineLesions[] = origineLesionsToCheck.filter(isPresent);
    if (origineLesions.length > 0) {
      const origineLesionsCollectionIdentifiers = origineLesionsCollection.map(
        origineLesionsItem => getOrigineLesionsIdentifier(origineLesionsItem)!
      );
      const origineLesionsToAdd = origineLesions.filter(origineLesionsItem => {
        const origineLesionsIdentifier = getOrigineLesionsIdentifier(origineLesionsItem);
        if (origineLesionsIdentifier == null || origineLesionsCollectionIdentifiers.includes(origineLesionsIdentifier)) {
          return false;
        }
        origineLesionsCollectionIdentifiers.push(origineLesionsIdentifier);
        return true;
      });
      return [...origineLesionsToAdd, ...origineLesionsCollection];
    }
    return origineLesionsCollection;
  }
}
