jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureAccidentService } from '../service/nature-accident.service';
import { INatureAccident, NatureAccident } from '../nature-accident.model';

import { NatureAccidentUpdateComponent } from './nature-accident-update.component';

describe('Component Tests', () => {
  describe('NatureAccident Management Update Component', () => {
    let comp: NatureAccidentUpdateComponent;
    let fixture: ComponentFixture<NatureAccidentUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let natureAccidentService: NatureAccidentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NatureAccidentUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NatureAccidentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NatureAccidentUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      natureAccidentService = TestBed.inject(NatureAccidentService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const natureAccident: INatureAccident = { id: 456 };

        activatedRoute.data = of({ natureAccident });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(natureAccident));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<NatureAccident>>();
        const natureAccident = { id: 123 };
        jest.spyOn(natureAccidentService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ natureAccident });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: natureAccident }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(natureAccidentService.update).toHaveBeenCalledWith(natureAccident);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<NatureAccident>>();
        const natureAccident = new NatureAccident();
        jest.spyOn(natureAccidentService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ natureAccident });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: natureAccident }));
        saveSubject.complete();

        // THEN
        expect(natureAccidentService.create).toHaveBeenCalledWith(natureAccident);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<NatureAccident>>();
        const natureAccident = { id: 123 };
        jest.spyOn(natureAccidentService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ natureAccident });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(natureAccidentService.update).toHaveBeenCalledWith(natureAccident);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
