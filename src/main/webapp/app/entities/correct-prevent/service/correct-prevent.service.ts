import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICorrectPrevent, getCorrectPreventIdentifier } from '../correct-prevent.model';

export type EntityResponseType = HttpResponse<ICorrectPrevent>;
export type EntityArrayResponseType = HttpResponse<ICorrectPrevent[]>;

@Injectable({ providedIn: 'root' })
export class CorrectPreventService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/correct-prevents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(correctPrevent: ICorrectPrevent): Observable<EntityResponseType> {
    return this.http.post<ICorrectPrevent>(this.resourceUrl, correctPrevent, { observe: 'response' });
  }

  update(correctPrevent: ICorrectPrevent): Observable<EntityResponseType> {
    return this.http.put<ICorrectPrevent>(`${this.resourceUrl}/${getCorrectPreventIdentifier(correctPrevent) as number}`, correctPrevent, {
      observe: 'response',
    });
  }

  partialUpdate(correctPrevent: ICorrectPrevent): Observable<EntityResponseType> {
    return this.http.patch<ICorrectPrevent>(
      `${this.resourceUrl}/${getCorrectPreventIdentifier(correctPrevent) as number}`,
      correctPrevent,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICorrectPrevent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICorrectPrevent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCorrectPreventToCollectionIfMissing(
    correctPreventCollection: ICorrectPrevent[],
    ...correctPreventsToCheck: (ICorrectPrevent | null | undefined)[]
  ): ICorrectPrevent[] {
    const correctPrevents: ICorrectPrevent[] = correctPreventsToCheck.filter(isPresent);
    if (correctPrevents.length > 0) {
      const correctPreventCollectionIdentifiers = correctPreventCollection.map(
        correctPreventItem => getCorrectPreventIdentifier(correctPreventItem)!
      );
      const correctPreventsToAdd = correctPrevents.filter(correctPreventItem => {
        const correctPreventIdentifier = getCorrectPreventIdentifier(correctPreventItem);
        if (correctPreventIdentifier == null || correctPreventCollectionIdentifiers.includes(correctPreventIdentifier)) {
          return false;
        }
        correctPreventCollectionIdentifiers.push(correctPreventIdentifier);
        return true;
      });
      return [...correctPreventsToAdd, ...correctPreventCollection];
    }
    return correctPreventCollection;
  }
}
