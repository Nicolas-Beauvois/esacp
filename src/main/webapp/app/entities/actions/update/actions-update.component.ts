import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IActions, Actions } from '../actions.model';
import { ActionsService } from '../service/actions.service';
import { IRapport } from 'app/entities/rapport/rapport.model';
import { RapportService } from 'app/entities/rapport/service/rapport.service';
import { ICorrectPrevent } from 'app/entities/correct-prevent/correct-prevent.model';
import { CorrectPreventService } from 'app/entities/correct-prevent/service/correct-prevent.service';
import { IPriorite } from 'app/entities/priorite/priorite.model';
import { PrioriteService } from 'app/entities/priorite/service/priorite.service';
import { IEtapeValidation } from 'app/entities/etape-validation/etape-validation.model';
import { EtapeValidationService } from 'app/entities/etape-validation/service/etape-validation.service';
import { ITypeAction } from 'app/entities/type-action/type-action.model';
import { TypeActionService } from 'app/entities/type-action/service/type-action.service';
import { ICriticite } from 'app/entities/criticite/criticite.model';
import { CriticiteService } from 'app/entities/criticite/service/criticite.service';
import { IPieceJointes } from 'app/entities/piece-jointes/piece-jointes.model';
import { PieceJointesService } from 'app/entities/piece-jointes/service/piece-jointes.service';

@Component({
  selector: 'jhi-actions-update',
  templateUrl: './actions-update.component.html',
})
export class ActionsUpdateComponent implements OnInit {
  isSaving = false;

  rapportsSharedCollection: IRapport[] = [];
  correctPreventsSharedCollection: ICorrectPrevent[] = [];
  prioritesSharedCollection: IPriorite[] = [];
  etapeValidationsSharedCollection: IEtapeValidation[] = [];
  typeActionsSharedCollection: ITypeAction[] = [];
  criticitesSharedCollection: ICriticite[] = [];
  pieceJointesSharedCollection: IPieceJointes[] = [];

  editForm = this.fb.group({
    id: [],
    redacteur: [],
    isActionImmediate: [],
    dateEtHeureCreation: [],
    titre: [],
    descriptionAction: [],
    descriptionReponse: [],
    delai: [],
    etat: [],
    pilote: [],
    dateEtHeureValidationPilote: [],
    porteur: [],
    dateEtHeureValidationPorteur: [],
    hse: [],
    dateEtHeureValidationHse: [],
    rapport: [],
    correctPrevent: [],
    priorite: [],
    etapeValidation: [],
    typeAction: [],
    criticite: [],
    pj: [],
  });

