import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IStatut, Statut } from '../statut.model';
import { StatutService } from '../service/statut.service';

@Component({
  selector: 'jhi-statut-update',
  templateUrl: './statut-update.component.html',
})
export class StatutUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    statut: [],
  });

  constructor(protected statutService: StatutService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statut }) => {
      this.updateForm(statut);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statut = this.createFromForm();
    if (statut.id !== undefined) {
      this.subscribeToSaveResponse(this.statutService.update(statut));
    } else {
      this.subscribeToSaveResponse(this.statutService.create(statut));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatut>>): void {
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

  protected updateForm(statut: IStatut): void {
    this.editForm.patchValue({
      id: statut.id,
      statut: statut.statut,
    });
  }

  protected createFromForm(): IStatut {
    return {
      ...new Statut(),
      id: this.editForm.get(['id'])!.value,
      statut: this.editForm.get(['statut'])!.value,
    };
  }
}
