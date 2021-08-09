import { IActions } from 'app/entities/actions/actions.model';

export interface ITypeAction {
  id?: number;
  typeAction?: string | null;
  actions?: IActions[] | null;
}

export class TypeAction implements ITypeAction {
  constructor(public id?: number, public typeAction?: string | null, public actions?: IActions[] | null) {}
}

export function getTypeActionIdentifier(typeAction: ITypeAction): number | undefined {
  return typeAction.id;
}
