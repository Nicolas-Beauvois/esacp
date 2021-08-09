import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface IStatut {
  id?: number;
  statut?: string | null;
  userExtras?: IUserExtra[] | null;
}

export class Statut implements IStatut {
  constructor(public id?: number, public statut?: string | null, public userExtras?: IUserExtra[] | null) {}
}

export function getStatutIdentifier(statut: IStatut): number | undefined {
  return statut.id;
}
