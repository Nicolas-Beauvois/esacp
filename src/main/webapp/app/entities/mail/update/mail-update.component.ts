import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMail, Mail } from '../mail.model';
import { MailService } from '../service/mail.service';

@Component({
  selector: 'jhi-mail-update',
  templateUrl: './mail-update.component.html',
})
export class MailUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    typeMail: [],
    msgMail: [],
  });

  constructor(protected mailService: MailService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mail }) => {
      this.updateForm(mail);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mail = this.createFromForm();
    if (mail.id !== undefined) {
      this.subscribeToSaveResponse(this.mailService.update(mail));
    } else {
      this.subscribeToSaveResponse(this.mailService.create(mail));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMail>>): void {
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

  protected updateForm(mail: IMail): void {
    this.editForm.patchValue({
      id: mail.id,
      typeMail: mail.typeMail,
      msgMail: mail.msgMail,
    });
  }

  protected createFromForm(): IMail {
    return {
      ...new Mail(),
      id: this.editForm.get(['id'])!.value,
      typeMail: this.editForm.get(['typeMail'])!.value,
      msgMail: this.editForm.get(['msgMail'])!.value,
    };
  }
}
