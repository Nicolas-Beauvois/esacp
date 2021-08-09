import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IType, Type } from '../type.model';
import { TypeService } from '../service/type.service';
import { IRepartition } from 'app/entities/repartition/repartition.model';
import { RepartitionService } from 'app/entities/repartition/service/repartition.service';

@Component({
  selector: 'jhi-type-update',
  templateUrl: './type-update.component.html',
})
export class TypeUpdateComponent implements OnInit {
  isSaving = false;

  repartitionsSharedCollection: IRepartition[] = [];

  editForm = this.fb.group({
    id: [],
    origine: [],
    accOrigine: [],
    repartition: [],
  });

  constructor(
    protected typeService: TypeService,
    protected repartitionService: RepartitionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ type }) => {
      this.updateForm(type);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const type = this.createFromForm();
    if (type.id !== undefined) {
      this.subscribeToSaveResponse(this.typeService.update(type));
    } else {
      this.subscribeToSaveResponse(this.typeService.create(type));
    }
  }

  trackRepartitionById(index: number, item: IRepartition): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IType>>): void {
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

  protected updateForm(type: IType): void {
    this.editForm.patchValue({
      id: type.id,
      origine: type.origine,
      accOrigine: type.accOrigine,
      repartition: type.repartition,
    });

    this.repartitionsSharedCollection = this.repartitionService.addRepartitionToCollectionIfMissing(
      this.repartitionsSharedCollection,
      type.repartition
    );
  }

  protected loadRelationshipsOptions(): void {
    this.repartitionService
      .query()
      .pipe(map((res: HttpResponse<IRepartition[]>) => res.body ?? []))
      .pipe(
        map((repartitions: IRepartition[]) =>
          this.repartitionService.addRepartitionToCollectionIfMissing(repartitions, this.editForm.get('repartition')!.value)
        )
      )
      .subscribe((repartitions: IRepartition[]) => (this.repartitionsSharedCollection = repartitions));
  }

  protected createFromForm(): IType {
    return {
      ...new Type(),
      id: this.editForm.get(['id'])!.value,
      origine: this.editForm.get(['origine'])!.value,
      accOrigine: this.editForm.get(['accOrigine'])!.value,
      repartition: this.editForm.get(['repartition'])!.value,
    };
  }
}
