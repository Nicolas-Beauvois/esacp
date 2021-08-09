import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEquipement, Equipement } from '../equipement.model';
import { EquipementService } from '../service/equipement.service';

@Component({
  selector: 'jhi-equipement-update',
  templateUrl: './equipement-update.component.html',
})
export class EquipementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    equipement: [],
  });

  constructor(protected equipementService: EquipementService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipement }) => {
      this.updateForm(equipement);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipement = this.createFromForm();
    if (equipement.id !== undefined) {
      this.subscribeToSaveResponse(this.equipementService.update(equipement));
    } else {
      this.subscribeToSaveResponse(this.equipementService.create(equipement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipement>>): void {
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

  protected updateForm(equipement: IEquipement): void {
    this.editForm.patchValue({
      id: equipement.id,
      equipement: equipement.equipement,
    });
  }

  protected createFromForm(): IEquipement {
    return {
      ...new Equipement(),
      id: this.editForm.get(['id'])!.value,
      equipement: this.editForm.get(['equipement'])!.value,
    };
  }
}
