jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CspService } from '../service/csp.service';
import { ICsp, Csp } from '../csp.model';

import { CspUpdateComponent } from './csp-update.component';

describe('Component Tests', () => {
  describe('Csp Management Update Component', () => {
    let comp: CspUpdateComponent;
    let fixture: ComponentFixture<CspUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let cspService: CspService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CspUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CspUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CspUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      cspService = TestBed.inject(CspService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const csp: ICsp = { id: 456 };

        activatedRoute.data = of({ csp });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(csp));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Csp>>();
        const csp = { id: 123 };
        jest.spyOn(cspService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ csp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: csp }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(cspService.update).toHaveBeenCalledWith(csp);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Csp>>();
        const csp = new Csp();
        jest.spyOn(cspService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ csp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: csp }));
        saveSubject.complete();

        // THEN
        expect(cspService.create).toHaveBeenCalledWith(csp);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Csp>>();
        const csp = { id: 123 };
        jest.spyOn(cspService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ csp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(cspService.update).toHaveBeenCalledWith(csp);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
