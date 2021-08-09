jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { StatutService } from '../service/statut.service';
import { IStatut, Statut } from '../statut.model';

import { StatutUpdateComponent } from './statut-update.component';

describe('Component Tests', () => {
  describe('Statut Management Update Component', () => {
    let comp: StatutUpdateComponent;
    let fixture: ComponentFixture<StatutUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let statutService: StatutService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [StatutUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(StatutUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StatutUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      statutService = TestBed.inject(StatutService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const statut: IStatut = { id: 456 };

        activatedRoute.data = of({ statut });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(statut));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Statut>>();
        const statut = { id: 123 };
        jest.spyOn(statutService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ statut });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: statut }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(statutService.update).toHaveBeenCalledWith(statut);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Statut>>();
        const statut = new Statut();
        jest.spyOn(statutService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ statut });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: statut }));
        saveSubject.complete();

        // THEN
        expect(statutService.create).toHaveBeenCalledWith(statut);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Statut>>();
        const statut = { id: 123 };
        jest.spyOn(statutService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ statut });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(statutService.update).toHaveBeenCalledWith(statut);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
