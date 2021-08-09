jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PrioriteService } from '../service/priorite.service';
import { IPriorite, Priorite } from '../priorite.model';

import { PrioriteUpdateComponent } from './priorite-update.component';

describe('Component Tests', () => {
  describe('Priorite Management Update Component', () => {
    let comp: PrioriteUpdateComponent;
    let fixture: ComponentFixture<PrioriteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let prioriteService: PrioriteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PrioriteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PrioriteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrioriteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      prioriteService = TestBed.inject(PrioriteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const priorite: IPriorite = { id: 456 };

        activatedRoute.data = of({ priorite });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(priorite));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Priorite>>();
        const priorite = { id: 123 };
        jest.spyOn(prioriteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ priorite });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: priorite }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(prioriteService.update).toHaveBeenCalledWith(priorite);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Priorite>>();
        const priorite = new Priorite();
        jest.spyOn(prioriteService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ priorite });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: priorite }));
        saveSubject.complete();

        // THEN
        expect(prioriteService.create).toHaveBeenCalledWith(priorite);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Priorite>>();
        const priorite = { id: 123 };
        jest.spyOn(prioriteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ priorite });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(prioriteService.update).toHaveBeenCalledWith(priorite);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
