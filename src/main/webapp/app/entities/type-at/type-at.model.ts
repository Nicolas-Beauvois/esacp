import { IFicheSuiviSante } from 'app/entities/fiche-suivi-sante/fiche-suivi-sante.model';

export interface ITypeAt {
  id?: number;
  typeAt?: string | null;
  ficheSuiviSantes?: IFicheSuiviSante[] | null;
}

export class TypeAt implements ITypeAt {
  constructor(public id?: number, public typeAt?: string | null, public ficheSuiviSantes?: IFicheSuiviSante[] | null) {}
}

export function getTypeAtIdentifier(typeAt: ITypeAt): number | undefined {
  return typeAt.id;
}
