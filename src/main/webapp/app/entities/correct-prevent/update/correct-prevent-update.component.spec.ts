jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CorrectPreventService } from '../service/correct-prevent.service';
import { ICorrectPrevent, CorrectPrevent } from '../correct-prevent.model';

import { CorrectPreventUpdateComponent } from './correct-prevent-update.component';

describe('Component Tests', () => {
  describe('CorrectPrevent Management Update Component', () => {
    let comp: CorrectPreventUpdateComponent;
    let fixture: ComponentFixture<CorrectPreventUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let correctPreventService: CorrectPreventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CorrectPreventUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CorrectPreventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CorrectPreventUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      correctPreventService = TestBed.inject(CorrectPreventService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const correctPrevent: ICorrectPrevent = { id: 456 };

        activatedRoute.data = of({ correctPrevent });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(correctPrevent));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CorrectPrevent>>();
        const correctPrevent = { id: 123 };
        jest.spyOn(correctPreventService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ correctPrevent });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: correctPrevent }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(correctPreventService.update).toHaveBeenCalledWith(correctPrevent);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CorrectPrevent>>();
        const correctPrevent = new CorrectPrevent();
        jest.spyOn(correctPreventService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ correctPrevent });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: correctPrevent }));
        saveSubject.complete();

        // THEN
        expect(correctPreventService.create).toHaveBeenCalledWith(correctPrevent);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<CorrectPrevent>>();
        const correctPrevent = { id: 123 };
        jest.spyOn(correctPreventService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ correctPrevent });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(correctPreventService.update).toHaveBeenCalledWith(correctPrevent);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
