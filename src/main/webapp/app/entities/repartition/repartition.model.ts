import { IType } from 'app/entities/type/type.model';

export interface IRepartition {
  id?: number;
  repartition?: string | null;
  types?: IType[] | null;
}

export class Repartition implements IRepartition {
  constructor(public id?: number, public repartition?: string | null, public types?: IType[] | null) {}
}

export function getRepartitionIdentifier(repartition: IRepartition): number | undefined {
  return repartition.id;
}
