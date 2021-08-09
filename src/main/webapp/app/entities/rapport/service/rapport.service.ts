import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRapport, getRapportIdentifier } from '../rapport.model';

export type EntityResponseType = HttpResponse<IRapport>;
export type EntityArrayResponseType = HttpResponse<IRapport[]>;

@Injectable({ providedIn: 'root' })
export class RapportService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rapports');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(rapport: IRapport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rapport);
    return this.http
      .post<IRapport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(rapport: IRapport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rapport);
    return this.http
      .put<IRapport>(`${this.resourceUrl}/${getRapportIdentifier(rapport) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(rapport: IRapport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(rapport);
    return this.http
      .patch<IRapport>(`${this.resourceUrl}/${getRapportIdentifier(rapport) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRapport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRapport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRapportToCollectionIfMissing(rapportCollection: IRapport[], ...rapportsToCheck: (IRapport | null | undefined)[]): IRapport[] {
    const rapports: IRapport[] = rapportsToCheck.filter(isPresent);
    if (rapports.length > 0) {
      const rapportCollectionIdentifiers = rapportCollection.map(rapportItem => getRapportIdentifier(rapportItem)!);
      const rapportsToAdd = rapports.filter(rapportItem => {
        const rapportIdentifier = getRapportIdentifier(rapportItem);
        if (rapportIdentifier == null || rapportCollectionIdentifiers.includes(rapportIdentifier)) {
          return false;
        }
        rapportCollectionIdentifiers.push(rapportIdentifier);
        return true;
      });
      return [...rapportsToAdd, ...rapportCollection];
    }
    return rapportCollection;
  }

  protected convertDateFromClient(rapport: IRapport): IRapport {
    return Object.assign({}, rapport, {
      dateDeCreation: rapport.dateDeCreation?.isValid() ? rapport.dateDeCreation.format(DATE_FORMAT) : undefined,
      dateEtHeureConnaissanceAt: rapport.dateEtHeureConnaissanceAt?.isValid()
        ? rapport.dateEtHeureConnaissanceAt.format(DATE_FORMAT)
        : undefined,
      dateEtHeurePrevenu: rapport.dateEtHeurePrevenu?.isValid() ? rapport.dateEtHeurePrevenu.format(DATE_FORMAT) : undefined,
      dateEtHeureMomentAccident: rapport.dateEtHeureMomentAccident?.isValid()
        ? rapport.dateEtHeureMomentAccident.format(DATE_FORMAT)
        : undefined,
      dateEtHeureValidationPilote: rapport.dateEtHeureValidationPilote?.isValid()
        ? rapport.dateEtHeureValidationPilote.format(DATE_FORMAT)
        : undefined,
      dateEtHeureValidationPorteur: rapport.dateEtHeureValidationPorteur?.isValid()
        ? rapport.dateEtHeureValidationPorteur.format(DATE_FORMAT)
        : undefined,
      dateEtHeureValidationHse: rapport.dateEtHeureValidationHse?.isValid()
        ? rapport.dateEtHeureValidationHse.format(DATE_FORMAT)
        : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDeCreation = res.body.dateDeCreation ? dayjs(res.body.dateDeCreation) : undefined;
      res.body.dateEtHeureConnaissanceAt = res.body.dateEtHeureConnaissanceAt ? dayjs(res.body.dateEtHeureConnaissanceAt) : undefined;
      res.body.dateEtHeurePrevenu = res.body.dateEtHeurePrevenu ? dayjs(res.body.dateEtHeurePrevenu) : undefined;
      res.body.dateEtHeureMomentAccident = res.body.dateEtHeureMomentAccident ? dayjs(res.body.dateEtHeureMomentAccident) : undefined;
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
      res.body.forEach((rapport: IRapport) => {
        rapport.dateDeCreation = rapport.dateDeCreation ? dayjs(rapport.dateDeCreation) : undefined;
        rapport.dateEtHeureConnaissanceAt = rapport.dateEtHeureConnaissanceAt ? dayjs(rapport.dateEtHeureConnaissanceAt) : undefined;
        rapport.dateEtHeurePrevenu = rapport.dateEtHeurePrevenu ? dayjs(rapport.dateEtHeurePrevenu) : undefined;
        rapport.dateEtHeureMomentAccident = rapport.dateEtHeureMomentAccident ? dayjs(rapport.dateEtHeureMomentAccident) : undefined;
        rapport.dateEtHeureValidationPilote = rapport.dateEtHeureValidationPilote ? dayjs(rapport.dateEtHeureValidationPilote) : undefined;
        rapport.dateEtHeureValidationPorteur = rapport.dateEtHeureValidationPorteur
          ? dayjs(rapport.dateEtHeureValidationPorteur)
          : undefined;
        rapport.dateEtHeureValidationHse = rapport.dateEtHeureValidationHse ? dayjs(rapport.dateEtHeureValidationHse) : undefined;
      });
    }
    return res;
  }
}
