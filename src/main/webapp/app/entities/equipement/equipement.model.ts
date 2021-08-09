import { IRapport } from 'app/entities/rapport/rapport.model';

export interface IEquipement {
  id?: number;
  equipement?: string | null;
  rapports?: IRapport[] | null;
}

export class Equipement implements IEquipement {
  constructor(public id?: number, public equipement?: string | null, public rapports?: IRapport[] | null) {}
}

export function getEquipementIdentifier(equipement: IEquipement): number | undefined {
  return equipement.id;
}