  constructor(
    protected actionsService: ActionsService,
    protected rapportService: RapportService,
    protected correctPreventService: CorrectPreventService,
    protected prioriteService: PrioriteService,
    protected etapeValidationService: EtapeValidationService,
    protected typeActionService: TypeActionService,
    protected criticiteService: CriticiteService,
    protected pieceJointesService: PieceJointesService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actions }) => {
      this.updateForm(actions);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actions = this.createFromForm();
    if (actions.id !== undefined) {
      this.subscribeToSaveResponse(this.actionsService.update(actions));
    } else {
      this.subscribeToSaveResponse(this.actionsService.create(actions));
    }
  }

  trackRapportById(index: number, item: IRapport): number {
    return item.id!;
  }

  trackCorrectPreventById(index: number, item: ICorrectPrevent): number {
    return item.id!;
  }

  trackPrioriteById(index: number, item: IPriorite): number {
    return item.id!;
  }

  trackEtapeValidationById(index: number, item: IEtapeValidation): number {
    return item.id!;
  }

  trackTypeActionById(index: number, item: ITypeAction): number {
    return item.id!;
  }

  trackCriticiteById(index: number, item: ICriticite): number {
    return item.id!;
  }

  trackPieceJointesById(index: number, item: IPieceJointes): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActions>>): void {
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

  protected updateForm(actions: IActions): void {
    this.editForm.patchValue({
      id: actions.id,
      redacteur: actions.redacteur,
      isActionImmediate: actions.isActionImmediate,
      dateEtHeureCreation: actions.dateEtHeureCreation,
      titre: actions.titre,
      descriptionAction: actions.descriptionAction,
      descriptionReponse: actions.descriptionReponse,
      delai: actions.delai,
      etat: actions.etat,
      pilote: actions.pilote,
      dateEtHeureValidationPilote: actions.dateEtHeureValidationPilote,
      porteur: actions.porteur,
      dateEtHeureValidationPorteur: actions.dateEtHeureValidationPorteur,
      hse: actions.hse,
      dateEtHeureValidationHse: actions.dateEtHeureValidationHse,
      rapport: actions.rapport,
      correctPrevent: actions.correctPrevent,
      priorite: actions.priorite,
      etapeValidation: actions.etapeValidation,
      typeAction: actions.typeAction,
      criticite: actions.criticite,
      pj: actions.pj,
    });

    this.rapportsSharedCollection = this.rapportService.addRapportToCollectionIfMissing(this.rapportsSharedCollection, actions.rapport);
    this.correctPreventsSharedCollection = this.correctPreventService.addCorrectPreventToCollectionIfMissing(
      this.correctPreventsSharedCollection,
      actions.correctPrevent
    );
    this.prioritesSharedCollection = this.prioriteService.addPrioriteToCollectionIfMissing(
      this.prioritesSharedCollection,
      actions.priorite
    );
    this.etapeValidationsSharedCollection = this.etapeValidationService.addEtapeValidationToCollectionIfMissing(
      this.etapeValidationsSharedCollection,
      actions.etapeValidation
    );
    this.typeActionsSharedCollection = this.typeActionService.addTypeActionToCollectionIfMissing(
      this.typeActionsSharedCollection,
      actions.typeAction
    );
    this.criticitesSharedCollection = this.criticiteService.addCriticiteToCollectionIfMissing(
      this.criticitesSharedCollection,
      actions.criticite
    );
    this.pieceJointesSharedCollection = this.pieceJointesService.addPieceJointesToCollectionIfMissing(
      this.pieceJointesSharedCollection,
      actions.pj
    );
  }

  protected loadRelationshipsOptions(): void {
    this.rapportService
      .query()
      .pipe(map((res: HttpResponse<IRapport[]>) => res.body ?? []))
      .pipe(
        map((rapports: IRapport[]) => this.rapportService.addRapportToCollectionIfMissing(rapports, this.editForm.get('rapport')!.value))
      )
      .subscribe((rapports: IRapport[]) => (this.rapportsSharedCollection = rapports));

    this.correctPreventService
      .query()
      .pipe(map((res: HttpResponse<ICorrectPrevent[]>) => res.body ?? []))
      .pipe(
        map((correctPrevents: ICorrectPrevent[]) =>
          this.correctPreventService.addCorrectPreventToCollectionIfMissing(correctPrevents, this.editForm.get('correctPrevent')!.value)
        )
      )
      .subscribe((correctPrevents: ICorrectPrevent[]) => (this.correctPreventsSharedCollection = correctPrevents));

    this.prioriteService
      .query()
      .pipe(map((res: HttpResponse<IPriorite[]>) => res.body ?? []))
      .pipe(
        map((priorites: IPriorite[]) =>
          this.prioriteService.addPrioriteToCollectionIfMissing(priorites, this.editForm.get('priorite')!.value)
        )
      )
      .subscribe((priorites: IPriorite[]) => (this.prioritesSharedCollection = priorites));

    this.etapeValidationService
      .query()
      .pipe(map((res: HttpResponse<IEtapeValidation[]>) => res.body ?? []))
      .pipe(
        map((etapeValidations: IEtapeValidation[]) =>
          this.etapeValidationService.addEtapeValidationToCollectionIfMissing(etapeValidations, this.editForm.get('etapeValidation')!.value)
        )
      )
      .subscribe((etapeValidations: IEtapeValidation[]) => (this.etapeValidationsSharedCollection = etapeValidations));

    this.typeActionService
      .query()
      .pipe(map((res: HttpResponse<ITypeAction[]>) => res.body ?? []))
      .pipe(
        map((typeActions: ITypeAction[]) =>
          this.typeActionService.addTypeActionToCollectionIfMissing(typeActions, this.editForm.get('typeAction')!.value)
        )
      )
      .subscribe((typeActions: ITypeAction[]) => (this.typeActionsSharedCollection = typeActions));

    this.criticiteService
      .query()
      .pipe(map((res: HttpResponse<ICriticite[]>) => res.body ?? []))
      .pipe(
        map((criticites: ICriticite[]) =>
          this.criticiteService.addCriticiteToCollectionIfMissing(criticites, this.editForm.get('criticite')!.value)
        )
      )
      .subscribe((criticites: ICriticite[]) => (this.criticitesSharedCollection = criticites));

    this.pieceJointesService
      .query()
      .pipe(map((res: HttpResponse<IPieceJointes[]>) => res.body ?? []))
      .pipe(
        map((pieceJointes: IPieceJointes[]) =>
          this.pieceJointesService.addPieceJointesToCollectionIfMissing(pieceJointes, this.editForm.get('pj')!.value)
        )
      )
      .subscribe((pieceJointes: IPieceJointes[]) => (this.pieceJointesSharedCollection = pieceJointes));
  }

  protected createFromForm(): IActions {
    return {
      ...new Actions(),
      id: this.editForm.get(['id'])!.value,
      redacteur: this.editForm.get(['redacteur'])!.value,
      isActionImmediate: this.editForm.get(['isActionImmediate'])!.value,
      dateEtHeureCreation: this.editForm.get(['dateEtHeureCreation'])!.value,
      titre: this.editForm.get(['titre'])!.value,
      descriptionAction: this.editForm.get(['descriptionAction'])!.value,
      descriptionReponse: this.editForm.get(['descriptionReponse'])!.value,
      delai: this.editForm.get(['delai'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      pilote: this.editForm.get(['pilote'])!.value,
      dateEtHeureValidationPilote: this.editForm.get(['dateEtHeureValidationPilote'])!.value,
      porteur: this.editForm.get(['porteur'])!.value,
      dateEtHeureValidationPorteur: this.editForm.get(['dateEtHeureValidationPorteur'])!.value,
      hse: this.editForm.get(['hse'])!.value,
      dateEtHeureValidationHse: this.editForm.get(['dateEtHeureValidationHse'])!.value,
      rapport: this.editForm.get(['rapport'])!.value,
      correctPrevent: this.editForm.get(['correctPrevent'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      etapeValidation: this.editForm.get(['etapeValidation'])!.value,
      typeAction: this.editForm.get(['typeAction'])!.value,
      criticite: this.editForm.get(['criticite'])!.value,
      pj: this.editForm.get(['pj'])!.value,
    };
  }
}
