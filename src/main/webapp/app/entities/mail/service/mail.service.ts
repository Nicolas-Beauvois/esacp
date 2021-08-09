import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMail, getMailIdentifier } from '../mail.model';

export type EntityResponseType = HttpResponse<IMail>;
export type EntityArrayResponseType = HttpResponse<IMail[]>;

@Injectable({ providedIn: 'root' })
export class MailService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mail');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(mail: IMail): Observable<EntityResponseType> {
    return this.http.post<IMail>(this.resourceUrl, mail, { observe: 'response' });
  }

  update(mail: IMail): Observable<EntityResponseType> {
    return this.http.put<IMail>(`${this.resourceUrl}/${getMailIdentifier(mail) as number}`, mail, { observe: 'response' });
  }

  partialUpdate(mail: IMail): Observable<EntityResponseType> {
    return this.http.patch<IMail>(`${this.resourceUrl}/${getMailIdentifier(mail) as number}`, mail, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMail>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMail[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMailToCollectionIfMissing(mailCollection: IMail[], ...mailToCheck: (IMail | null | undefined)[]): IMail[] {
    const mail: IMail[] = mailToCheck.filter(isPresent);
    if (mail.length > 0) {
      const mailCollectionIdentifiers = mailCollection.map(mailItem => getMailIdentifier(mailItem)!);
      const mailToAdd = mail.filter(mailItem => {
        const mailIdentifier = getMailIdentifier(mailItem);
        if (mailIdentifier == null || mailCollectionIdentifiers.includes(mailIdentifier)) {
          return false;
        }
        mailCollectionIdentifiers.push(mailIdentifier);
        return true;
      });
      return [...mailToAdd, ...mailCollection];
    }
    return mailCollection;
  }
}
