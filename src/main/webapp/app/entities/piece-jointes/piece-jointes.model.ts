import { IActions } from 'app/entities/actions/actions.model';

export interface IPieceJointes {
  id?: number;
  pj?: string | null;
  actions?: IActions[] | null;
}

export class PieceJointes implements IPieceJointes {
  constructor(public id?: number, public pj?: string | null, public actions?: IActions[] | null) {}
}

export function getPieceJointesIdentifier(pieceJointes: IPieceJointes): number | undefined {
  return pieceJointes.id;
}
