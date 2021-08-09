import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPieceJointes, getPieceJointesIdentifier } from '../piece-jointes.model';

export type EntityResponseType = HttpResponse<IPieceJointes>;
export type EntityArrayResponseType = HttpResponse<IPieceJointes[]>;

@Injectable({ providedIn: 'root' })
export class PieceJointesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/piece-jointes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(pieceJointes: IPieceJointes): Observable<EntityResponseType> {
    return this.http.post<IPieceJointes>(this.resourceUrl, pieceJointes, { observe: 'response' });
  }

  update(pieceJointes: IPieceJointes): Observable<EntityResponseType> {
    return this.http.put<IPieceJointes>(`${this.resourceUrl}/${getPieceJointesIdentifier(pieceJointes) as number}`, pieceJointes, {
      observe: 'response',
    });
  }

  partialUpdate(pieceJointes: IPieceJointes): Observable<EntityResponseType> {
    return this.http.patch<IPieceJointes>(`${this.resourceUrl}/${getPieceJointesIdentifier(pieceJointes) as number}`, pieceJointes, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPieceJointes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPieceJointes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPieceJointesToCollectionIfMissing(
    pieceJointesCollection: IPieceJointes[],
    ...pieceJointesToCheck: (IPieceJointes | null | undefined)[]
  ): IPieceJointes[] {
    const pieceJointes: IPieceJointes[] = pieceJointesToCheck.filter(isPresent);
    if (pieceJointes.length > 0) {
      const pieceJointesCollectionIdentifiers = pieceJointesCollection.map(
        pieceJointesItem => getPieceJointesIdentifier(pieceJointesItem)!
      );
      const pieceJointesToAdd = pieceJointes.filter(pieceJointesItem => {
        const pieceJointesIdentifier = getPieceJointesIdentifier(pieceJointesItem);
        if (pieceJointesIdentifier == null || pieceJointesCollectionIdentifiers.includes(pieceJointesIdentifier)) {
          return false;
        }
        pieceJointesCollectionIdentifiers.push(pieceJointesIdentifier);
        return true;
      });
      return [...pieceJointesToAdd, ...pieceJointesCollection];
    }
    return pieceJointesCollection;
  }
}
