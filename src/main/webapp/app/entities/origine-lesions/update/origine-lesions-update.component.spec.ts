jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OrigineLesionsService } from '../service/origine-lesions.service';
import { IOrigineLesions, OrigineLesions } from '../origine-lesions.model';

import { OrigineLesionsUpdateComponent } from './origine-lesions-update.component';

describe('Component Tests', () => {
  describe('OrigineLesions Management Update Component', () => {
    let comp: OrigineLesionsUpdateComponent;
    let fixture: ComponentFixture<OrigineLesionsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let origineLesionsService: OrigineLesionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OrigineLesionsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OrigineLesionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrigineLesionsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      origineLesionsService = TestBed.inject(OrigineLesionsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const origineLesions: IOrigineLesions = { id: 456 };

        activatedRoute.data = of({ origineLesions });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(origineLesions));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<OrigineLesions>>();
        const origineLesions = { id: 123 };
        jest.spyOn(origineLesionsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ origineLesions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: origineLesions }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(origineLesionsService.update).toHaveBeenCalledWith(origineLesions);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<OrigineLesions>>();
        const origineLesions = new OrigineLesions();
        jest.spyOn(origineLesionsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ origineLesions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: origineLesions }));
        saveSubject.complete();

        // THEN
        expect(origineLesionsService.create).toHaveBeenCalledWith(origineLesions);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<OrigineLesions>>();
        const origineLesions = { id: 123 };
        jest.spyOn(origineLesionsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ origineLesions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(origineLesionsService.update).toHaveBeenCalledWith(origineLesions);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
