jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EquipementService } from '../service/equipement.service';
import { IEquipement, Equipement } from '../equipement.model';

import { EquipementUpdateComponent } from './equipement-update.component';

describe('Component Tests', () => {
  describe('Equipement Management Update Component', () => {
    let comp: EquipementUpdateComponent;
    let fixture: ComponentFixture<EquipementUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let equipementService: EquipementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EquipementUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EquipementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquipementUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      equipementService = TestBed.inject(EquipementService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const equipement: IEquipement = { id: 456 };

        activatedRoute.data = of({ equipement });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(equipement));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Equipement>>();
        const equipement = { id: 123 };
        jest.spyOn(equipementService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ equipement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: equipement }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(equipementService.update).toHaveBeenCalledWith(equipement);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Equipement>>();
        const equipement = new Equipement();
        jest.spyOn(equipementService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ equipement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: equipement }));
        saveSubject.complete();

        // THEN
        expect(equipementService.create).toHaveBeenCalledWith(equipement);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Equipement>>();
        const equipement = { id: 123 };
        jest.spyOn(equipementService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ equipement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(equipementService.update).toHaveBeenCalledWith(equipement);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
