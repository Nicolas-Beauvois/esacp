jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RepartitionService } from '../service/repartition.service';
import { IRepartition, Repartition } from '../repartition.model';

import { RepartitionUpdateComponent } from './repartition-update.component';

describe('Component Tests', () => {
  describe('Repartition Management Update Component', () => {
    let comp: RepartitionUpdateComponent;
    let fixture: ComponentFixture<RepartitionUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let repartitionService: RepartitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RepartitionUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RepartitionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RepartitionUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      repartitionService = TestBed.inject(RepartitionService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const repartition: IRepartition = { id: 456 };

        activatedRoute.data = of({ repartition });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(repartition));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Repartition>>();
        const repartition = { id: 123 };
        jest.spyOn(repartitionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ repartition });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: repartition }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(repartitionService.update).toHaveBeenCalledWith(repartition);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Repartition>>();
        const repartition = new Repartition();
        jest.spyOn(repartitionService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ repartition });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: repartition }));
        saveSubject.complete();

        // THEN
        expect(repartitionService.create).toHaveBeenCalledWith(repartition);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Repartition>>();
        const repartition = { id: 123 };
        jest.spyOn(repartitionService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ repartition });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(repartitionService.update).toHaveBeenCalledWith(repartition);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
