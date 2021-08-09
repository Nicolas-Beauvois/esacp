import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface IContrat {
  id?: number;
  contrat?: string | null;
  userExtras?: IUserExtra[] | null;
}

export class Contrat implements IContrat {
  constructor(public id?: number, public contrat?: string | null, public userExtras?: IUserExtra[] | null) {}
}

export function getContratIdentifier(contrat: IContrat): number | undefined {
  return contrat.id;
}
