import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IContrat, Contrat } from '../contrat.model';
import { ContratService } from '../service/contrat.service';

@Component({
  selector: 'jhi-contrat-update',
  templateUrl: './contrat-update.component.html',
})
export class ContratUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    contrat: [],
  });

  constructor(protected contratService: ContratService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrat }) => {
      this.updateForm(contrat);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contrat = this.createFromForm();
    if (contrat.id !== undefined) {
      this.subscribeToSaveResponse(this.contratService.update(contrat));
    } else {
      this.subscribeToSaveResponse(this.contratService.create(contrat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrat>>): void {
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

  protected updateForm(contrat: IContrat): void {
    this.editForm.patchValue({
      id: contrat.id,
      contrat: contrat.contrat,
    });
  }

  protected createFromForm(): IContrat {
    return {
      ...new Contrat(),
      id: this.editForm.get(['id'])!.value,
      contrat: this.editForm.get(['contrat'])!.value,
    };
  }
}
