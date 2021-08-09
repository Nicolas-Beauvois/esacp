import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICorrectPrevent, CorrectPrevent } from '../correct-prevent.model';
import { CorrectPreventService } from '../service/correct-prevent.service';

@Component({
  selector: 'jhi-correct-prevent-update',
  templateUrl: './correct-prevent-update.component.html',
})
export class CorrectPreventUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    correctPrevent: [],
  });

  constructor(
    protected correctPreventService: CorrectPreventService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ correctPrevent }) => {
      this.updateForm(correctPrevent);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const correctPrevent = this.createFromForm();
    if (correctPrevent.id !== undefined) {
      this.subscribeToSaveResponse(this.correctPreventService.update(correctPrevent));
    } else {
      this.subscribeToSaveResponse(this.correctPreventService.create(correctPrevent));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICorrectPrevent>>): void {
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

  protected updateForm(correctPrevent: ICorrectPrevent): void {
    this.editForm.patchValue({
      id: correctPrevent.id,
      correctPrevent: correctPrevent.correctPrevent,
    });
  }

  protected createFromForm(): ICorrectPrevent {
    return {
      ...new CorrectPrevent(),
      id: this.editForm.get(['id'])!.value,
      correctPrevent: this.editForm.get(['correctPrevent'])!.value,
    };
  }
}
