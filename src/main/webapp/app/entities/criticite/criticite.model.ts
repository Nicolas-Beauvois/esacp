import { IActions } from 'app/entities/actions/actions.model';

export interface ICriticite {
  id?: number;
  criticite?: string | null;
  criticiteAcronyme?: string | null;
  actions?: IActions[] | null;
}

export class Criticite implements ICriticite {
  constructor(
    public id?: number,
    public criticite?: string | null,
    public criticiteAcronyme?: string | null,
    public actions?: IActions[] | null
  ) {}
}

export function getCriticiteIdentifier(criticite: ICriticite): number | undefined {
  return criticite.id;
}
