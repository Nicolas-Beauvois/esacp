jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SiegeLesionsService } from '../service/siege-lesions.service';
import { ISiegeLesions, SiegeLesions } from '../siege-lesions.model';

import { SiegeLesionsUpdateComponent } from './siege-lesions-update.component';

describe('Component Tests', () => {
  describe('SiegeLesions Management Update Component', () => {
    let comp: SiegeLesionsUpdateComponent;
    let fixture: ComponentFixture<SiegeLesionsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let siegeLesionsService: SiegeLesionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SiegeLesionsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SiegeLesionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SiegeLesionsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      siegeLesionsService = TestBed.inject(SiegeLesionsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const siegeLesions: ISiegeLesions = { id: 456 };

        activatedRoute.data = of({ siegeLesions });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(siegeLesions));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SiegeLesions>>();
        const siegeLesions = { id: 123 };
        jest.spyOn(siegeLesionsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ siegeLesions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: siegeLesions }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(siegeLesionsService.update).toHaveBeenCalledWith(siegeLesions);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SiegeLesions>>();
        const siegeLesions = new SiegeLesions();
        jest.spyOn(siegeLesionsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ siegeLesions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: siegeLesions }));
        saveSubject.complete();

        // THEN
        expect(siegeLesionsService.create).toHaveBeenCalledWith(siegeLesions);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<SiegeLesions>>();
        const siegeLesions = { id: 123 };
        jest.spyOn(siegeLesionsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ siegeLesions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(siegeLesionsService.update).toHaveBeenCalledWith(siegeLesions);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
