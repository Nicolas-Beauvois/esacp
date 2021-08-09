import { IRapport } from 'app/entities/rapport/rapport.model';

export interface IOrigineLesions {
  id?: number;
  origineLesions?: string | null;
  rapports?: IRapport[] | null;
}

export class OrigineLesions implements IOrigineLesions {
  constructor(public id?: number, public origineLesions?: string | null, public rapports?: IRapport[] | null) {}
}

export function getOrigineLesionsIdentifier(origineLesions: IOrigineLesions): number | undefined {
  return origineLesions.id;
}
