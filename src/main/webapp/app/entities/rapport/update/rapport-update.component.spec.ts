jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RapportService } from '../service/rapport.service';
import { IRapport, Rapport } from '../rapport.model';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { ISiegeLesions } from 'app/entities/siege-lesions/siege-lesions.model';
import { SiegeLesionsService } from 'app/entities/siege-lesions/service/siege-lesions.service';
import { IFicheSuiviSante } from 'app/entities/fiche-suivi-sante/fiche-suivi-sante.model';
import { FicheSuiviSanteService } from 'app/entities/fiche-suivi-sante/service/fiche-suivi-sante.service';
import { IType } from 'app/entities/type/type.model';
import { TypeService } from 'app/entities/type/service/type.service';
import { ICategorie } from 'app/entities/categorie/categorie.model';
import { CategorieService } from 'app/entities/categorie/service/categorie.service';
import { IEquipement } from 'app/entities/equipement/equipement.model';
import { EquipementService } from 'app/entities/equipement/service/equipement.service';
import { ITypeRapport } from 'app/entities/type-rapport/type-rapport.model';
import { TypeRapportService } from 'app/entities/type-rapport/service/type-rapport.service';
import { INatureAccident } from 'app/entities/nature-accident/nature-accident.model';
import { NatureAccidentService } from 'app/entities/nature-accident/service/nature-accident.service';
import { IOrigineLesions } from 'app/entities/origine-lesions/origine-lesions.model';
import { OrigineLesionsService } from 'app/entities/origine-lesions/service/origine-lesions.service';

import { RapportUpdateComponent } from './rapport-update.component';

