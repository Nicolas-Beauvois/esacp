import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITypeRapport, TypeRapport } from '../type-rapport.model';
import { TypeRapportService } from '../service/type-rapport.service';

@Component({
  selector: 'jhi-type-rapport-update',
  templateUrl: './type-rapport-update.component.html',
})
export class TypeRapportUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeRapport: [],
  });

  constructor(protected typeRapportService: TypeRapportService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typeRapport }) => {
      this.updateForm(typeRapport);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typeRapport = this.createFromForm();
    if (typeRapport.id !== undefined) {
      this.subscribeToSaveResponse(this.typeRapportService.update(typeRapport));
    } else {
      this.subscribeToSaveResponse(this.typeRapportService.create(typeRapport));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeRapport>>): void {
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

  protected updateForm(typeRapport: ITypeRapport): void {
    this.editForm.patchValue({
      id: typeRapport.id,
      typeRapport: typeRapport.typeRapport,
    });
  }

  protected createFromForm(): ITypeRapport {
    return {
      ...new TypeRapport(),
      id: this.editForm.get(['id'])!.value,
      typeRapport: this.editForm.get(['typeRapport'])!.value,
    };
  }
}
