import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISexe, getSexeIdentifier } from '../sexe.model';

export type EntityResponseType = HttpResponse<ISexe>;
export type EntityArrayResponseType = HttpResponse<ISexe[]>;

@Injectable({ providedIn: 'root' })
export class SexeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sexes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(sexe: ISexe): Observable<EntityResponseType> {
    return this.http.post<ISexe>(this.resourceUrl, sexe, { observe: 'response' });
  }

  update(sexe: ISexe): Observable<EntityResponseType> {
    return this.http.put<ISexe>(`${this.resourceUrl}/${getSexeIdentifier(sexe) as number}`, sexe, { observe: 'response' });
  }

  partialUpdate(sexe: ISexe): Observable<EntityResponseType> {
    return this.http.patch<ISexe>(`${this.resourceUrl}/${getSexeIdentifier(sexe) as number}`, sexe, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISexe>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISexe[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSexeToCollectionIfMissing(sexeCollection: ISexe[], ...sexesToCheck: (ISexe | null | undefined)[]): ISexe[] {
    const sexes: ISexe[] = sexesToCheck.filter(isPresent);
    if (sexes.length > 0) {
      const sexeCollectionIdentifiers = sexeCollection.map(sexeItem => getSexeIdentifier(sexeItem)!);
      const sexesToAdd = sexes.filter(sexeItem => {
        const sexeIdentifier = getSexeIdentifier(sexeItem);
        if (sexeIdentifier == null || sexeCollectionIdentifiers.includes(sexeIdentifier)) {
          return false;
        }
        sexeCollectionIdentifiers.push(sexeIdentifier);
        return true;
      });
      return [...sexesToAdd, ...sexeCollection];
    }
    return sexeCollection;
  }
}
