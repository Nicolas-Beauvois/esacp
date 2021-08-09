import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IRepartition, Repartition } from '../repartition.model';
import { RepartitionService } from '../service/repartition.service';

@Component({
  selector: 'jhi-repartition-update',
  templateUrl: './repartition-update.component.html',
})
export class RepartitionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    repartition: [],
  });

  constructor(protected repartitionService: RepartitionService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ repartition }) => {
      this.updateForm(repartition);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const repartition = this.createFromForm();
    if (repartition.id !== undefined) {
      this.subscribeToSaveResponse(this.repartitionService.update(repartition));
    } else {
      this.subscribeToSaveResponse(this.repartitionService.create(repartition));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRepartition>>): void {
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

  protected updateForm(repartition: IRepartition): void {
    this.editForm.patchValue({
      id: repartition.id,
      repartition: repartition.repartition,
    });
  }

  protected createFromForm(): IRepartition {
    return {
      ...new Repartition(),
      id: this.editForm.get(['id'])!.value,
      repartition: this.editForm.get(['repartition'])!.value,
    };
  }
}
