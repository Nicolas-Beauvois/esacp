import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPieceJointes, PieceJointes } from '../piece-jointes.model';
import { PieceJointesService } from '../service/piece-jointes.service';

@Component({
  selector: 'jhi-piece-jointes-update',
  templateUrl: './piece-jointes-update.component.html',
})
export class PieceJointesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    pj: [],
  });

  constructor(protected pieceJointesService: PieceJointesService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pieceJointes }) => {
      this.updateForm(pieceJointes);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pieceJointes = this.createFromForm();
    if (pieceJointes.id !== undefined) {
      this.subscribeToSaveResponse(this.pieceJointesService.update(pieceJointes));
    } else {
      this.subscribeToSaveResponse(this.pieceJointesService.create(pieceJointes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPieceJointes>>): void {
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

  protected updateForm(pieceJointes: IPieceJointes): void {
    this.editForm.patchValue({
      id: pieceJointes.id,
      pj: pieceJointes.pj,
    });
  }

  protected createFromForm(): IPieceJointes {
    return {
      ...new PieceJointes(),
      id: this.editForm.get(['id'])!.value,
      pj: this.editForm.get(['pj'])!.value,
    };
  }
}
