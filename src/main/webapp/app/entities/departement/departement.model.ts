import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface IDepartement {
  id?: number;
  departement?: string | null;
  typeService?: string | null;
  userExtras?: IUserExtra[] | null;
}

export class Departement implements IDepartement {
  constructor(
    public id?: number,
    public departement?: string | null,
    public typeService?: string | null,
    public userExtras?: IUserExtra[] | null
  ) {}
}

export function getDepartementIdentifier(departement: IDepartement): number | undefined {
  return departement.id;
}
