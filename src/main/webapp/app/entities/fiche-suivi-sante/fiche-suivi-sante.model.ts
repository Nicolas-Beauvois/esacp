import * as dayjs from 'dayjs';
import { IRapport } from 'app/entities/rapport/rapport.model';
import { ITypeAt } from 'app/entities/type-at/type-at.model';

export interface IFicheSuiviSante {
  id?: number;
  suiviIndividuel?: boolean | null;
  medecinDuTravail?: string | null;
  dateDeCreation?: dayjs.Dayjs | null;
  datededebutAT?: dayjs.Dayjs | null;
  datedefinAT?: dayjs.Dayjs | null;
  propositionMedecinDuTravail?: string | null;
  aRevoirLe?: dayjs.Dayjs | null;
  rapports?: IRapport[] | null;
  typeAt?: ITypeAt | null;
}

export class FicheSuiviSante implements IFicheSuiviSante {
  constructor(
    public id?: number,
    public suiviIndividuel?: boolean | null,
    public medecinDuTravail?: string | null,
    public dateDeCreation?: dayjs.Dayjs | null,
    public datededebutAT?: dayjs.Dayjs | null,
    public datedefinAT?: dayjs.Dayjs | null,
    public propositionMedecinDuTravail?: string | null,
    public aRevoirLe?: dayjs.Dayjs | null,
    public rapports?: IRapport[] | null,
    public typeAt?: ITypeAt | null
  ) {
    this.suiviIndividuel = this.suiviIndividuel ?? false;
  }
}

export function getFicheSuiviSanteIdentifier(ficheSuiviSante: IFicheSuiviSante): number | undefined {
  return ficheSuiviSante.id;
}
