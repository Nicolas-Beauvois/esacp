import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITypeAt, TypeAt } from '../type-at.model';
import { TypeAtService } from '../service/type-at.service';

@Component({
  selector: 'jhi-type-at-update',
  templateUrl: './type-at-update.component.html',
})
export class TypeAtUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeAt: [],
  });

  constructor(protected typeAtService: TypeAtService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAt }) => {
      this.updateForm(typeAt);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeAt = this.createFromForm();
    if (typeAt.id !== undefined) {
      this.subscribeToSaveResponse(this.typeAtService.update(typeAt));
    } else {
      this.subscribeToSaveResponse(this.typeAtService.create(typeAt));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeAt>>): void {
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

  protected updateForm(typeAt: ITypeAt): void {
    this.editForm.patchValue({
      id: typeAt.id,
      typeAt: typeAt.typeAt,
    });
  }

  protected createFromForm(): ITypeAt {
    return {
      ...new TypeAt(),
      id: this.editForm.get(['id'])!.value,
      typeAt: this.editForm.get(['typeAt'])!.value,
    };
  }
}
