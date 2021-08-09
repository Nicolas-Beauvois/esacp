import * as dayjs from 'dayjs';
import { IActions } from 'app/entities/actions/actions.model';
import { IUser } from 'app/entities/user/user.model';
import { ISiegeLesions } from 'app/entities/siege-lesions/siege-lesions.model';
import { IFicheSuiviSante } from 'app/entities/fiche-suivi-sante/fiche-suivi-sante.model';
import { IType } from 'app/entities/type/type.model';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { ITypeRapport } from 'app/entities/type-rapport/type-rapport.model';
import { INatureAccident } from 'app/entities/nature-accident/nature-accident.model';
import { IOrigineLesions } from 'app/entities/origine-lesions/origine-lesions.model';

export interface IRapport {
  id?: number;
  redacteur?: string | null;
  dateDeCreation?: dayjs.Dayjs | null;
  uap?: string | null;
  dateEtHeureConnaissanceAt?: dayjs.Dayjs | null;
  prevenuComment?: string | null;
  nomPremierePersonnePrevenu?: string | null;
  dateEtHeurePrevenu?: dayjs.Dayjs | null;
  isTemoin?: boolean | null;
  commentaireTemoin?: string | null;
  isTiersEnCause?: boolean | null;
  commentaireTiersEnCause?: string | null;
  isAutreVictime?: boolean | null;
  commentaireAutreVictime?: string | null;
  isRapportDePolice?: boolean | null;
  commentaireRapportDePolice?: string | null;
  isVictimeTransports?: boolean | null;
  commentaireVictimeTransporter?: string | null;
  dateEtHeureMomentAccident?: dayjs.Dayjs | null;
  lieuAccident?: string | null;
  isIdentifierDu?: boolean | null;
  circonstanceAccident?: string | null;
  materielEnCause?: string | null;
  remarques?: string | null;
  pilote?: string | null;
  dateEtHeureValidationPilote?: dayjs.Dayjs | null;
  porteur?: string | null;
  dateEtHeureValidationPorteur?: dayjs.Dayjs | null;
  hse?: string | null;
  dateEtHeureValidationHse?: dayjs.Dayjs | null;
  isIntervention8300?: boolean | null;
  isInterventionInfirmiere?: boolean | null;
  commentaireInfirmere?: string | null;
  isInterventionMedecin?: boolean | null;
  commentaireMedecin?: string | null;
  isInterventionsecouriste?: boolean | null;
  commentaireSecouriste?: string | null;
  isInterventionsecouristeExterieur?: boolean | null;
  retourAuPosteDeTravail?: boolean | null;
  travailLegerPossible?: string | null;
  analyseAChaudInfirmiere?: boolean | null;
  analyseAChaudCodir?: boolean | null;
  analyseAChaudHse?: boolean | null;
  analyseAChaudNplus1?: boolean | null;
  analyseAChaudCssCt?: boolean | null;
  analyseAChaudCommentaire?: string | null;
  pourquoi1?: string | null;
  pourquoi2?: string | null;
  pourquoi3?: string | null;
  pourquoi4?: string | null;
  pourquoi5?: string | null;
  bras?: boolean | null;
  chevilles?: boolean | null;
  colonne?: boolean | null;
  cou?: boolean | null;
  coude?: boolean | null;
  cos?: boolean | null;
  epaule?: boolean | null;
  genou?: boolean | null;
  jambes?: boolean | null;
  mains?: boolean | null;
  oeil?: boolean | null;
  pieds?: boolean | null;
  poignet?: boolean | null;
  tete?: boolean | null;
  torse?: boolean | null;
  actions?: IActions[] | null;
  user?: IUser | null;
  siegeLesions?: ISiegeLesions | null;
  ficheSuiviSante?: IFicheSuiviSante | null;
  type?: IType | null;
  categorie?: ICategorie | null;
  equipement?: IEquipement | null;
  typeRapport?: ITypeRapport | null;
  natureAccident?: INatureAccident | null;
  origineLesions?: IOrigineLesions | null;
}

