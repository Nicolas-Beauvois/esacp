import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEtapeValidation, getEtapeValidationIdentifier } from '../etape-validation.model';

export type EntityResponseType = HttpResponse<IEtapeValidation>;
export type EntityArrayResponseType = HttpResponse<IEtapeValidation[]>;

@Injectable({ providedIn: 'root' })
export class EtapeValidationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/etape-validations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(etapeValidation: IEtapeValidation): Observable<EntityResponseType> {
    return this.http.post<IEtapeValidation>(this.resourceUrl, etapeValidation, { observe: 'response' });
  }

  update(etapeValidation: IEtapeValidation): Observable<EntityResponseType> {
    return this.http.put<IEtapeValidation>(
      `${this.resourceUrl}/${getEtapeValidationIdentifier(etapeValidation) as number}`,
      etapeValidation,
      { observe: 'response' }
    );
  }

  partialUpdate(etapeValidation: IEtapeValidation): Observable<EntityResponseType> {
    return this.http.patch<IEtapeValidation>(
      `${this.resourceUrl}/${getEtapeValidationIdentifier(etapeValidation) as number}`,
      etapeValidation,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtapeValidation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtapeValidation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEtapeValidationToCollectionIfMissing(
    etapeValidationCollection: IEtapeValidation[],
    ...etapeValidationsToCheck: (IEtapeValidation | null | undefined)[]
  ): IEtapeValidation[] {
    const etapeValidations: IEtapeValidation[] = etapeValidationsToCheck.filter(isPresent);
    if (etapeValidations.length > 0) {
      const etapeValidationCollectionIdentifiers = etapeValidationCollection.map(
        etapeValidationItem => getEtapeValidationIdentifier(etapeValidationItem)!
      );
      const etapeValidationsToAdd = etapeValidations.filter(etapeValidationItem => {
        const etapeValidationIdentifier = getEtapeValidationIdentifier(etapeValidationItem);
        if (etapeValidationIdentifier == null || etapeValidationCollectionIdentifiers.includes(etapeValidationIdentifier)) {
          return false;
        }
        etapeValidationCollectionIdentifiers.push(etapeValidationIdentifier);
        return true;
      });
      return [...etapeValidationsToAdd, ...etapeValidationCollection];
    }
    return etapeValidationCollection;
  }
}
