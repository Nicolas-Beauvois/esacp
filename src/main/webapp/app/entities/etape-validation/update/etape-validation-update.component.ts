import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEtapeValidation, EtapeValidation } from '../etape-validation.model';
import { EtapeValidationService } from '../service/etape-validation.service';

@Component({
  selector: 'jhi-etape-validation-update',
  templateUrl: './etape-validation-update.component.html',
})
export class EtapeValidationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    etapeValidation: [],
  });

  constructor(
    protected etapeValidationService: EtapeValidationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ etapeValidation }) => {
      this.updateForm(etapeValidation);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const etapeValidation = this.createFromForm();
    if (etapeValidation.id !== undefined) {
      this.subscribeToSaveResponse(this.etapeValidationService.update(etapeValidation));
    } else {
      this.subscribeToSaveResponse(this.etapeValidationService.create(etapeValidation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEtapeValidation>>): void {
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

  protected updateForm(etapeValidation: IEtapeValidation): void {
    this.editForm.patchValue({
      id: etapeValidation.id,
      etapeValidation: etapeValidation.etapeValidation,
    });
  }

  protected createFromForm(): IEtapeValidation {
    return {
      ...new EtapeValidation(),
      id: this.editForm.get(['id'])!.value,
      etapeValidation: this.editForm.get(['etapeValidation'])!.value,
    };
  }
}
