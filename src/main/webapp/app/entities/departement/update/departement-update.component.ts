import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDepartement, Departement } from '../departement.model';
import { DepartementService } from '../service/departement.service';

@Component({
  selector: 'jhi-departement-update',
  templateUrl: './departement-update.component.html',
})
export class DepartementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    departement: [],
    typeService: [],
  });

  constructor(protected departementService: DepartementService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departement }) => {
      this.updateForm(departement);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const departement = this.createFromForm();
    if (departement.id !== undefined) {
      this.subscribeToSaveResponse(this.departementService.update(departement));
    } else {
      this.subscribeToSaveResponse(this.departementService.create(departement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartement>>): void {
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

  protected updateForm(departement: IDepartement): void {
    this.editForm.patchValue({
      id: departement.id,
      departement: departement.departement,
      typeService: departement.typeService,
    });
  }

  protected createFromForm(): IDepartement {
    return {
      ...new Departement(),
      id: this.editForm.get(['id'])!.value,
      departement: this.editForm.get(['departement'])!.value,
      typeService: this.editForm.get(['typeService'])!.value,
    };
  }
}
