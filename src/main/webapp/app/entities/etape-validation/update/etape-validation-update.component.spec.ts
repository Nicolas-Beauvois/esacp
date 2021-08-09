jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EtapeValidationService } from '../service/etape-validation.service';
import { IEtapeValidation, EtapeValidation } from '../etape-validation.model';

import { EtapeValidationUpdateComponent } from './etape-validation-update.component';

describe('Component Tests', () => {
  describe('EtapeValidation Management Update Component', () => {
    let comp: EtapeValidationUpdateComponent;
    let fixture: ComponentFixture<EtapeValidationUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let etapeValidationService: EtapeValidationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EtapeValidationUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EtapeValidationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EtapeValidationUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      etapeValidationService = TestBed.inject(EtapeValidationService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const etapeValidation: IEtapeValidation = { id: 456 };

        activatedRoute.data = of({ etapeValidation });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(etapeValidation));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtapeValidation>>();
        const etapeValidation = { id: 123 };
        jest.spyOn(etapeValidationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etapeValidation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etapeValidation }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(etapeValidationService.update).toHaveBeenCalledWith(etapeValidation);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtapeValidation>>();
        const etapeValidation = new EtapeValidation();
        jest.spyOn(etapeValidationService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etapeValidation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: etapeValidation }));
        saveSubject.complete();

        // THEN
        expect(etapeValidationService.create).toHaveBeenCalledWith(etapeValidation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<EtapeValidation>>();
        const etapeValidation = { id: 123 };
        jest.spyOn(etapeValidationService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ etapeValidation });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(etapeValidationService.update).toHaveBeenCalledWith(etapeValidation);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
