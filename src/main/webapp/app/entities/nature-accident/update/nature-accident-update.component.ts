import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INatureAccident, NatureAccident } from '../nature-accident.model';
import { NatureAccidentService } from '../service/nature-accident.service';

@Component({
  selector: 'jhi-nature-accident-update',
  templateUrl: './nature-accident-update.component.html',
})
export class NatureAccidentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeNatureAccident: [],
  });

  constructor(
    protected natureAccidentService: NatureAccidentService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureAccident }) => {
      this.updateForm(natureAccident);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const natureAccident = this.createFromForm();
    if (natureAccident.id !== undefined) {
      this.subscribeToSaveResponse(this.natureAccidentService.update(natureAccident));
    } else {
      this.subscribeToSaveResponse(this.natureAccidentService.create(natureAccident));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INatureAccident>>): void {
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

  protected updateForm(natureAccident: INatureAccident): void {
    this.editForm.patchValue({
      id: natureAccident.id,
      typeNatureAccident: natureAccident.typeNatureAccident,
    });
  }

  protected createFromForm(): INatureAccident {
    return {
      ...new NatureAccident(),
      id: this.editForm.get(['id'])!.value,
      typeNatureAccident: this.editForm.get(['typeNatureAccident'])!.value,
    };
  }
}
