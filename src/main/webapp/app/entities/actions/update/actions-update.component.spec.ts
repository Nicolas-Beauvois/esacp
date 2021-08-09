jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ActionsService } from '../service/actions.service';
import { IActions, Actions } from '../actions.model';
import { IRapport } from 'app/entities/rapport/rapport.model';
import { RapportService } from 'app/entities/rapport/service/rapport.service';
import { ICorrectPrevent } from 'app/entities/correct-prevent/correct-prevent.model';
import { CorrectPreventService } from 'app/entities/correct-prevent/service/correct-prevent.service';
import { IPriorite } from 'app/entities/priorite/priorite.model';
import { PrioriteService } from 'app/entities/priorite/service/priorite.service';
import { IEtapeValidation } from 'app/entities/etape-validation/etape-validation.model';
import { EtapeValidationService } from 'app/entities/etape-validation/service/etape-validation.service';
import { ITypeAction } from 'app/entities/type-action/type-action.model';
import { TypeActionService } from 'app/entities/type-action/service/type-action.service';
import { ICriticite } from 'app/entities/criticite/criticite.model';
import { CriticiteService } from 'app/entities/criticite/service/criticite.service';
import { IPieceJointes } from 'app/entities/piece-jointes/piece-jointes.model';
import { PieceJointesService } from 'app/entities/piece-jointes/service/piece-jointes.service';

import { ActionsUpdateComponent } from './actions-update.component';

