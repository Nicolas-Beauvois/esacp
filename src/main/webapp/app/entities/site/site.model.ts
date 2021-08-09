import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface ISite {
  id?: number;
  site?: string | null;
  userExtras?: IUserExtra[] | null;
}

export class Site implements ISite {
  constructor(public id?: number, public site?: string | null, public userExtras?: IUserExtra[] | null) {}
}

export function getSiteIdentifier(site: ISite): number | undefined {
  return site.id;
}
