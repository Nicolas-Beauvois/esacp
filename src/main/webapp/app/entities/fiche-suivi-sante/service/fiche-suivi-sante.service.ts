import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFicheSuiviSante, getFicheSuiviSanteIdentifier } from '../fiche-suivi-sante.model';

export type EntityResponseType = HttpResponse<IFicheSuiviSante>;
export type EntityArrayResponseType = HttpResponse<IFicheSuiviSante[]>;

@Injectable({ providedIn: 'root' })
export class FicheSuiviSanteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fiche-suivi-santes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ficheSuiviSante: IFicheSuiviSante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ficheSuiviSante);
    return this.http
      .post<IFicheSuiviSante>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ficheSuiviSante: IFicheSuiviSante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ficheSuiviSante);
    return this.http
      .put<IFicheSuiviSante>(`${this.resourceUrl}/${getFicheSuiviSanteIdentifier(ficheSuiviSante) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(ficheSuiviSante: IFicheSuiviSante): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ficheSuiviSante);
    return this.http
      .patch<IFicheSuiviSante>(`${this.resourceUrl}/${getFicheSuiviSanteIdentifier(ficheSuiviSante) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFicheSuiviSante>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFicheSuiviSante[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFicheSuiviSanteToCollectionIfMissing(
    ficheSuiviSanteCollection: IFicheSuiviSante[],
    ...ficheSuiviSantesToCheck: (IFicheSuiviSante | null | undefined)[]
  ): IFicheSuiviSante[] {
    const ficheSuiviSantes: IFicheSuiviSante[] = ficheSuiviSantesToCheck.filter(isPresent);
    if (ficheSuiviSantes.length > 0) {
      const ficheSuiviSanteCollectionIdentifiers = ficheSuiviSanteCollection.map(
        ficheSuiviSanteItem => getFicheSuiviSanteIdentifier(ficheSuiviSanteItem)!
      );
      const ficheSuiviSantesToAdd = ficheSuiviSantes.filter(ficheSuiviSanteItem => {
        const ficheSuiviSanteIdentifier = getFicheSuiviSanteIdentifier(ficheSuiviSanteItem);
        if (ficheSuiviSanteIdentifier == null || ficheSuiviSanteCollectionIdentifiers.includes(ficheSuiviSanteIdentifier)) {
          return false;
        }
        ficheSuiviSanteCollectionIdentifiers.push(ficheSuiviSanteIdentifier);
        return true;
      });
      return [...ficheSuiviSantesToAdd, ...ficheSuiviSanteCollection];
    }
    return ficheSuiviSanteCollection;
  }

  protected convertDateFromClient(ficheSuiviSante: IFicheSuiviSante): IFicheSuiviSante {
    return Object.assign({}, ficheSuiviSante, {
      dateDeCreation: ficheSuiviSante.dateDeCreation?.isValid() ? ficheSuiviSante.dateDeCreation.format(DATE_FORMAT) : undefined,
      datededebutAT: ficheSuiviSante.datededebutAT?.isValid() ? ficheSuiviSante.datededebutAT.format(DATE_FORMAT) : undefined,
      datedefinAT: ficheSuiviSante.datedefinAT?.isValid() ? ficheSuiviSante.datedefinAT.format(DATE_FORMAT) : undefined,
      aRevoirLe: ficheSuiviSante.aRevoirLe?.isValid() ? ficheSuiviSante.aRevoirLe.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDeCreation = res.body.dateDeCreation ? dayjs(res.body.dateDeCreation) : undefined;
      res.body.datededebutAT = res.body.datededebutAT ? dayjs(res.body.datededebutAT) : undefined;
      res.body.datedefinAT = res.body.datedefinAT ? dayjs(res.body.datedefinAT) : undefined;
      res.body.aRevoirLe = res.body.aRevoirLe ? dayjs(res.body.aRevoirLe) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ficheSuiviSante: IFicheSuiviSante) => {
        ficheSuiviSante.dateDeCreation = ficheSuiviSante.dateDeCreation ? dayjs(ficheSuiviSante.dateDeCreation) : undefined;
        ficheSuiviSante.datededebutAT = ficheSuiviSante.datededebutAT ? dayjs(ficheSuiviSante.datededebutAT) : undefined;
        ficheSuiviSante.datedefinAT = ficheSuiviSante.datedefinAT ? dayjs(ficheSuiviSante.datedefinAT) : undefined;
        ficheSuiviSante.aRevoirLe = ficheSuiviSante.aRevoirLe ? dayjs(ficheSuiviSante.aRevoirLe) : undefined;
      });
    }
    return res;
  }
}
