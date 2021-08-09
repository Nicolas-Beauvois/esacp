jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TypeService } from '../service/type.service';
import { IType, Type } from '../type.model';
import { IRepartition } from 'app/entities/repartition/repartition.model';
import { RepartitionService } from 'app/entities/repartition/service/repartition.service';

import { TypeUpdateComponent } from './type-update.component';

describe('Component Tests', () => {
  describe('Type Management Update Component', () => {
    let comp: TypeUpdateComponent;
    let fixture: ComponentFixture<TypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let typeService: TypeService;
    let repartitionService: RepartitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      typeService = TestBed.inject(TypeService);
      repartitionService = TestBed.inject(RepartitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Repartition query and add missing value', () => {
        const type: IType = { id: 456 };
        const repartition: IRepartition = { id: 36959 };
        type.repartition = repartition;

        const repartitionCollection: IRepartition[] = [{ id: 60902 }];
        jest.spyOn(repartitionService, 'query').mockReturnValue(of(new HttpResponse({ body: repartitionCollection })));
        const additionalRepartitions = [repartition];
        const expectedCollection: IRepartition[] = [...additionalRepartitions, ...repartitionCollection];
        jest.spyOn(repartitionService, 'addRepartitionToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ type });
        comp.ngOnInit();

        expect(repartitionService.query).toHaveBeenCalled();
        expect(repartitionService.addRepartitionToCollectionIfMissing).toHaveBeenCalledWith(
          repartitionCollection,
          ...additionalRepartitions
        );
        expect(comp.repartitionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const type: IType = { id: 456 };
        const repartition: IRepartition = { id: 10288 };
        type.repartition = repartition;

        activatedRoute.data = of({ type });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(type));
        expect(comp.repartitionsSharedCollection).toContain(repartition);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Type>>();
        const type = { id: 123 };
        jest.spyOn(typeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ type });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: type }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(typeService.update).toHaveBeenCalledWith(type);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Type>>();
        const type = new Type();
        jest.spyOn(typeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ type });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: type }));
        saveSubject.complete();

        // THEN
        expect(typeService.create).toHaveBeenCalledWith(type);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Type>>();
        const type = { id: 123 };
        jest.spyOn(typeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ type });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(typeService.update).toHaveBeenCalledWith(type);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackRepartitionById', () => {
        it('Should return tracked Repartition primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRepartitionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
