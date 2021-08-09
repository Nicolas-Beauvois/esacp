import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICriticite, Criticite } from '../criticite.model';
import { CriticiteService } from '../service/criticite.service';

@Component({
  selector: 'jhi-criticite-update',
  templateUrl: './criticite-update.component.html',
})
export class CriticiteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    criticite: [],
    criticiteAcronyme: [],
  });

  constructor(protected criticiteService: CriticiteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ criticite }) => {
      this.updateForm(criticite);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const criticite = this.createFromForm();
    if (criticite.id !== undefined) {
      this.subscribeToSaveResponse(this.criticiteService.update(criticite));
    } else {
      this.subscribeToSaveResponse(this.criticiteService.create(criticite));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICriticite>>): void {
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

  protected updateForm(criticite: ICriticite): void {
    this.editForm.patchValue({
      id: criticite.id,
      criticite: criticite.criticite,
      criticiteAcronyme: criticite.criticiteAcronyme,
    });
  }

  protected createFromForm(): ICriticite {
    return {
      ...new Criticite(),
      id: this.editForm.get(['id'])!.value,
      criticite: this.editForm.get(['criticite'])!.value,
      criticiteAcronyme: this.editForm.get(['criticiteAcronyme'])!.value,
    };
  }
}
