import { IUserExtra } from 'app/entities/user-extra/user-extra.model';

export interface ISexe {
  id?: number;
  sexe?: string | null;
  userExtras?: IUserExtra[] | null;
}

export class Sexe implements ISexe {
  constructor(public id?: number, public sexe?: string | null, public userExtras?: IUserExtra[] | null) {}
}

export function getSexeIdentifier(sexe: ISexe): number | undefined {
  return sexe.id;
}
