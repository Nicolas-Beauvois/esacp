import * as dayjs from 'dayjs';
import { IRapport } from 'app/entities/rapport/rapport.model';
import { ICorrectPrevent } from 'app/entities/correct-prevent/correct-prevent.model';
import { IPriorite } from 'app/entities/priorite/priorite.model';
import { IEtapeValidation } from 'app/entities/etape-validation/etape-validation.model';
import { ITypeAction } from 'app/entities/type-action/type-action.model';
import { ICriticite } from 'app/entities/criticite/criticite.model';
import { IPieceJointes } from 'app/entities/piece-jointes/piece-jointes.model';

export interface IActions {
  id?: number;
  redacteur?: string | null;
  isActionImmediate?: boolean | null;
  dateEtHeureCreation?: dayjs.Dayjs | null;
  titre?: string | null;
  descriptionAction?: string | null;
  descriptionReponse?: string | null;
  delai?: dayjs.Dayjs | null;
  etat?: string | null;
  pilote?: string | null;
  dateEtHeureValidationPilote?: dayjs.Dayjs | null;
  porteur?: string | null;
  dateEtHeureValidationPorteur?: dayjs.Dayjs | null;
  hse?: string | null;
  dateEtHeureValidationHse?: dayjs.Dayjs | null;
  rapport?: IRapport | null;
  correctPrevent?: ICorrectPrevent | null;
  priorite?: IPriorite | null;
  etapeValidation?: IEtapeValidation | null;
  typeAction?: ITypeAction | null;
  criticite?: ICriticite | null;
  pj?: IPieceJointes | null;
}

export class Actions implements IActions {
  constructor(
    public id?: number,
    public redacteur?: string | null,
    public isActionImmediate?: boolean | null,
    public dateEtHeureCreation?: dayjs.Dayjs | null,
    public titre?: string | null,
    public descriptionAction?: string | null,
    public descriptionReponse?: string | null,
    public delai?: dayjs.Dayjs | null,
    public etat?: string | null,
    public pilote?: string | null,
    public dateEtHeureValidationPilote?: dayjs.Dayjs | null,
    public porteur?: string | null,
    public dateEtHeureValidationPorteur?: dayjs.Dayjs | null,
    public hse?: string | null,
    public dateEtHeureValidationHse?: dayjs.Dayjs | null,
    public rapport?: IRapport | null,
    public correctPrevent?: ICorrectPrevent | null,
    public priorite?: IPriorite | null,
    public etapeValidation?: IEtapeValidation | null,
    public typeAction?: ITypeAction | null,
    public criticite?: ICriticite | null,
    public pj?: IPieceJointes | null
  ) {
    this.isActionImmediate = this.isActionImmediate ?? false;
  }
}

export function getActionsIdentifier(actions: IActions): number | undefined {
  return actions.id;
}
