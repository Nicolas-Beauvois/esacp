jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { UserExtraService } from '../service/user-extra.service';
import { IUserExtra, UserExtra } from '../user-extra.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IStatut } from 'app/entities/statut/statut.model';
import { StatutService } from 'app/entities/statut/service/statut.service';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';
import { IDepartement } from 'app/entities/departement/departement.model';
import { DepartementService } from 'app/entities/departement/service/departement.service';
import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { ISite } from 'app/entities/site/site.model';
import { SiteService } from 'app/entities/site/service/site.service';
import { ICsp } from 'app/entities/csp/csp.model';
import { CspService } from 'app/entities/csp/service/csp.service';

import { UserExtraUpdateComponent } from './user-extra-update.component';

describe('Component Tests', () => {
  describe('UserExtra Management Update Component', () => {
    let comp: UserExtraUpdateComponent;
    let fixture: ComponentFixture<UserExtraUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let userExtraService: UserExtraService;
    let userService: UserService;
    let statutService: StatutService;
    let sexeService: SexeService;
    let departementService: DepartementService;
    let contratService: ContratService;
    let siteService: SiteService;
    let cspService: CspService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [UserExtraUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(UserExtraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserExtraUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      userExtraService = TestBed.inject(UserExtraService);
      userService = TestBed.inject(UserService);
      statutService = TestBed.inject(StatutService);
      sexeService = TestBed.inject(SexeService);
      departementService = TestBed.inject(DepartementService);
      contratService = TestBed.inject(ContratService);
      siteService = TestBed.inject(SiteService);
      cspService = TestBed.inject(CspService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call User query and add missing value', () => {
        const userExtra: IUserExtra = { id: 456 };
        const user: IUser = { id: 71520 };
        userExtra.user = user;

        const userCollection: IUser[] = [{ id: 72997 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [user];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Statut query and add missing value', () => {
        const userExtra: IUserExtra = { id: 456 };
        const statut: IStatut = { id: 18106 };
        userExtra.statut = statut;

        const statutCollection: IStatut[] = [{ id: 21202 }];
        jest.spyOn(statutService, 'query').mockReturnValue(of(new HttpResponse({ body: statutCollection })));
        const additionalStatuts = [statut];
        const expectedCollection: IStatut[] = [...additionalStatuts, ...statutCollection];
        jest.spyOn(statutService, 'addStatutToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        expect(statutService.query).toHaveBeenCalled();
        expect(statutService.addStatutToCollectionIfMissing).toHaveBeenCalledWith(statutCollection, ...additionalStatuts);
        expect(comp.statutsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Sexe query and add missing value', () => {
        const userExtra: IUserExtra = { id: 456 };
        const sexe: ISexe = { id: 1676 };
        userExtra.sexe = sexe;

        const sexeCollection: ISexe[] = [{ id: 97250 }];
        jest.spyOn(sexeService, 'query').mockReturnValue(of(new HttpResponse({ body: sexeCollection })));
        const additionalSexes = [sexe];
        const expectedCollection: ISexe[] = [...additionalSexes, ...sexeCollection];
        jest.spyOn(sexeService, 'addSexeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        expect(sexeService.query).toHaveBeenCalled();
        expect(sexeService.addSexeToCollectionIfMissing).toHaveBeenCalledWith(sexeCollection, ...additionalSexes);
        expect(comp.sexesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Departement query and add missing value', () => {
        const userExtra: IUserExtra = { id: 456 };
        const departement: IDepartement = { id: 70097 };
        userExtra.departement = departement;

        const departementCollection: IDepartement[] = [{ id: 27399 }];
        jest.spyOn(departementService, 'query').mockReturnValue(of(new HttpResponse({ body: departementCollection })));
        const additionalDepartements = [departement];
        const expectedCollection: IDepartement[] = [...additionalDepartements, ...departementCollection];
        jest.spyOn(departementService, 'addDepartementToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        expect(departementService.query).toHaveBeenCalled();
        expect(departementService.addDepartementToCollectionIfMissing).toHaveBeenCalledWith(
          departementCollection,
          ...additionalDepartements
        );
        expect(comp.departementsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Contrat query and add missing value', () => {
        const userExtra: IUserExtra = { id: 456 };
        const contrat: IContrat = { id: 85305 };
        userExtra.contrat = contrat;

        const contratCollection: IContrat[] = [{ id: 33727 }];
        jest.spyOn(contratService, 'query').mockReturnValue(of(new HttpResponse({ body: contratCollection })));
        const additionalContrats = [contrat];
        const expectedCollection: IContrat[] = [...additionalContrats, ...contratCollection];
        jest.spyOn(contratService, 'addContratToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        expect(contratService.query).toHaveBeenCalled();
        expect(contratService.addContratToCollectionIfMissing).toHaveBeenCalledWith(contratCollection, ...additionalContrats);
        expect(comp.contratsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Site query and add missing value', () => {
        const userExtra: IUserExtra = { id: 456 };
        const site: ISite = { id: 31654 };
        userExtra.site = site;

        const siteCollection: ISite[] = [{ id: 9646 }];
        jest.spyOn(siteService, 'query').mockReturnValue(of(new HttpResponse({ body: siteCollection })));
        const additionalSites = [site];
        const expectedCollection: ISite[] = [...additionalSites, ...siteCollection];
        jest.spyOn(siteService, 'addSiteToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        expect(siteService.query).toHaveBeenCalled();
        expect(siteService.addSiteToCollectionIfMissing).toHaveBeenCalledWith(siteCollection, ...additionalSites);
        expect(comp.sitesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Csp query and add missing value', () => {
        const userExtra: IUserExtra = { id: 456 };
        const csp: ICsp = { id: 17509 };
        userExtra.csp = csp;

        const cspCollection: ICsp[] = [{ id: 26378 }];
        jest.spyOn(cspService, 'query').mockReturnValue(of(new HttpResponse({ body: cspCollection })));
        const additionalCsps = [csp];
        const expectedCollection: ICsp[] = [...additionalCsps, ...cspCollection];
        jest.spyOn(cspService, 'addCspToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        expect(cspService.query).toHaveBeenCalled();
        expect(cspService.addCspToCollectionIfMissing).toHaveBeenCalledWith(cspCollection, ...additionalCsps);
        expect(comp.cspsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const userExtra: IUserExtra = { id: 456 };
        const user: IUser = { id: 98618 };
        userExtra.user = user;
        const statut: IStatut = { id: 36326 };
        userExtra.statut = statut;
        const sexe: ISexe = { id: 37905 };
        userExtra.sexe = sexe;
        const departement: IDepartement = { id: 73170 };
        userExtra.departement = departement;
        const contrat: IContrat = { id: 23481 };
        userExtra.contrat = contrat;
        const site: ISite = { id: 76631 };
        userExtra.site = site;
        const csp: ICsp = { id: 98767 };
        userExtra.csp = csp;

        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(userExtra));
        expect(comp.usersSharedCollection).toContain(user);
        expect(comp.statutsSharedCollection).toContain(statut);
        expect(comp.sexesSharedCollection).toContain(sexe);
        expect(comp.departementsSharedCollection).toContain(departement);
        expect(comp.contratsSharedCollection).toContain(contrat);
        expect(comp.sitesSharedCollection).toContain(site);
        expect(comp.cspsSharedCollection).toContain(csp);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<UserExtra>>();
        const userExtra = { id: 123 };
        jest.spyOn(userExtraService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: userExtra }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(userExtraService.update).toHaveBeenCalledWith(userExtra);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<UserExtra>>();
        const userExtra = new UserExtra();
        jest.spyOn(userExtraService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: userExtra }));
        saveSubject.complete();

        // THEN
        expect(userExtraService.create).toHaveBeenCalledWith(userExtra);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<UserExtra>>();
        const userExtra = { id: 123 };
        jest.spyOn(userExtraService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ userExtra });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(userExtraService.update).toHaveBeenCalledWith(userExtra);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackUserById', () => {
        it('Should return tracked User primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackStatutById', () => {
        it('Should return tracked Statut primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackStatutById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackSexeById', () => {
        it('Should return tracked Sexe primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSexeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackDepartementById', () => {
        it('Should return tracked Departement primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDepartementById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackContratById', () => {
        it('Should return tracked Contrat primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackContratById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackSiteById', () => {
        it('Should return tracked Site primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSiteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCspById', () => {
        it('Should return tracked Csp primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCspById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
