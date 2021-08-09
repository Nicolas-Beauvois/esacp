import { IRapport } from 'app/entities/rapport/rapport.model';

export interface ISiegeLesions {
  id?: number;
  typeSiegeDeLesions?: string | null;
  rapports?: IRapport[] | null;
}

export class SiegeLesions implements ISiegeLesions {
  constructor(public id?: number, public typeSiegeDeLesions?: string | null, public rapports?: IRapport[] | null) {}
}

export function getSiegeLesionsIdentifier(siegeLesions: ISiegeLesions): number | undefined {
  return siegeLesions.id;
}
