jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MailService } from '../service/mail.service';
import { IMail, Mail } from '../mail.model';

import { MailUpdateComponent } from './mail-update.component';

describe('Component Tests', () => {
  describe('Mail Management Update Component', () => {
    let comp: MailUpdateComponent;
    let fixture: ComponentFixture<MailUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let mailService: MailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MailUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MailUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      mailService = TestBed.inject(MailService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const mail: IMail = { id: 456 };

        activatedRoute.data = of({ mail });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(mail));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Mail>>();
        const mail = { id: 123 };
        jest.spyOn(mailService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ mail });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: mail }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(mailService.update).toHaveBeenCalledWith(mail);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Mail>>();
        const mail = new Mail();
        jest.spyOn(mailService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ mail });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: mail }));
        saveSubject.complete();

        // THEN
        expect(mailService.create).toHaveBeenCalledWith(mail);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Mail>>();
        const mail = { id: 123 };
        jest.spyOn(mailService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ mail });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(mailService.update).toHaveBeenCalledWith(mail);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
