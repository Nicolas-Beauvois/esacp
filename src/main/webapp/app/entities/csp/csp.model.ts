import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface ICsp {
  id?: number;
  csp?: string | null;
  userExtras?: IUserExtra[] | null;
}

export class Csp implements ICsp {
  constructor(public id?: number, public csp?: string | null, public userExtras?: IUserExtra[] | null) {}
}

export function getCspIdentifier(csp: ICsp): number | undefined {
  return csp.id;
}
