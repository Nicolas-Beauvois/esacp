import * as dayjs from 'dayjs';
import { IUser } from 'app/entities/user/user.model';
import { IStatut } from 'app/entities/statut/statut.model';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { IDepartement } from 'app/entities/departement/departement.model';
import { IContrat } from 'app/entities/contrat/contrat.model';
import { ISite } from 'app/entities/site/site.model';
import { ICsp } from 'app/entities/csp/csp.model';

export interface IUserExtra {
  id?: number;
  matricule?: string | null;
  dateDeNaissance?: dayjs.Dayjs | null;
  isRedacteur?: boolean | null;
  isPilote?: boolean | null;
  isPorteur?: boolean | null;
  isCodir?: boolean | null;
  isHse?: boolean | null;
  isValidateurHse?: boolean | null;
  user?: IUser | null;
  statut?: IStatut | null;
  sexe?: ISexe | null;
  departement?: IDepartement | null;
  contrat?: IContrat | null;
  site?: ISite | null;
  csp?: ICsp | null;
}

export class UserExtra implements IUserExtra {
  constructor(
    public id?: number,
    public matricule?: string | null,
    public dateDeNaissance?: dayjs.Dayjs | null,
    public isRedacteur?: boolean | null,
    public isPilote?: boolean | null,
    public isPorteur?: boolean | null,
    public isCodir?: boolean | null,
    public isHse?: boolean | null,
    public isValidateurHse?: boolean | null,
    public user?: IUser | null,
    public statut?: IStatut | null,
    public sexe?: ISexe | null,
    public departement?: IDepartement | null,
    public contrat?: IContrat | null,
    public site?: ISite | null,
    public csp?: ICsp | null
  ) {
    this.isRedacteur = this.isRedacteur ?? false;
    this.isPilote = this.isPilote ?? false;
    this.isPorteur = this.isPorteur ?? false;
    this.isCodir = this.isCodir ?? false;
    this.isHse = this.isHse ?? false;
    this.isValidateurHse = this.isValidateurHse ?? false;
  }
}

export function getUserExtraIdentifier(userExtra: IUserExtra): number | undefined {
  return userExtra.id;
}
