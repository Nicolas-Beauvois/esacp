import { IRapport } from 'app/entities/rapport/rapport.model';

export interface INatureAccident {
  id?: number;
  typeNatureAccident?: string | null;
  rapports?: IRapport[] | null;
}

export class NatureAccident implements INatureAccident {
  constructor(public id?: number, public typeNatureAccident?: string | null, public rapports?: IRapport[] | null) {}
}

export function getNatureAccidentIdentifier(natureAccident: INatureAccident): number | undefined {
  return natureAccident.id;
}
