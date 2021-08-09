import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISexe, Sexe } from '../sexe.model';
import { SexeService } from '../service/sexe.service';

@Component({
  selector: 'jhi-sexe-update',
  templateUrl: './sexe-update.component.html',
})
export class SexeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    sexe: [],
  });

  constructor(protected sexeService: SexeService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sexe }) => {
      this.updateForm(sexe);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sexe = this.createFromForm();
    if (sexe.id !== undefined) {
      this.subscribeToSaveResponse(this.sexeService.update(sexe));
    } else {
      this.subscribeToSaveResponse(this.sexeService.create(sexe));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISexe>>): void {
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

  protected updateForm(sexe: ISexe): void {
    this.editForm.patchValue({
      id: sexe.id,
      sexe: sexe.sexe,
    });
  }

  protected createFromForm(): ISexe {
    return {
      ...new Sexe(),
      id: this.editForm.get(['id'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
    };
  }
}
