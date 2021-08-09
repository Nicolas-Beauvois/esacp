import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISiegeLesions, SiegeLesions } from '../siege-lesions.model';
import { SiegeLesionsService } from '../service/siege-lesions.service';

@Component({
  selector: 'jhi-siege-lesions-update',
  templateUrl: './siege-lesions-update.component.html',
})
export class SiegeLesionsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeSiegeDeLesions: [],
  });

  constructor(protected siegeLesionsService: SiegeLesionsService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ siegeLesions }) => {
      this.updateForm(siegeLesions);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const siegeLesions = this.createFromForm();
    if (siegeLesions.id !== undefined) {
      this.subscribeToSaveResponse(this.siegeLesionsService.update(siegeLesions));
    } else {
      this.subscribeToSaveResponse(this.siegeLesionsService.create(siegeLesions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiegeLesions>>): void {
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

  protected updateForm(siegeLesions: ISiegeLesions): void {
    this.editForm.patchValue({
      id: siegeLesions.id,
      typeSiegeDeLesions: siegeLesions.typeSiegeDeLesions,
    });
  }

  protected createFromForm(): ISiegeLesions {
    return {
      ...new SiegeLesions(),
      id: this.editForm.get(['id'])!.value,
      typeSiegeDeLesions: this.editForm.get(['typeSiegeDeLesions'])!.value,
    };
  }
}
