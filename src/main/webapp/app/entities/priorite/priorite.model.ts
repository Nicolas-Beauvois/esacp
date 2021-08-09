import { IActions } from 'app/entities/actions/actions.model';

export interface IPriorite {
  id?: number;
  priorite?: string | null;
  accrPriorite?: string | null;
  actions?: IActions[] | null;
}

export class Priorite implements IPriorite {
  constructor(
    public id?: number,
    public priorite?: string | null,
    public accrPriorite?: string | null,
    public actions?: IActions[] | null
  ) {}
}

export function getPrioriteIdentifier(priorite: IPriorite): number | undefined {
  return priorite.id;
}
