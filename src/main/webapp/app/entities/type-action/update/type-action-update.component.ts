import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITypeAction, TypeAction } from '../type-action.model';
import { TypeActionService } from '../service/type-action.service';

@Component({
  selector: 'jhi-type-action-update',
  templateUrl: './type-action-update.component.html',
})
export class TypeActionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeAction: [],
  });

  constructor(protected typeActionService: TypeActionService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeAction }) => {
      this.updateForm(typeAction);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeAction = this.createFromForm();
    if (typeAction.id !== undefined) {
      this.subscribeToSaveResponse(this.typeActionService.update(typeAction));
    } else {
      this.subscribeToSaveResponse(this.typeActionService.create(typeAction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeAction>>): void {
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

  protected updateForm(typeAction: ITypeAction): void {
    this.editForm.patchValue({
      id: typeAction.id,
      typeAction: typeAction.typeAction,
    });
  }

  protected createFromForm(): ITypeAction {
    return {
      ...new TypeAction(),
      id: this.editForm.get(['id'])!.value,
      typeAction: this.editForm.get(['typeAction'])!.value,
    };
  }
}
