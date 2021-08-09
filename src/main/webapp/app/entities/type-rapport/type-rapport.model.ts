import { IRapport } from 'app/entities/rapport/rapport.model';

export interface ITypeRapport {
  id?: number;
  typeRapport?: string | null;
  rapports?: IRapport[] | null;
}

export class TypeRapport implements ITypeRapport {
  constructor(public id?: number, public typeRapport?: string | null, public rapports?: IRapport[] | null) {}
}

export function getTypeRapportIdentifier(typeRapport: ITypeRapport): number | undefined {
  return typeRapport.id;
}
