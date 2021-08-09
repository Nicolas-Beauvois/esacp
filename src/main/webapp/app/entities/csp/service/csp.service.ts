import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICsp, getCspIdentifier } from '../csp.model';

export type EntityResponseType = HttpResponse<ICsp>;
export type EntityArrayResponseType = HttpResponse<ICsp[]>;

@Injectable({ providedIn: 'root' })
export class CspService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/csps');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(csp: ICsp): Observable<EntityResponseType> {
    return this.http.post<ICsp>(this.resourceUrl, csp, { observe: 'response' });
  }

  update(csp: ICsp): Observable<EntityResponseType> {
    return this.http.put<ICsp>(`${this.resourceUrl}/${getCspIdentifier(csp) as number}`, csp, { observe: 'response' });
  }

  partialUpdate(csp: ICsp): Observable<EntityResponseType> {
    return this.http.patch<ICsp>(`${this.resourceUrl}/${getCspIdentifier(csp) as number}`, csp, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICsp>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICsp[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCspToCollectionIfMissing(cspCollection: ICsp[], ...cspsToCheck: (ICsp | null | undefined)[]): ICsp[] {
    const csps: ICsp[] = cspsToCheck.filter(isPresent);
    if (csps.length > 0) {
      const cspCollectionIdentifiers = cspCollection.map(cspItem => getCspIdentifier(cspItem)!);
      const cspsToAdd = csps.filter(cspItem => {
        const cspIdentifier = getCspIdentifier(cspItem);
        if (cspIdentifier == null || cspCollectionIdentifiers.includes(cspIdentifier)) {
          return false;
        }
        cspCollectionIdentifiers.push(cspIdentifier);
        return true;
      });
      return [...cspsToAdd, ...cspCollection];
    }
    return cspCollection;
  }
}
