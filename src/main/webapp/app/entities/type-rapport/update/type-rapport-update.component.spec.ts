jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TypeRapportService } from '../service/type-rapport.service';
import { ITypeRapport, TypeRapport } from '../type-rapport.model';

import { TypeRapportUpdateComponent } from './type-rapport-update.component';

describe('Component Tests', () => {
  describe('TypeRapport Management Update Component', () => {
    let comp: TypeRapportUpdateComponent;
    let fixture: ComponentFixture<TypeRapportUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let typeRapportService: TypeRapportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TypeRapportUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TypeRapportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeRapportUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      typeRapportService = TestBed.inject(TypeRapportService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const typeRapport: ITypeRapport = { id: 456 };

        activatedRoute.data = of({ typeRapport });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(typeRapport));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TypeRapport>>();
        const typeRapport = { id: 123 };
        jest.spyOn(typeRapportService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ typeRapport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: typeRapport }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(typeRapportService.update).toHaveBeenCalledWith(typeRapport);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TypeRapport>>();
        const typeRapport = new TypeRapport();
        jest.spyOn(typeRapportService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ typeRapport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: typeRapport }));
        saveSubject.complete();

        // THEN
        expect(typeRapportService.create).toHaveBeenCalledWith(typeRapport);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<TypeRapport>>();
        const typeRapport = { id: 123 };
        jest.spyOn(typeRapportService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ typeRapport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(typeRapportService.update).toHaveBeenCalledWith(typeRapport);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
