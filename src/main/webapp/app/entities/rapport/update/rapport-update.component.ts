import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRapport, Rapport } from '../rapport.model';
import { RapportService } from '../service/rapport.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ISiegeLesions } from 'app/entities/siege-lesions/siege-lesions.model';
import { SiegeLesionsService } from 'app/entities/siege-lesions/service/siege-lesions.service';
import { IFicheSuiviSante } from 'app/entities/fiche-suivi-sante/fiche-suivi-sante.model';
import { FicheSuiviSanteService } from 'app/entities/fiche-suivi-sante/service/fiche-suivi-sante.service';
import { IType } from 'app/entities/type/type.model';
import { TypeService } from 'app/entities/type/service/type.service';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { EquipementService } from 'app/entities/equipement/service/equipement.service';
import { ITypeRapport } from 'app/entities/type-rapport/type-rapport.model';
import { TypeRapportService } from 'app/entities/type-rapport/service/type-rapport.service';
import { INatureAccident } from 'app/entities/nature-accident/nature-accident.model';
import { NatureAccidentService } from 'app/entities/nature-accident/service/nature-accident.service';
import { IOrigineLesions } from 'app/entities/origine-lesions/origine-lesions.model';
import { OrigineLesionsService } from 'app/entities/origine-lesions/service/origine-lesions.service';

