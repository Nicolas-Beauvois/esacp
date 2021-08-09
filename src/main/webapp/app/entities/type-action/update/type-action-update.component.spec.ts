jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TypeActionService } from '../service/type-action.service';
import { ITypeAction, TypeAction } from '../type-action.model';

import { TypeActionUpdateComponent } from './type-action-update.component';

describe('Component Tests', () => {
  describe('TypeAction Management Update Component', () => {
    let comp: TypeActionUpdateComponent;
    let fixture: ComponentFixture<TypeActionUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let typeActionService: TypeActionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TypeActionUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TypeActionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeActionUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      typeActionService = TestBed.inject(TypeActionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const typeAction: ITypeAction = { id: 456 };

        activatedRoute.data = of({ typeAction });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(typeAction));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TypeAction>>();
        const typeAction = { id: 123 };
        jest.spyOn(typeActionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ typeAction });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: typeAction }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(typeActionService.update).toHaveBeenCalledWith(typeAction);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TypeAction>>();
        const typeAction = new TypeAction();
        jest.spyOn(typeActionService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ typeAction });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: typeAction }));
        saveSubject.complete();

        // THEN
        expect(typeActionService.create).toHaveBeenCalledWith(typeAction);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TypeAction>>();
        const typeAction = { id: 123 };
        jest.spyOn(typeActionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ typeAction });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(typeActionService.update).toHaveBeenCalledWith(typeAction);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
