import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICsp, Csp } from '../csp.model';
import { CspService } from '../service/csp.service';

@Component({
  selector: 'jhi-csp-update',
  templateUrl: './csp-update.component.html',
})
export class CspUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    csp: [],
  });

  constructor(protected cspService: CspService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ csp }) => {
      this.updateForm(csp);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const csp = this.createFromForm();
    if (csp.id !== undefined) {
      this.subscribeToSaveResponse(this.cspService.update(csp));
    } else {
      this.subscribeToSaveResponse(this.cspService.create(csp));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICsp>>): void {
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

  protected updateForm(csp: ICsp): void {
    this.editForm.patchValue({
      id: csp.id,
      csp: csp.csp,
    });
  }

  protected createFromForm(): ICsp {
    return {
      ...new Csp(),
      id: this.editForm.get(['id'])!.value,
      csp: this.editForm.get(['csp'])!.value,
    };
  }
}
