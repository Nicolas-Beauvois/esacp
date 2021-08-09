import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPriorite, Priorite } from '../priorite.model';
import { PrioriteService } from '../service/priorite.service';

@Component({
  selector: 'jhi-priorite-update',
  templateUrl: './priorite-update.component.html',
})
export class PrioriteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    priorite: [],
    accrPriorite: [],
  });

  constructor(protected prioriteService: PrioriteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ priorite }) => {
      this.updateForm(priorite);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const priorite = this.createFromForm();
    if (priorite.id !== undefined) {
      this.subscribeToSaveResponse(this.prioriteService.update(priorite));
    } else {
      this.subscribeToSaveResponse(this.prioriteService.create(priorite));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriorite>>): void {
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

  protected updateForm(priorite: IPriorite): void {
    this.editForm.patchValue({
      id: priorite.id,
      priorite: priorite.priorite,
      accrPriorite: priorite.accrPriorite,
    });
  }

  protected createFromForm(): IPriorite {
    return {
      ...new Priorite(),
      id: this.editForm.get(['id'])!.value,
      priorite: this.editForm.get(['priorite'])!.value,
      accrPriorite: this.editForm.get(['accrPriorite'])!.value,
    };
  }
}
