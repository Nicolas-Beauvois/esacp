import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IActions, getActionsIdentifier } from '../actions.model';

export type EntityResponseType = HttpResponse<IActions>;
export type EntityArrayResponseType = HttpResponse<IActions[]>;

@Injectable({ providedIn: 'root' })
export class ActionsService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/actions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(actions: IActions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actions);
    return this.http
      .post<IActions>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(actions: IActions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actions);
    return this.http
      .put<IActions>(`${this.resourceUrl}/${getActionsIdentifier(actions) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(actions: IActions): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actions);
    return this.http
      .patch<IActions>(`${this.resourceUrl}/${getActionsIdentifier(actions) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IActions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IActions[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addActionsToCollectionIfMissing(actionsCollection: IActions[], ...actionsToCheck: (IActions | null | undefined)[]): IActions[] {
    const actions: IActions[] = actionsToCheck.filter(isPresent);
    if (actions.length > 0) {
      const actionsCollectionIdentifiers = actionsCollection.map(actionsItem => getActionsIdentifier(actionsItem)!);
      const actionsToAdd = actions.filter(actionsItem => {
        const actionsIdentifier = getActionsIdentifier(actionsItem);
        if (actionsIdentifier == null || actionsCollectionIdentifiers.includes(actionsIdentifier)) {
          return false;
        }
        actionsCollectionIdentifiers.push(actionsIdentifier);
        return true;
      });
      return [...actionsToAdd, ...actionsCollection];
    }
    return actionsCollection;
  }

  protected convertDateFromClient(actions: IActions): IActions {
    return Object.assign({}, actions, {
      dateEtHeureCreation: actions.dateEtHeureCreation?.isValid() ? actions.dateEtHeureCreation.format(DATE_FORMAT) : undefined,
      delai: actions.delai?.isValid() ? actions.delai.format(DATE_FORMAT) : undefined,
      dateEtHeureValidationPilote: actions.dateEtHeureValidationPilote?.isValid()
        ? actions.dateEtHeureValidationPilote.format(DATE_FORMAT)
        : undefined,
      dateEtHeureValidationPorteur: actions.dateEtHeureValidationPorteur?.isValid()
        ? actions.dateEtHeureValidationPorteur.format(DATE_FORMAT)
        : undefined,
      dateEtHeureValidationHse: actions.dateEtHeureValidationHse?.isValid()
        ? actions.dateEtHeureValidationHse.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateEtHeureCreation = res.body.dateEtHeureCreation ? dayjs(res.body.dateEtHeureCreation) : undefined;
      res.body.delai = res.body.delai ? dayjs(res.body.delai) : undefined;
      res.body.dateEtHeureValidationPilote = res.body.dateEtHeureValidationPilote ? dayjs(res.body.dateEtHeureValidationPilote) : undefined;
      res.body.dateEtHeureValidationPorteur = res.body.dateEtHeureValidationPorteur
        ? dayjs(res.body.dateEtHeureValidationPorteur)
        : undefined;
      res.body.dateEtHeureValidationHse = res.body.dateEtHeureValidationHse ? dayjs(res.body.dateEtHeureValidationHse) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((actions: IActions) => {
        actions.dateEtHeureCreation = actions.dateEtHeureCreation ? dayjs(actions.dateEtHeureCreation) : undefined;
        actions.delai = actions.delai ? dayjs(actions.delai) : undefined;
        actions.dateEtHeureValidationPilote = actions.dateEtHeureValidationPilote ? dayjs(actions.dateEtHeureValidationPilote) : undefined;
        actions.dateEtHeureValidationPorteur = actions.dateEtHeureValidationPorteur
          ? dayjs(actions.dateEtHeureValidationPorteur)
          : undefined;
        actions.dateEtHeureValidationHse = actions.dateEtHeureValidationHse ? dayjs(actions.dateEtHeureValidationHse) : undefined;
      });
    }
    return res;
  }
}
