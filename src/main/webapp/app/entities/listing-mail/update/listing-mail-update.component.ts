import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IListingMail, ListingMail } from '../listing-mail.model';
import { ListingMailService } from '../service/listing-mail.service';

@Component({
  selector: 'jhi-listing-mail-update',
  templateUrl: './listing-mail-update.component.html',
})
export class ListingMailUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeMessage: [],
    email: [],
  });

  constructor(protected listingMailService: ListingMailService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listingMail }) => {
      this.updateForm(listingMail);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const listingMail = this.createFromForm();
    if (listingMail.id !== undefined) {
      this.subscribeToSaveResponse(this.listingMailService.update(listingMail));
    } else {
      this.subscribeToSaveResponse(this.listingMailService.create(listingMail));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IListingMail>>): void {
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

  protected updateForm(listingMail: IListingMail): void {
    this.editForm.patchValue({
      id: listingMail.id,
      typeMessage: listingMail.typeMessage,
      email: listingMail.email,
    });
  }

  protected createFromForm(): IListingMail {
    return {
      ...new ListingMail(),
      id: this.editForm.get(['id'])!.value,
      typeMessage: this.editForm.get(['typeMessage'])!.value,
      email: this.editForm.get(['email'])!.value,
    };
  }
}
