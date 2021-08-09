import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IOrigineLesions, OrigineLesions } from '../origine-lesions.model';
import { OrigineLesionsService } from '../service/origine-lesions.service';

@Component({
  selector: 'jhi-origine-lesions-update',
  templateUrl: './origine-lesions-update.component.html',
})
export class OrigineLesionsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    origineLesions: [],
  });

  constructor(
    protected origineLesionsService: OrigineLesionsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ origineLesions }) => {
      this.updateForm(origineLesions);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const origineLesions = this.createFromForm();
    if (origineLesions.id !== undefined) {
      this.subscribeToSaveResponse(this.origineLesionsService.update(origineLesions));
    } else {
      this.subscribeToSaveResponse(this.origineLesionsService.create(origineLesions));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrigineLesions>>): void {
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

  protected updateForm(origineLesions: IOrigineLesions): void {
    this.editForm.patchValue({
      id: origineLesions.id,
      origineLesions: origineLesions.origineLesions,
    });
  }

  protected createFromForm(): IOrigineLesions {
    return {
      ...new OrigineLesions(),
      id: this.editForm.get(['id'])!.value,
      origineLesions: this.editForm.get(['origineLesions'])!.value,
    };
  }
}
