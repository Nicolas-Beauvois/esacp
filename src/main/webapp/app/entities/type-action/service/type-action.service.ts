import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITypeAction, getTypeActionIdentifier } from '../type-action.model';

export type EntityResponseType = HttpResponse<ITypeAction>;
export type EntityArrayResponseType = HttpResponse<ITypeAction[]>;

@Injectable({ providedIn: 'root' })
export class TypeActionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/type-actions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(typeAction: ITypeAction): Observable<EntityResponseType> {
    return this.http.post<ITypeAction>(this.resourceUrl, typeAction, { observe: 'response' });
  }

  update(typeAction: ITypeAction): Observable<EntityResponseType> {
    return this.http.put<ITypeAction>(`${this.resourceUrl}/${getTypeActionIdentifier(typeAction) as number}`, typeAction, {
      observe: 'response',
    });
  }

  partialUpdate(typeAction: ITypeAction): Observable<EntityResponseType> {
    return this.http.patch<ITypeAction>(`${this.resourceUrl}/${getTypeActionIdentifier(typeAction) as number}`, typeAction, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeAction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeAction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTypeActionToCollectionIfMissing(
    typeActionCollection: ITypeAction[],
    ...typeActionsToCheck: (ITypeAction | null | undefined)[]
  ): ITypeAction[] {
    const typeActions: ITypeAction[] = typeActionsToCheck.filter(isPresent);
    if (typeActions.length > 0) {
      const typeActionCollectionIdentifiers = typeActionCollection.map(typeActionItem => getTypeActionIdentifier(typeActionItem)!);
      const typeActionsToAdd = typeActions.filter(typeActionItem => {
        const typeActionIdentifier = getTypeActionIdentifier(typeActionItem);
        if (typeActionIdentifier == null || typeActionCollectionIdentifiers.includes(typeActionIdentifier)) {
          return false;
        }
        typeActionCollectionIdentifiers.push(typeActionIdentifier);
        return true;
      });
      return [...typeActionsToAdd, ...typeActionCollection];
    }
    return typeActionCollection;
  }
}