export class Rapport implements IRapport {
  constructor(
    public id?: number,
    public redacteur?: string | null,
    public dateDeCreation?: dayjs.Dayjs | null,
    public uap?: string | null,
    public dateEtHeureConnaissanceAt?: dayjs.Dayjs | null,
    public prevenuComment?: string | null,
    public nomPremierePersonnePrevenu?: string | null,
    public dateEtHeurePrevenu?: dayjs.Dayjs | null,
    public isTemoin?: boolean | null,
    public commentaireTemoin?: string | null,
    public isTiersEnCause?: boolean | null,
    public commentaireTiersEnCause?: string | null,
    public isAutreVictime?: boolean | null,
    public commentaireAutreVictime?: string | null,
    public isRapportDePolice?: boolean | null,
    public commentaireRapportDePolice?: string | null,
    public isVictimeTransports?: boolean | null,
    public commentaireVictimeTransporter?: string | null,
    public dateEtHeureMomentAccident?: dayjs.Dayjs | null,
    public lieuAccident?: string | null,
    public isIdentifierDu?: boolean | null,
    public circonstanceAccident?: string | null,
    public materielEnCause?: string | null,
    public remarques?: string | null,
    public pilote?: string | null,
    public dateEtHeureValidationPilote?: dayjs.Dayjs | null,
    public porteur?: string | null,
    public dateEtHeureValidationPorteur?: dayjs.Dayjs | null,
    public hse?: string | null,
    public dateEtHeureValidationHse?: dayjs.Dayjs | null,
    public isIntervention8300?: boolean | null,
    public isInterventionInfirmiere?: boolean | null,
    public commentaireInfirmere?: string | null,
    public isInterventionMedecin?: boolean | null,
    public commentaireMedecin?: string | null,
    public isInterventionsecouriste?: boolean | null,
    public commentaireSecouriste?: string | null,
    public isInterventionsecouristeExterieur?: boolean | null,
    public retourAuPosteDeTravail?: boolean | null,
    public travailLegerPossible?: string | null,
    public analyseAChaudInfirmiere?: boolean | null,
    public analyseAChaudCodir?: boolean | null,
    public analyseAChaudHse?: boolean | null,
    public analyseAChaudNplus1?: boolean | null,
    public analyseAChaudCssCt?: boolean | null,
    public analyseAChaudCommentaire?: string | null,
    public pourquoi1?: string | null,
    public pourquoi2?: string | null,
    public pourquoi3?: string | null,
    public pourquoi4?: string | null,
    public pourquoi5?: string | null,
    public bras?: boolean | null,
    public chevilles?: boolean | null,
    public colonne?: boolean | null,
    public cou?: boolean | null,
    public coude?: boolean | null,
    public cos?: boolean | null,
    public epaule?: boolean | null,
    public genou?: boolean | null,
    public jambes?: boolean | null,
    public mains?: boolean | null,
    public oeil?: boolean | null,
    public pieds?: boolean | null,
    public poignet?: boolean | null,
    public tete?: boolean | null,
    public torse?: boolean | null,
    public actions?: IActions[] | null,
    public user?: IUser | null,
    public siegeLesions?: ISiegeLesions | null,
    public ficheSuiviSante?: IFicheSuiviSante | null,
    public type?: IType | null,
    public categorie?: ICategorie | null,
    public equipement?: IEquipement | null,
    public typeRapport?: ITypeRapport | null,
    public natureAccident?: INatureAccident | null,
    public origineLesions?: IOrigineLesions | null
  ) {
    this.isTemoin = this.isTemoin ?? false;
    this.isTiersEnCause = this.isTiersEnCause ?? false;
    this.isAutreVictime = this.isAutreVictime ?? false;
    this.isRapportDePolice = this.isRapportDePolice ?? false;
    this.isVictimeTransports = this.isVictimeTransports ?? false;
    this.isIdentifierDu = this.isIdentifierDu ?? false;
    this.isIntervention8300 = this.isIntervention8300 ?? false;
    this.isInterventionInfirmiere = this.isInterventionInfirmiere ?? false;
    this.isInterventionMedecin = this.isInterventionMedecin ?? false;
    this.isInterventionsecouriste = this.isInterventionsecouriste ?? false;
    this.isInterventionsecouristeExterieur = this.isInterventionsecouristeExterieur ?? false;
    this.retourAuPosteDeTravail = this.retourAuPosteDeTravail ?? false;
    this.analyseAChaudInfirmiere = this.analyseAChaudInfirmiere ?? false;
    this.analyseAChaudCodir = this.analyseAChaudCodir ?? false;
    this.analyseAChaudHse = this.analyseAChaudHse ?? false;
    this.analyseAChaudNplus1 = this.analyseAChaudNplus1 ?? false;
    this.analyseAChaudCssCt = this.analyseAChaudCssCt ?? false;
    this.bras = this.bras ?? false;
    this.chevilles = this.chevilles ?? false;
    this.colonne = this.colonne ?? false;
    this.cou = this.cou ?? false;
    this.coude = this.coude ?? false;
    this.cos = this.cos ?? false;
    this.epaule = this.epaule ?? false;
    this.genou = this.genou ?? false;
    this.jambes = this.jambes ?? false;
    this.mains = this.mains ?? false;
    this.oeil = this.oeil ?? false;
    this.pieds = this.pieds ?? false;
    this.poignet = this.poignet ?? false;
    this.tete = this.tete ?? false;
    this.torse = this.torse ?? false;
  }
}

export function getRapportIdentifier(rapport: IRapport): number | undefined {
  return rapport.id;
}
