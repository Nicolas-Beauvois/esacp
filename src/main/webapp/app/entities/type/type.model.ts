import { IRapport } from 'app/entities/rapport/rapport.model';
import { IRepartition } from 'app/entities/repartition/repartition.model';

export interface IType {
  id?: number;
  origine?: string | null;
  accOrigine?: string | null;
  rapports?: IRapport[] | null;
  repartition?: IRepartition | null;
}

export class Type implements IType {
  constructor(
    public id?: number,
    public origine?: string | null,
    public accOrigine?: string | null,
    public rapports?: IRapport[] | null,
    public repartition?: IRepartition | null
  ) {}
}

export function getTypeIdentifier(type: IType): number | undefined {
  return type.id;
}
