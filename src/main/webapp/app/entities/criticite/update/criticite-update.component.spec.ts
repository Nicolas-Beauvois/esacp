jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CriticiteService } from '../service/criticite.service';
import { ICriticite, Criticite } from '../criticite.model';

import { CriticiteUpdateComponent } from './criticite-update.component';

describe('Component Tests', () => {
  describe('Criticite Management Update Component', () => {
    let comp: CriticiteUpdateComponent;
    let fixture: ComponentFixture<CriticiteUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let criticiteService: CriticiteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CriticiteUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CriticiteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CriticiteUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      criticiteService = TestBed.inject(CriticiteService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const criticite: ICriticite = { id: 456 };

        activatedRoute.data = of({ criticite });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(criticite));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Criticite>>();
        const criticite = { id: 123 };
        jest.spyOn(criticiteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ criticite });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: criticite }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(criticiteService.update).toHaveBeenCalledWith(criticite);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Criticite>>();
        const criticite = new Criticite();
        jest.spyOn(criticiteService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ criticite });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: criticite }));
        saveSubject.complete();

        // THEN
        expect(criticiteService.create).toHaveBeenCalledWith(criticite);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Criticite>>();
        const criticite = { id: 123 };
        jest.spyOn(criticiteService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ criticite });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(criticiteService.update).toHaveBeenCalledWith(criticite);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
