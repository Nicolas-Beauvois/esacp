jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PieceJointesService } from '../service/piece-jointes.service';
import { IPieceJointes, PieceJointes } from '../piece-jointes.model';

import { PieceJointesUpdateComponent } from './piece-jointes-update.component';

describe('Component Tests', () => {
  describe('PieceJointes Management Update Component', () => {
    let comp: PieceJointesUpdateComponent;
    let fixture: ComponentFixture<PieceJointesUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let pieceJointesService: PieceJointesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PieceJointesUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(PieceJointesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PieceJointesUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      pieceJointesService = TestBed.inject(PieceJointesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const pieceJointes: IPieceJointes = { id: 456 };

        activatedRoute.data = of({ pieceJointes });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(pieceJointes));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PieceJointes>>();
        const pieceJointes = { id: 123 };
        jest.spyOn(pieceJointesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pieceJointes });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pieceJointes }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(pieceJointesService.update).toHaveBeenCalledWith(pieceJointes);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PieceJointes>>();
        const pieceJointes = new PieceJointes();
        jest.spyOn(pieceJointesService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pieceJointes });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: pieceJointes }));
        saveSubject.complete();

        // THEN
        expect(pieceJointesService.create).toHaveBeenCalledWith(pieceJointes);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<PieceJointes>>();
        const pieceJointes = { id: 123 };
        jest.spyOn(pieceJointesService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ pieceJointes });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(pieceJointesService.update).toHaveBeenCalledWith(pieceJointes);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