describe('Component Tests', () => {
  describe('Rapport Management Update Component', () => {
    let comp: RapportUpdateComponent;
    let fixture: ComponentFixture<RapportUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let rapportService: RapportService;
    let userService: UserService;
    let siegeLesionsService: SiegeLesionsService;
    let ficheSuiviSanteService: FicheSuiviSanteService;
    let typeService: TypeService;
    let categorieService: CategorieService;
    let equipementService: EquipementService;
    let typeRapportService: TypeRapportService;
    let natureAccidentService: NatureAccidentService;
    let origineLesionsService: OrigineLesionsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RapportUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RapportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RapportUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      rapportService = TestBed.inject(RapportService);
      userService = TestBed.inject(UserService);
      siegeLesionsService = TestBed.inject(SiegeLesionsService);
      ficheSuiviSanteService = TestBed.inject(FicheSuiviSanteService);
      typeService = TestBed.inject(TypeService);
      categorieService = TestBed.inject(CategorieService);
      equipementService = TestBed.inject(EquipementService);
      typeRapportService = TestBed.inject(TypeRapportService);
      natureAccidentService = TestBed.inject(NatureAccidentService);
      origineLesionsService = TestBed.inject(OrigineLesionsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call User query and add missing value', () => {
        const rapport: IRapport = { id: 456 };
        const user: IUser = { id: 7233 };
        rapport.user = user;

        const userCollection: IUser[] = [{ id: 23209 }];
        jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
        const additionalUsers = [user];
        const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
        jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(userService.query).toHaveBeenCalled();
        expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(userCollection, ...additionalUsers);
        expect(comp.usersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call SiegeLesions query and add missing value', () => {
        const rapport: IRapport = { id: 456 };
        const siegeLesions: ISiegeLesions = { id: 65905 };
        rapport.siegeLesions = siegeLesions;

        const siegeLesionsCollection: ISiegeLesions[] = [{ id: 37172 }];
        jest.spyOn(siegeLesionsService, 'query').mockReturnValue(of(new HttpResponse({ body: siegeLesionsCollection })));
        const additionalSiegeLesions = [siegeLesions];
        const expectedCollection: ISiegeLesions[] = [...additionalSiegeLesions, ...siegeLesionsCollection];
        jest.spyOn(siegeLesionsService, 'addSiegeLesionsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(siegeLesionsService.query).toHaveBeenCalled();
        expect(siegeLesionsService.addSiegeLesionsToCollectionIfMissing).toHaveBeenCalledWith(
          siegeLesionsCollection,
          ...additionalSiegeLesions
        );
        expect(comp.siegeLesionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call FicheSuiviSante query and add missing value', () => {
        const rapport: IRapport = { id: 456 };
        const ficheSuiviSante: IFicheSuiviSante = { id: 90935 };
        rapport.ficheSuiviSante = ficheSuiviSante;

        const ficheSuiviSanteCollection: IFicheSuiviSante[] = [{ id: 66047 }];
        jest.spyOn(ficheSuiviSanteService, 'query').mockReturnValue(of(new HttpResponse({ body: ficheSuiviSanteCollection })));
        const additionalFicheSuiviSantes = [ficheSuiviSante];
        const expectedCollection: IFicheSuiviSante[] = [...additionalFicheSuiviSantes, ...ficheSuiviSanteCollection];
        jest.spyOn(ficheSuiviSanteService, 'addFicheSuiviSanteToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(ficheSuiviSanteService.query).toHaveBeenCalled();
        expect(ficheSuiviSanteService.addFicheSuiviSanteToCollectionIfMissing).toHaveBeenCalledWith(
          ficheSuiviSanteCollection,
          ...additionalFicheSuiviSantes
        );
        expect(comp.ficheSuiviSantesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Type query and add missing value', () => {
        const rapport: IRapport = { id: 456 };
        const type: IType = { id: 9197 };
        rapport.type = type;

        const typeCollection: IType[] = [{ id: 13244 }];
        jest.spyOn(typeService, 'query').mockReturnValue(of(new HttpResponse({ body: typeCollection })));
        const additionalTypes = [type];
        const expectedCollection: IType[] = [...additionalTypes, ...typeCollection];
        jest.spyOn(typeService, 'addTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(typeService.query).toHaveBeenCalled();
        expect(typeService.addTypeToCollectionIfMissing).toHaveBeenCalledWith(typeCollection, ...additionalTypes);
        expect(comp.typesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Categorie query and add missing value', () => {
        const rapport: IRapport = { id: 456 };
        const categorie: ICategorie = { id: 52984 };
        rapport.categorie = categorie;

        const categorieCollection: ICategorie[] = [{ id: 81705 }];
        jest.spyOn(categorieService, 'query').mockReturnValue(of(new HttpResponse({ body: categorieCollection })));
        const additionalCategories = [categorie];
        const expectedCollection: ICategorie[] = [...additionalCategories, ...categorieCollection];
        jest.spyOn(categorieService, 'addCategorieToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(categorieService.query).toHaveBeenCalled();
        expect(categorieService.addCategorieToCollectionIfMissing).toHaveBeenCalledWith(categorieCollection, ...additionalCategories);
        expect(comp.categoriesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Equipement query and add missing value', () => {
        const rapport: IRapport = { id: 456 };
        const equipement: IEquipement = { id: 37566 };
        rapport.equipement = equipement;

        const equipementCollection: IEquipement[] = [{ id: 41356 }];
        jest.spyOn(equipementService, 'query').mockReturnValue(of(new HttpResponse({ body: equipementCollection })));
        const additionalEquipements = [equipement];
        const expectedCollection: IEquipement[] = [...additionalEquipements, ...equipementCollection];
        jest.spyOn(equipementService, 'addEquipementToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(equipementService.query).toHaveBeenCalled();
        expect(equipementService.addEquipementToCollectionIfMissing).toHaveBeenCalledWith(equipementCollection, ...additionalEquipements);
        expect(comp.equipementsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call TypeRapport query and add missing value', () => {
        const rapport: IRapport = { id: 456 };
        const typeRapport: ITypeRapport = { id: 13854 };
        rapport.typeRapport = typeRapport;

        const typeRapportCollection: ITypeRapport[] = [{ id: 39880 }];
        jest.spyOn(typeRapportService, 'query').mockReturnValue(of(new HttpResponse({ body: typeRapportCollection })));
        const additionalTypeRapports = [typeRapport];
        const expectedCollection: ITypeRapport[] = [...additionalTypeRapports, ...typeRapportCollection];
        jest.spyOn(typeRapportService, 'addTypeRapportToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(typeRapportService.query).toHaveBeenCalled();
        expect(typeRapportService.addTypeRapportToCollectionIfMissing).toHaveBeenCalledWith(
          typeRapportCollection,
          ...additionalTypeRapports
        );
        expect(comp.typeRapportsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call NatureAccident query and add missing value', () => {
        const rapport: IRapport = { id: 456 };
        const natureAccident: INatureAccident = { id: 80951 };
        rapport.natureAccident = natureAccident;

        const natureAccidentCollection: INatureAccident[] = [{ id: 95773 }];
        jest.spyOn(natureAccidentService, 'query').mockReturnValue(of(new HttpResponse({ body: natureAccidentCollection })));
        const additionalNatureAccidents = [natureAccident];
        const expectedCollection: INatureAccident[] = [...additionalNatureAccidents, ...natureAccidentCollection];
        jest.spyOn(natureAccidentService, 'addNatureAccidentToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(natureAccidentService.query).toHaveBeenCalled();
        expect(natureAccidentService.addNatureAccidentToCollectionIfMissing).toHaveBeenCalledWith(
          natureAccidentCollection,
          ...additionalNatureAccidents
        );
        expect(comp.natureAccidentsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call OrigineLesions query and add missing value', () => {
        const rapport: IRapport = { id: 456 };
        const origineLesions: IOrigineLesions = { id: 56132 };
        rapport.origineLesions = origineLesions;

        const origineLesionsCollection: IOrigineLesions[] = [{ id: 31959 }];
        jest.spyOn(origineLesionsService, 'query').mockReturnValue(of(new HttpResponse({ body: origineLesionsCollection })));
        const additionalOrigineLesions = [origineLesions];
        const expectedCollection: IOrigineLesions[] = [...additionalOrigineLesions, ...origineLesionsCollection];
        jest.spyOn(origineLesionsService, 'addOrigineLesionsToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(origineLesionsService.query).toHaveBeenCalled();
        expect(origineLesionsService.addOrigineLesionsToCollectionIfMissing).toHaveBeenCalledWith(
          origineLesionsCollection,
          ...additionalOrigineLesions
        );
        expect(comp.origineLesionsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const rapport: IRapport = { id: 456 };
        const user: IUser = { id: 5350 };
        rapport.user = user;
        const siegeLesions: ISiegeLesions = { id: 77468 };
        rapport.siegeLesions = siegeLesions;
        const ficheSuiviSante: IFicheSuiviSante = { id: 44813 };
        rapport.ficheSuiviSante = ficheSuiviSante;
        const type: IType = { id: 38576 };
        rapport.type = type;
        const categorie: ICategorie = { id: 1677 };
        rapport.categorie = categorie;
        const equipement: IEquipement = { id: 27549 };
        rapport.equipement = equipement;
        const typeRapport: ITypeRapport = { id: 52180 };
        rapport.typeRapport = typeRapport;
        const natureAccident: INatureAccident = { id: 85796 };
        rapport.natureAccident = natureAccident;
        const origineLesions: IOrigineLesions = { id: 32571 };
        rapport.origineLesions = origineLesions;

        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(rapport));
        expect(comp.usersSharedCollection).toContain(user);
        expect(comp.siegeLesionsSharedCollection).toContain(siegeLesions);
        expect(comp.ficheSuiviSantesSharedCollection).toContain(ficheSuiviSante);
        expect(comp.typesSharedCollection).toContain(type);
        expect(comp.categoriesSharedCollection).toContain(categorie);
        expect(comp.equipementsSharedCollection).toContain(equipement);
        expect(comp.typeRapportsSharedCollection).toContain(typeRapport);
        expect(comp.natureAccidentsSharedCollection).toContain(natureAccident);
        expect(comp.origineLesionsSharedCollection).toContain(origineLesions);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rapport>>();
        const rapport = { id: 123 };
        jest.spyOn(rapportService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rapport }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(rapportService.update).toHaveBeenCalledWith(rapport);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rapport>>();
        const rapport = new Rapport();
        jest.spyOn(rapportService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: rapport }));
        saveSubject.complete();

        // THEN
        expect(rapportService.create).toHaveBeenCalledWith(rapport);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Rapport>>();
        const rapport = { id: 123 };
        jest.spyOn(rapportService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ rapport });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(rapportService.update).toHaveBeenCalledWith(rapport);
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

      describe('trackSiegeLesionsById', () => {
        it('Should return tracked SiegeLesions primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSiegeLesionsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackFicheSuiviSanteById', () => {
        it('Should return tracked FicheSuiviSante primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFicheSuiviSanteById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackTypeById', () => {
        it('Should return tracked Type primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTypeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackCategorieById', () => {
        it('Should return tracked Categorie primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategorieById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEquipementById', () => {
        it('Should return tracked Equipement primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEquipementById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackTypeRapportById', () => {
        it('Should return tracked TypeRapport primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTypeRapportById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackNatureAccidentById', () => {
        it('Should return tracked NatureAccident primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackNatureAccidentById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackOrigineLesionsById', () => {
        it('Should return tracked OrigineLesions primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackOrigineLesionsById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
