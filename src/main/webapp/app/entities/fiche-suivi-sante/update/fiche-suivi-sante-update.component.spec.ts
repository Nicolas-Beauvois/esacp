jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FicheSuiviSanteService } from '../service/fiche-suivi-sante.service';
import { IFicheSuiviSante, FicheSuiviSante } from '../fiche-suivi-sante.model';
import { ITypeAt } from 'app/entities/type-at/type-at.model';
import { TypeAtService } from 'app/entities/type-at/service/type-at.service';

import { FicheSuiviSanteUpdateComponent } from './fiche-suivi-sante-update.component';

describe('Component Tests', () => {
  describe('FicheSuiviSante Management Update Component', () => {
    let comp: FicheSuiviSanteUpdateComponent;
    let fixture: ComponentFixture<FicheSuiviSanteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ficheSuiviSanteService: FicheSuiviSanteService;
    let typeAtService: TypeAtService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FicheSuiviSanteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FicheSuiviSanteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FicheSuiviSanteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ficheSuiviSanteService = TestBed.inject(FicheSuiviSanteService);
      typeAtService = TestBed.inject(TypeAtService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call TypeAt query and add missing value', () => {
        const ficheSuiviSante: IFicheSuiviSante = { id: 456 };
        const typeAt: ITypeAt = { id: 80196 };
        ficheSuiviSante.typeAt = typeAt;

        const typeAtCollection: ITypeAt[] = [{ id: 83093 }];
        jest.spyOn(typeAtService, 'query').mockReturnValue(of(new HttpResponse({ body: typeAtCollection })));
        const additionalTypeAts = [typeAt];
        const expectedCollection: ITypeAt[] = [...additionalTypeAts, ...typeAtCollection];
        jest.spyOn(typeAtService, 'addTypeAtToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ ficheSuiviSante });
        comp.ngOnInit();

        expect(typeAtService.query).toHaveBeenCalled();
        expect(typeAtService.addTypeAtToCollectionIfMissing).toHaveBeenCalledWith(typeAtCollection, ...additionalTypeAts);
        expect(comp.typeAtsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const ficheSuiviSante: IFicheSuiviSante = { id: 456 };
        const typeAt: ITypeAt = { id: 69320 };
        ficheSuiviSante.typeAt = typeAt;

        activatedRoute.data = of({ ficheSuiviSante });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(ficheSuiviSante));
        expect(comp.typeAtsSharedCollection).toContain(typeAt);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FicheSuiviSante>>();
        const ficheSuiviSante = { id: 123 };
        jest.spyOn(ficheSuiviSanteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ ficheSuiviSante });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ficheSuiviSante }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ficheSuiviSanteService.update).toHaveBeenCalledWith(ficheSuiviSante);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FicheSuiviSante>>();
        const ficheSuiviSante = new FicheSuiviSante();
        jest.spyOn(ficheSuiviSanteService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ ficheSuiviSante });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: ficheSuiviSante }));
        saveSubject.complete();

        // THEN
        expect(ficheSuiviSanteService.create).toHaveBeenCalledWith(ficheSuiviSante);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FicheSuiviSante>>();
        const ficheSuiviSante = { id: 123 };
        jest.spyOn(ficheSuiviSanteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ ficheSuiviSante });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ficheSuiviSanteService.update).toHaveBeenCalledWith(ficheSuiviSante);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTypeAtById', () => {
        it('Should return tracked TypeAt primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTypeAtById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
