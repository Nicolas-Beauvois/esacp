import { IActions } from 'app/entities/actions/actions.model';

export interface IEtapeValidation {
  id?: number;
  etapeValidation?: string | null;
  actions?: IActions[] | null;
}

export class EtapeValidation implements IEtapeValidation {
  constructor(public id?: number, public etapeValidation?: string | null, public actions?: IActions[] | null) {}
}

export function getEtapeValidationIdentifier(etapeValidation: IEtapeValidation): number | undefined {
  return etapeValidation.id;
}
