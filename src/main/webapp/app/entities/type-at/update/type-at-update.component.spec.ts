jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TypeAtService } from '../service/type-at.service';
import { ITypeAt, TypeAt } from '../type-at.model';

import { TypeAtUpdateComponent } from './type-at-update.component';

describe('Component Tests', () => {
  describe('TypeAt Management Update Component', () => {
    let comp: TypeAtUpdateComponent;
    let fixture: ComponentFixture<TypeAtUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let typeAtService: TypeAtService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TypeAtUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TypeAtUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeAtUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      typeAtService = TestBed.inject(TypeAtService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const typeAt: ITypeAt = { id: 456 };

        activatedRoute.data = of({ typeAt });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(typeAt));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TypeAt>>();
        const typeAt = { id: 123 };
        jest.spyOn(typeAtService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ typeAt });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: typeAt }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(typeAtService.update).toHaveBeenCalledWith(typeAt);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TypeAt>>();
        const typeAt = new TypeAt();
        jest.spyOn(typeAtService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ typeAt });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: typeAt }));
        saveSubject.complete();

        // THEN
        expect(typeAtService.create).toHaveBeenCalledWith(typeAt);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TypeAt>>();
        const typeAt = { id: 123 };
        jest.spyOn(typeAtService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ typeAt });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(typeAtService.update).toHaveBeenCalledWith(typeAt);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
