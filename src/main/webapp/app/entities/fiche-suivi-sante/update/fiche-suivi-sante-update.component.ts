import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IFicheSuiviSante, FicheSuiviSante } from '../fiche-suivi-sante.model';
import { FicheSuiviSanteService } from '../service/fiche-suivi-sante.service';
import { ITypeAt } from 'app/entities/type-at/type-at.model';
import { TypeAtService } from 'app/entities/type-at/service/type-at.service';

@Component({
  selector: 'jhi-fiche-suivi-sante-update',
  templateUrl: './fiche-suivi-sante-update.component.html',
})
export class FicheSuiviSanteUpdateComponent implements OnInit {
  isSaving = false;

  typeAtsSharedCollection: ITypeAt[] = [];

  editForm = this.fb.group({
    id: [],
    suiviIndividuel: [],
    medecinDuTravail: [],
    dateDeCreation: [],
    datededebutAT: [],
    datedefinAT: [],
    propositionMedecinDuTravail: [],
    aRevoirLe: [],
    typeAt: [],
  });

  constructor(
    protected ficheSuiviSanteService: FicheSuiviSanteService,
    protected typeAtService: TypeAtService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ficheSuiviSante }) => {
      this.updateForm(ficheSuiviSante);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ficheSuiviSante = this.createFromForm();
    if (ficheSuiviSante.id !== undefined) {
      this.subscribeToSaveResponse(this.ficheSuiviSanteService.update(ficheSuiviSante));
    } else {
      this.subscribeToSaveResponse(this.ficheSuiviSanteService.create(ficheSuiviSante));
    }
  }

  trackTypeAtById(index: number, item: ITypeAt): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFicheSuiviSante>>): void {
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

  protected updateForm(ficheSuiviSante: IFicheSuiviSante): void {
    this.editForm.patchValue({
      id: ficheSuiviSante.id,
      suiviIndividuel: ficheSuiviSante.suiviIndividuel,
      medecinDuTravail: ficheSuiviSante.medecinDuTravail,
      dateDeCreation: ficheSuiviSante.dateDeCreation,
      datededebutAT: ficheSuiviSante.datededebutAT,
      datedefinAT: ficheSuiviSante.datedefinAT,
      propositionMedecinDuTravail: ficheSuiviSante.propositionMedecinDuTravail,
      aRevoirLe: ficheSuiviSante.aRevoirLe,
      typeAt: ficheSuiviSante.typeAt,
    });

    this.typeAtsSharedCollection = this.typeAtService.addTypeAtToCollectionIfMissing(this.typeAtsSharedCollection, ficheSuiviSante.typeAt);
  }

  protected loadRelationshipsOptions(): void {
    this.typeAtService
      .query()
      .pipe(map((res: HttpResponse<ITypeAt[]>) => res.body ?? []))
      .pipe(map((typeAts: ITypeAt[]) => this.typeAtService.addTypeAtToCollectionIfMissing(typeAts, this.editForm.get('typeAt')!.value)))
      .subscribe((typeAts: ITypeAt[]) => (this.typeAtsSharedCollection = typeAts));
  }

  protected createFromForm(): IFicheSuiviSante {
    return {
      ...new FicheSuiviSante(),
      id: this.editForm.get(['id'])!.value,
      suiviIndividuel: this.editForm.get(['suiviIndividuel'])!.value,
      medecinDuTravail: this.editForm.get(['medecinDuTravail'])!.value,
      dateDeCreation: this.editForm.get(['dateDeCreation'])!.value,
      datededebutAT: this.editForm.get(['datededebutAT'])!.value,
      datedefinAT: this.editForm.get(['datedefinAT'])!.value,
      propositionMedecinDuTravail: this.editForm.get(['propositionMedecinDuTravail'])!.value,
      aRevoirLe: this.editForm.get(['aRevoirLe'])!.value,
      typeAt: this.editForm.get(['typeAt'])!.value,
    };
  }
}