@Component({
  selector: 'jhi-rapport-update',
  templateUrl: './rapport-update.component.html',
})
export class RapportUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];
  siegeLesionsSharedCollection: ISiegeLesions[] = [];
  ficheSuiviSantesSharedCollection: IFicheSuiviSante[] = [];
  typesSharedCollection: IType[] = [];
  categoriesSharedCollection: ICategorie[] = [];
  equipementsSharedCollection: IEquipement[] = [];
  typeRapportsSharedCollection: ITypeRapport[] = [];
  natureAccidentsSharedCollection: INatureAccident[] = [];
  origineLesionsSharedCollection: IOrigineLesions[] = [];

  editForm = this.fb.group({
    id: [],
    redacteur: [],
    dateDeCreation: [],
    uap: [],
    dateEtHeureConnaissanceAt: [],
    prevenuComment: [],
    nomPremierePersonnePrevenu: [],
    dateEtHeurePrevenu: [],
    isTemoin: [],
    commentaireTemoin: [],
    isTiersEnCause: [],
    commentaireTiersEnCause: [],
    isAutreVictime: [],
    commentaireAutreVictime: [],
    isRapportDePolice: [],
    commentaireRapportDePolice: [],
    isVictimeTransports: [],
    commentaireVictimeTransporter: [],
    dateEtHeureMomentAccident: [],
    lieuAccident: [],
    isIdentifierDu: [],
    circonstanceAccident: [],
    materielEnCause: [],
    remarques: [],
    pilote: [],
    dateEtHeureValidationPilote: [],
    porteur: [],
    dateEtHeureValidationPorteur: [],
    hse: [],
    dateEtHeureValidationHse: [],
    isIntervention8300: [],
    isInterventionInfirmiere: [],
    commentaireInfirmere: [],
    isInterventionMedecin: [],
    commentaireMedecin: [],
    isInterventionsecouriste: [],
    commentaireSecouriste: [],
    isInterventionsecouristeExterieur: [],
    retourAuPosteDeTravail: [],
    travailLegerPossible: [],
    analyseAChaudInfirmiere: [],
    analyseAChaudCodir: [],
    analyseAChaudHse: [],
    analyseAChaudNplus1: [],
    analyseAChaudCssCt: [],
    analyseAChaudCommentaire: [],
    pourquoi1: [],
    pourquoi2: [],
    pourquoi3: [],
    pourquoi4: [],
    pourquoi5: [],
    bras: [],
    chevilles: [],
    colonne: [],
    cou: [],
    coude: [],
    cos: [],
    epaule: [],
    genou: [],
    jambes: [],
    mains: [],
    oeil: [],
    pieds: [],
    poignet: [],
    tete: [],
    torse: [],
    user: [],
    siegeLesions: [],
    ficheSuiviSante: [],
    type: [],
    categorie: [],
    equipement: [],
    typeRapport: [],
    natureAccident: [],
    origineLesions: [],
  });

  constructor(
    protected rapportService: RapportService,
    protected userService: UserService,
    protected siegeLesionsService: SiegeLesionsService,
    protected ficheSuiviSanteService: FicheSuiviSanteService,
    protected typeService: TypeService,
    protected categorieService: CategorieService,
    protected equipementService: EquipementService,
    protected typeRapportService: TypeRapportService,
    protected natureAccidentService: NatureAccidentService,
    protected origineLesionsService: OrigineLesionsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rapport }) => {
      this.updateForm(rapport);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rapport = this.createFromForm();
    if (rapport.id !== undefined) {
      this.subscribeToSaveResponse(this.rapportService.update(rapport));
    } else {
      this.subscribeToSaveResponse(this.rapportService.create(rapport));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  trackSiegeLesionsById(index: number, item: ISiegeLesions): number {
    return item.id!;
  }

  trackFicheSuiviSanteById(index: number, item: IFicheSuiviSante): number {
    return item.id!;
  }

  trackTypeById(index: number, item: IType): number {
    return item.id!;
  }

  trackCategorieById(index: number, item: ICategorie): number {
    return item.id!;
  }

  trackEquipementById(index: number, item: IEquipement): number {
    return item.id!;
  }

  trackTypeRapportById(index: number, item: ITypeRapport): number {
    return item.id!;
  }

  trackNatureAccidentById(index: number, item: INatureAccident): number {
    return item.id!;
  }

  trackOrigineLesionsById(index: number, item: IOrigineLesions): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRapport>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(rapport: IRapport): void {
    this.editForm.patchValue({
      id: rapport.id,
      redacteur: rapport.redacteur,
      dateDeCreation: rapport.dateDeCreation,
      uap: rapport.uap,
      dateEtHeureConnaissanceAt: rapport.dateEtHeureConnaissanceAt,
      prevenuComment: rapport.prevenuComment,
      nomPremierePersonnePrevenu: rapport.nomPremierePersonnePrevenu,
      dateEtHeurePrevenu: rapport.dateEtHeurePrevenu,
      isTemoin: rapport.isTemoin,
      commentaireTemoin: rapport.commentaireTemoin,
      isTiersEnCause: rapport.isTiersEnCause,
      commentaireTiersEnCause: rapport.commentaireTiersEnCause,
      isAutreVictime: rapport.isAutreVictime,
      commentaireAutreVictime: rapport.commentaireAutreVictime,
      isRapportDePolice: rapport.isRapportDePolice,
      commentaireRapportDePolice: rapport.commentaireRapportDePolice,
      isVictimeTransports: rapport.isVictimeTransports,
      commentaireVictimeTransporter: rapport.commentaireVictimeTransporter,
      dateEtHeureMomentAccident: rapport.dateEtHeureMomentAccident,
      lieuAccident: rapport.lieuAccident,
      isIdentifierDu: rapport.isIdentifierDu,
      circonstanceAccident: rapport.circonstanceAccident,
      materielEnCause: rapport.materielEnCause,
      remarques: rapport.remarques,
      pilote: rapport.pilote,
      dateEtHeureValidationPilote: rapport.dateEtHeureValidationPilote,
      porteur: rapport.porteur,
      dateEtHeureValidationPorteur: rapport.dateEtHeureValidationPorteur,
      hse: rapport.hse,
      dateEtHeureValidationHse: rapport.dateEtHeureValidationHse,
      isIntervention8300: rapport.isIntervention8300,
      isInterventionInfirmiere: rapport.isInterventionInfirmiere,
      commentaireInfirmere: rapport.commentaireInfirmere,
      isInterventionMedecin: rapport.isInterventionMedecin,
      commentaireMedecin: rapport.commentaireMedecin,
      isInterventionsecouriste: rapport.isInterventionsecouriste,
      commentaireSecouriste: rapport.commentaireSecouriste,
      isInterventionsecouristeExterieur: rapport.isInterventionsecouristeExterieur,
      retourAuPosteDeTravail: rapport.retourAuPosteDeTravail,
      travailLegerPossible: rapport.travailLegerPossible,
      analyseAChaudInfirmiere: rapport.analyseAChaudInfirmiere,
      analyseAChaudCodir: rapport.analyseAChaudCodir,
      analyseAChaudHse: rapport.analyseAChaudHse,
      analyseAChaudNplus1: rapport.analyseAChaudNplus1,
      analyseAChaudCssCt: rapport.analyseAChaudCssCt,
      analyseAChaudCommentaire: rapport.analyseAChaudCommentaire,
      pourquoi1: rapport.pourquoi1,
      pourquoi2: rapport.pourquoi2,
      pourquoi3: rapport.pourquoi3,
      pourquoi4: rapport.pourquoi4,
      pourquoi5: rapport.pourquoi5,
      bras: rapport.bras,
      chevilles: rapport.chevilles,
      colonne: rapport.colonne,
      cou: rapport.cou,
      coude: rapport.coude,
      cos: rapport.cos,
      epaule: rapport.epaule,
      genou: rapport.genou,
      jambes: rapport.jambes,
      mains: rapport.mains,
      oeil: rapport.oeil,
      pieds: rapport.pieds,
      poignet: rapport.poignet,
      tete: rapport.tete,
      torse: rapport.torse,
      user: rapport.user,
      siegeLesions: rapport.siegeLesions,
      ficheSuiviSante: rapport.ficheSuiviSante,
      type: rapport.type,
      categorie: rapport.categorie,
      equipement: rapport.equipement,
      typeRapport: rapport.typeRapport,
      natureAccident: rapport.natureAccident,
      origineLesions: rapport.origineLesions,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, rapport.user);
    this.siegeLesionsSharedCollection = this.siegeLesionsService.addSiegeLesionsToCollectionIfMissing(
      this.siegeLesionsSharedCollection,
      rapport.siegeLesions
    );
    this.ficheSuiviSantesSharedCollection = this.ficheSuiviSanteService.addFicheSuiviSanteToCollectionIfMissing(
      this.ficheSuiviSantesSharedCollection,
      rapport.ficheSuiviSante
    );
    this.typesSharedCollection = this.typeService.addTypeToCollectionIfMissing(this.typesSharedCollection, rapport.type);
    this.categoriesSharedCollection = this.categorieService.addCategorieToCollectionIfMissing(
      this.categoriesSharedCollection,
      rapport.categorie
    );
    this.equipementsSharedCollection = this.equipementService.addEquipementToCollectionIfMissing(
      this.equipementsSharedCollection,
      rapport.equipement
    );
    this.typeRapportsSharedCollection = this.typeRapportService.addTypeRapportToCollectionIfMissing(
      this.typeRapportsSharedCollection,
      rapport.typeRapport
    );
    this.natureAccidentsSharedCollection = this.natureAccidentService.addNatureAccidentToCollectionIfMissing(
      this.natureAccidentsSharedCollection,
      rapport.natureAccident
    );
    this.origineLesionsSharedCollection = this.origineLesionsService.addOrigineLesionsToCollectionIfMissing(
      this.origineLesionsSharedCollection,
      rapport.origineLesions
    );
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.siegeLesionsService
      .query()
      .pipe(map((res: HttpResponse<ISiegeLesions[]>) => res.body ?? []))
      .pipe(
        map((siegeLesions: ISiegeLesions[]) =>
          this.siegeLesionsService.addSiegeLesionsToCollectionIfMissing(siegeLesions, this.editForm.get('siegeLesions')!.value)
        )
      )
      .subscribe((siegeLesions: ISiegeLesions[]) => (this.siegeLesionsSharedCollection = siegeLesions));

    this.ficheSuiviSanteService
      .query()
      .pipe(map((res: HttpResponse<IFicheSuiviSante[]>) => res.body ?? []))
      .pipe(
        map((ficheSuiviSantes: IFicheSuiviSante[]) =>
          this.ficheSuiviSanteService.addFicheSuiviSanteToCollectionIfMissing(ficheSuiviSantes, this.editForm.get('ficheSuiviSante')!.value)
        )
      )
      .subscribe((ficheSuiviSantes: IFicheSuiviSante[]) => (this.ficheSuiviSantesSharedCollection = ficheSuiviSantes));

    this.typeService
      .query()
      .pipe(map((res: HttpResponse<IType[]>) => res.body ?? []))
      .pipe(map((types: IType[]) => this.typeService.addTypeToCollectionIfMissing(types, this.editForm.get('type')!.value)))
      .subscribe((types: IType[]) => (this.typesSharedCollection = types));

    this.categorieService
      .query()
      .pipe(map((res: HttpResponse<ICategorie[]>) => res.body ?? []))
      .pipe(
        map((categories: ICategorie[]) =>
          this.categorieService.addCategorieToCollectionIfMissing(categories, this.editForm.get('categorie')!.value)
        )
      )
      .subscribe((categories: ICategorie[]) => (this.categoriesSharedCollection = categories));

    this.equipementService
      .query()
      .pipe(map((res: HttpResponse<IEquipement[]>) => res.body ?? []))
      .pipe(
        map((equipements: IEquipement[]) =>
          this.equipementService.addEquipementToCollectionIfMissing(equipements, this.editForm.get('equipement')!.value)
        )
      )
      .subscribe((equipements: IEquipement[]) => (this.equipementsSharedCollection = equipements));

    this.typeRapportService
      .query()
      .pipe(map((res: HttpResponse<ITypeRapport[]>) => res.body ?? []))
      .pipe(
        map((typeRapports: ITypeRapport[]) =>
          this.typeRapportService.addTypeRapportToCollectionIfMissing(typeRapports, this.editForm.get('typeRapport')!.value)
        )
      )
      .subscribe((typeRapports: ITypeRapport[]) => (this.typeRapportsSharedCollection = typeRapports));

    this.natureAccidentService
      .query()
      .pipe(map((res: HttpResponse<INatureAccident[]>) => res.body ?? []))
      .pipe(
        map((natureAccidents: INatureAccident[]) =>
          this.natureAccidentService.addNatureAccidentToCollectionIfMissing(natureAccidents, this.editForm.get('natureAccident')!.value)
        )
      )
      .subscribe((natureAccidents: INatureAccident[]) => (this.natureAccidentsSharedCollection = natureAccidents));

    this.origineLesionsService
      .query()
      .pipe(map((res: HttpResponse<IOrigineLesions[]>) => res.body ?? []))
      .pipe(
        map((origineLesions: IOrigineLesions[]) =>
          this.origineLesionsService.addOrigineLesionsToCollectionIfMissing(origineLesions, this.editForm.get('origineLesions')!.value)
        )
      )
      .subscribe((origineLesions: IOrigineLesions[]) => (this.origineLesionsSharedCollection = origineLesions));
  }

  protected createFromForm(): IRapport {
    return {
      ...new Rapport(),
      id: this.editForm.get(['id'])!.value,
      redacteur: this.editForm.get(['redacteur'])!.value,
      dateDeCreation: this.editForm.get(['dateDeCreation'])!.value,
      uap: this.editForm.get(['uap'])!.value,
      dateEtHeureConnaissanceAt: this.editForm.get(['dateEtHeureConnaissanceAt'])!.value,
      prevenuComment: this.editForm.get(['prevenuComment'])!.value,
      nomPremierePersonnePrevenu: this.editForm.get(['nomPremierePersonnePrevenu'])!.value,
      dateEtHeurePrevenu: this.editForm.get(['dateEtHeurePrevenu'])!.value,
      isTemoin: this.editForm.get(['isTemoin'])!.value,
      commentaireTemoin: this.editForm.get(['commentaireTemoin'])!.value,
      isTiersEnCause: this.editForm.get(['isTiersEnCause'])!.value,
      commentaireTiersEnCause: this.editForm.get(['commentaireTiersEnCause'])!.value,
      isAutreVictime: this.editForm.get(['isAutreVictime'])!.value,
      commentaireAutreVictime: this.editForm.get(['commentaireAutreVictime'])!.value,
      isRapportDePolice: this.editForm.get(['isRapportDePolice'])!.value,
      commentaireRapportDePolice: this.editForm.get(['commentaireRapportDePolice'])!.value,
      isVictimeTransports: this.editForm.get(['isVictimeTransports'])!.value,
      commentaireVictimeTransporter: this.editForm.get(['commentaireVictimeTransporter'])!.value,
      dateEtHeureMomentAccident: this.editForm.get(['dateEtHeureMomentAccident'])!.value,
      lieuAccident: this.editForm.get(['lieuAccident'])!.value,
      isIdentifierDu: this.editForm.get(['isIdentifierDu'])!.value,
      circonstanceAccident: this.editForm.get(['circonstanceAccident'])!.value,
      materielEnCause: this.editForm.get(['materielEnCause'])!.value,
      remarques: this.editForm.get(['remarques'])!.value,
      pilote: this.editForm.get(['pilote'])!.value,
      dateEtHeureValidationPilote: this.editForm.get(['dateEtHeureValidationPilote'])!.value,
      porteur: this.editForm.get(['porteur'])!.value,
      dateEtHeureValidationPorteur: this.editForm.get(['dateEtHeureValidationPorteur'])!.value,
      hse: this.editForm.get(['hse'])!.value,
      dateEtHeureValidationHse: this.editForm.get(['dateEtHeureValidationHse'])!.value,
      isIntervention8300: this.editForm.get(['isIntervention8300'])!.value,
      isInterventionInfirmiere: this.editForm.get(['isInterventionInfirmiere'])!.value,
      commentaireInfirmere: this.editForm.get(['commentaireInfirmere'])!.value,
      isInterventionMedecin: this.editForm.get(['isInterventionMedecin'])!.value,
      commentaireMedecin: this.editForm.get(['commentaireMedecin'])!.value,
      isInterventionsecouriste: this.editForm.get(['isInterventionsecouriste'])!.value,
      commentaireSecouriste: this.editForm.get(['commentaireSecouriste'])!.value,
      isInterventionsecouristeExterieur: this.editForm.get(['isInterventionsecouristeExterieur'])!.value,
      retourAuPosteDeTravail: this.editForm.get(['retourAuPosteDeTravail'])!.value,
      travailLegerPossible: this.editForm.get(['travailLegerPossible'])!.value,
      analyseAChaudInfirmiere: this.editForm.get(['analyseAChaudInfirmiere'])!.value,
      analyseAChaudCodir: this.editForm.get(['analyseAChaudCodir'])!.value,
      analyseAChaudHse: this.editForm.get(['analyseAChaudHse'])!.value,
      analyseAChaudNplus1: this.editForm.get(['analyseAChaudNplus1'])!.value,
      analyseAChaudCssCt: this.editForm.get(['analyseAChaudCssCt'])!.value,
      analyseAChaudCommentaire: this.editForm.get(['analyseAChaudCommentaire'])!.value,
      pourquoi1: this.editForm.get(['pourquoi1'])!.value,
      pourquoi2: this.editForm.get(['pourquoi2'])!.value,
      pourquoi3: this.editForm.get(['pourquoi3'])!.value,
      pourquoi4: this.editForm.get(['pourquoi4'])!.value,
      pourquoi5: this.editForm.get(['pourquoi5'])!.value,
      bras: this.editForm.get(['bras'])!.value,
      chevilles: this.editForm.get(['chevilles'])!.value,
      colonne: this.editForm.get(['colonne'])!.value,
      cou: this.editForm.get(['cou'])!.value,
      coude: this.editForm.get(['coude'])!.value,
      cos: this.editForm.get(['cos'])!.value,
      epaule: this.editForm.get(['epaule'])!.value,
      genou: this.editForm.get(['genou'])!.value,
      jambes: this.editForm.get(['jambes'])!.value,
      mains: this.editForm.get(['mains'])!.value,
      oeil: this.editForm.get(['oeil'])!.value,
      pieds: this.editForm.get(['pieds'])!.value,
      poignet: this.editForm.get(['poignet'])!.value,
      tete: this.editForm.get(['tete'])!.value,
      torse: this.editForm.get(['torse'])!.value,
      user: this.editForm.get(['user'])!.value,
      siegeLesions: this.editForm.get(['siegeLesions'])!.value,
      ficheSuiviSante: this.editForm.get(['ficheSuiviSante'])!.value,
      type: this.editForm.get(['type'])!.value,
      categorie: this.editForm.get(['categorie'])!.value,
      equipement: this.editForm.get(['equipement'])!.value,
      typeRapport: this.editForm.get(['typeRapport'])!.value,
      natureAccident: this.editForm.get(['natureAccident'])!.value,
      origineLesions: this.editForm.get(['origineLesions'])!.value,
    };
  }
}
