import { IActions } from 'app/entities/actions/actions.model';

export interface ICorrectPrevent {
  id?: number;
  correctPrevent?: string | null;
  actions?: IActions[] | null;
}

export class CorrectPrevent implements ICorrectPrevent {
  constructor(public id?: number, public correctPrevent?: string | null, public actions?: IActions[] | null) {}
}

export function getCorrectPreventIdentifier(correctPrevent: ICorrectPrevent): number | undefined {
  return correctPrevent.id;
}