describe('Component Tests', () => {
  describe('Actions Management Update Component', () => {
    let comp: ActionsUpdateComponent;
    let fixture: ComponentFixture<ActionsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let actionsService: ActionsService;
    let rapportService: RapportService;
    let correctPreventService: CorrectPreventService;
    let prioriteService: PrioriteService;
    let etapeValidationService: EtapeValidationService;
    let typeActionService: TypeActionService;
    let criticiteService: CriticiteService;
    let pieceJointesService: PieceJointesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ActionsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ActionsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActionsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      actionsService = TestBed.inject(ActionsService);
      rapportService = TestBed.inject(RapportService);
      correctPreventService = TestBed.inject(CorrectPreventService);
      prioriteService = TestBed.inject(PrioriteService);
      etapeValidationService = TestBed.inject(EtapeValidationService);
      typeActionService = TestBed.inject(TypeActionService);
      criticiteService = TestBed.inject(CriticiteService);
      pieceJointesService = TestBed.inject(PieceJointesService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Rapport query and add missing value', () => {
        const actions: IActions = { id: 456 };
        const rapport: IRapport = { id: 16454 };
        actions.rapport = rapport;

        const rapportCollection: IRapport[] = [{ id: 89298 }];
        jest.spyOn(rapportService, 'query').mockReturnValue(of(new HttpResponse({ body: rapportCollection })));
        const additionalRapports = [rapport];
        const expectedCollection: IRapport[] = [...additionalRapports, ...rapportCollection];
        jest.spyOn(rapportService, 'addRapportToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        expect(rapportService.query).toHaveBeenCalled();
        expect(rapportService.addRapportToCollectionIfMissing).toHaveBeenCalledWith(rapportCollection, ...additionalRapports);
        expect(comp.rapportsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call CorrectPrevent query and add missing value', () => {
        const actions: IActions = { id: 456 };
        const correctPrevent: ICorrectPrevent = { id: 56361 };
        actions.correctPrevent = correctPrevent;

        const correctPreventCollection: ICorrectPrevent[] = [{ id: 93156 }];
        jest.spyOn(correctPreventService, 'query').mockReturnValue(of(new HttpResponse({ body: correctPreventCollection })));
        const additionalCorrectPrevents = [correctPrevent];
        const expectedCollection: ICorrectPrevent[] = [...additionalCorrectPrevents, ...correctPreventCollection];
        jest.spyOn(correctPreventService, 'addCorrectPreventToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        expect(correctPreventService.query).toHaveBeenCalled();
        expect(correctPreventService.addCorrectPreventToCollectionIfMissing).toHaveBeenCalledWith(
          correctPreventCollection,
          ...additionalCorrectPrevents
        );
        expect(comp.correctPreventsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Priorite query and add missing value', () => {
        const actions: IActions = { id: 456 };
        const priorite: IPriorite = { id: 79431 };
        actions.priorite = priorite;

        const prioriteCollection: IPriorite[] = [{ id: 95506 }];
        jest.spyOn(prioriteService, 'query').mockReturnValue(of(new HttpResponse({ body: prioriteCollection })));
        const additionalPriorites = [priorite];
        const expectedCollection: IPriorite[] = [...additionalPriorites, ...prioriteCollection];
        jest.spyOn(prioriteService, 'addPrioriteToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        expect(prioriteService.query).toHaveBeenCalled();
        expect(prioriteService.addPrioriteToCollectionIfMissing).toHaveBeenCalledWith(prioriteCollection, ...additionalPriorites);
        expect(comp.prioritesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call EtapeValidation query and add missing value', () => {
        const actions: IActions = { id: 456 };
        const etapeValidation: IEtapeValidation = { id: 39783 };
        actions.etapeValidation = etapeValidation;

        const etapeValidationCollection: IEtapeValidation[] = [{ id: 28922 }];
        jest.spyOn(etapeValidationService, 'query').mockReturnValue(of(new HttpResponse({ body: etapeValidationCollection })));
        const additionalEtapeValidations = [etapeValidation];
        const expectedCollection: IEtapeValidation[] = [...additionalEtapeValidations, ...etapeValidationCollection];
        jest.spyOn(etapeValidationService, 'addEtapeValidationToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        expect(etapeValidationService.query).toHaveBeenCalled();
        expect(etapeValidationService.addEtapeValidationToCollectionIfMissing).toHaveBeenCalledWith(
          etapeValidationCollection,
          ...additionalEtapeValidations
        );
        expect(comp.etapeValidationsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call TypeAction query and add missing value', () => {
        const actions: IActions = { id: 456 };
        const typeAction: ITypeAction = { id: 70382 };
        actions.typeAction = typeAction;

        const typeActionCollection: ITypeAction[] = [{ id: 94919 }];
        jest.spyOn(typeActionService, 'query').mockReturnValue(of(new HttpResponse({ body: typeActionCollection })));
        const additionalTypeActions = [typeAction];
        const expectedCollection: ITypeAction[] = [...additionalTypeActions, ...typeActionCollection];
        jest.spyOn(typeActionService, 'addTypeActionToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        expect(typeActionService.query).toHaveBeenCalled();
        expect(typeActionService.addTypeActionToCollectionIfMissing).toHaveBeenCalledWith(typeActionCollection, ...additionalTypeActions);
        expect(comp.typeActionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Criticite query and add missing value', () => {
        const actions: IActions = { id: 456 };
        const criticite: ICriticite = { id: 63826 };
        actions.criticite = criticite;

        const criticiteCollection: ICriticite[] = [{ id: 98119 }];
        jest.spyOn(criticiteService, 'query').mockReturnValue(of(new HttpResponse({ body: criticiteCollection })));
        const additionalCriticites = [criticite];
        const expectedCollection: ICriticite[] = [...additionalCriticites, ...criticiteCollection];
        jest.spyOn(criticiteService, 'addCriticiteToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        expect(criticiteService.query).toHaveBeenCalled();
        expect(criticiteService.addCriticiteToCollectionIfMissing).toHaveBeenCalledWith(criticiteCollection, ...additionalCriticites);
        expect(comp.criticitesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call PieceJointes query and add missing value', () => {
        const actions: IActions = { id: 456 };
        const pj: IPieceJointes = { id: 12104 };
        actions.pj = pj;

        const pieceJointesCollection: IPieceJointes[] = [{ id: 45147 }];
        jest.spyOn(pieceJointesService, 'query').mockReturnValue(of(new HttpResponse({ body: pieceJointesCollection })));
        const additionalPieceJointes = [pj];
        const expectedCollection: IPieceJointes[] = [...additionalPieceJointes, ...pieceJointesCollection];
        jest.spyOn(pieceJointesService, 'addPieceJointesToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        expect(pieceJointesService.query).toHaveBeenCalled();
        expect(pieceJointesService.addPieceJointesToCollectionIfMissing).toHaveBeenCalledWith(
          pieceJointesCollection,
          ...additionalPieceJointes
        );
        expect(comp.pieceJointesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const actions: IActions = { id: 456 };
        const rapport: IRapport = { id: 16714 };
        actions.rapport = rapport;
        const correctPrevent: ICorrectPrevent = { id: 13830 };
        actions.correctPrevent = correctPrevent;
        const priorite: IPriorite = { id: 12373 };
        actions.priorite = priorite;
        const etapeValidation: IEtapeValidation = { id: 90946 };
        actions.etapeValidation = etapeValidation;
        const typeAction: ITypeAction = { id: 47723 };
        actions.typeAction = typeAction;
        const criticite: ICriticite = { id: 18058 };
        actions.criticite = criticite;
        const pj: IPieceJointes = { id: 29239 };
        actions.pj = pj;

        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(actions));
        expect(comp.rapportsSharedCollection).toContain(rapport);
        expect(comp.correctPreventsSharedCollection).toContain(correctPrevent);
        expect(comp.prioritesSharedCollection).toContain(priorite);
        expect(comp.etapeValidationsSharedCollection).toContain(etapeValidation);
        expect(comp.typeActionsSharedCollection).toContain(typeAction);
        expect(comp.criticitesSharedCollection).toContain(criticite);
        expect(comp.pieceJointesSharedCollection).toContain(pj);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Actions>>();
        const actions = { id: 123 };
        jest.spyOn(actionsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: actions }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(actionsService.update).toHaveBeenCalledWith(actions);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Actions>>();
        const actions = new Actions();
        jest.spyOn(actionsService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: actions }));
        saveSubject.complete();

        // THEN
        expect(actionsService.create).toHaveBeenCalledWith(actions);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Actions>>();
        const actions = { id: 123 };
        jest.spyOn(actionsService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ actions });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(actionsService.update).toHaveBeenCalledWith(actions);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackRapportById', () => {
        it('Should return tracked Rapport primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRapportById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCorrectPreventById', () => {
        it('Should return tracked CorrectPrevent primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCorrectPreventById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackPrioriteById', () => {
        it('Should return tracked Priorite primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPrioriteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEtapeValidationById', () => {
        it('Should return tracked EtapeValidation primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEtapeValidationById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackTypeActionById', () => {
        it('Should return tracked TypeAction primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTypeActionById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCriticiteById', () => {
        it('Should return tracked Criticite primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCriticiteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackPieceJointesById', () => {
        it('Should return tracked PieceJointes primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPieceJointesById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
