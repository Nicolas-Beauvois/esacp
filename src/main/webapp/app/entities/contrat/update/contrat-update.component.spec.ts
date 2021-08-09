jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ContratService } from '../service/contrat.service';
import { IContrat, Contrat } from '../contrat.model';

import { ContratUpdateComponent } from './contrat-update.component';

describe('Component Tests', () => {
  describe('Contrat Management Update Component', () => {
    let comp: ContratUpdateComponent;
    let fixture: ComponentFixture<ContratUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let contratService: ContratService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ContratUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ContratUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContratUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      contratService = TestBed.inject(ContratService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const contrat: IContrat = { id: 456 };

        activatedRoute.data = of({ contrat });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(contrat));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Contrat>>();
        const contrat = { id: 123 };
        jest.spyOn(contratService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contrat });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: contrat }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(contratService.update).toHaveBeenCalledWith(contrat);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Contrat>>();
        const contrat = new Contrat();
        jest.spyOn(contratService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contrat });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: contrat }));
        saveSubject.complete();

        // THEN
        expect(contratService.create).toHaveBeenCalledWith(contrat);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Contrat>>();
        const contrat = { id: 123 };
        jest.spyOn(contratService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ contrat });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(contratService.update).toHaveBeenCalledWith(contrat);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
