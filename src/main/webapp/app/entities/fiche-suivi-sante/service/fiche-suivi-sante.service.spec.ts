import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFicheSuiviSante, FicheSuiviSante } from '../fiche-suivi-sante.model';

import { FicheSuiviSanteService } from './fiche-suivi-sante.service';

describe('Service Tests', () => {
  describe('FicheSuiviSante Service', () => {
    let service: FicheSuiviSanteService;
    let httpMock: HttpTestingController;
    let elemDefault: IFicheSuiviSante;
    let expectedResult: IFicheSuiviSante | IFicheSuiviSante[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FicheSuiviSanteService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        suiviIndividuel: false,
        medecinDuTravail: 'AAAAAAA',
        dateDeCreation: currentDate,
        datededebutAT: currentDate,
        datedefinAT: currentDate,
        propositionMedecinDuTravail: 'AAAAAAA',
        aRevoirLe: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateDeCreation: currentDate.format(DATE_FORMAT),
            datededebutAT: currentDate.format(DATE_FORMAT),
            datedefinAT: currentDate.format(DATE_FORMAT),
            aRevoirLe: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FicheSuiviSante', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateDeCreation: currentDate.format(DATE_FORMAT),
            datededebutAT: currentDate.format(DATE_FORMAT),
            datedefinAT: currentDate.format(DATE_FORMAT),
            aRevoirLe: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeCreation: currentDate,
            datededebutAT: currentDate,
            datedefinAT: currentDate,
            aRevoirLe: currentDate,
          },
          returnedFromService
        );

        service.create(new FicheSuiviSante()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FicheSuiviSante', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            suiviIndividuel: true,
            medecinDuTravail: 'BBBBBB',
            dateDeCreation: currentDate.format(DATE_FORMAT),
            datededebutAT: currentDate.format(DATE_FORMAT),
            datedefinAT: currentDate.format(DATE_FORMAT),
            propositionMedecinDuTravail: 'BBBBBB',
            aRevoirLe: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeCreation: currentDate,
            datededebutAT: currentDate,
            datedefinAT: currentDate,
            aRevoirLe: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FicheSuiviSante', () => {
        const patchObject = Object.assign(
          {
            medecinDuTravail: 'BBBBBB',
            aRevoirLe: currentDate.format(DATE_FORMAT),
          },
          new FicheSuiviSante()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDeCreation: currentDate,
            datededebutAT: currentDate,
            datedefinAT: currentDate,
            aRevoirLe: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FicheSuiviSante', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            suiviIndividuel: true,
            medecinDuTravail: 'BBBBBB',
            dateDeCreation: currentDate.format(DATE_FORMAT),
            datededebutAT: currentDate.format(DATE_FORMAT),
            datedefinAT: currentDate.format(DATE_FORMAT),
            propositionMedecinDuTravail: 'BBBBBB',
            aRevoirLe: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeCreation: currentDate,
            datededebutAT: currentDate,
            datedefinAT: currentDate,
            aRevoirLe: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FicheSuiviSante', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFicheSuiviSanteToCollectionIfMissing', () => {
        it('should add a FicheSuiviSante to an empty array', () => {
          const ficheSuiviSante: IFicheSuiviSante = { id: 123 };
          expectedResult = service.addFicheSuiviSanteToCollectionIfMissing([], ficheSuiviSante);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ficheSuiviSante);
        });

        it('should not add a FicheSuiviSante to an array that contains it', () => {
          const ficheSuiviSante: IFicheSuiviSante = { id: 123 };
          const ficheSuiviSanteCollection: IFicheSuiviSante[] = [
            {
              ...ficheSuiviSante,
            },
            { id: 456 },
          ];
          expectedResult = service.addFicheSuiviSanteToCollectionIfMissing(ficheSuiviSanteCollection, ficheSuiviSante);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FicheSuiviSante to an array that doesn't contain it", () => {
          const ficheSuiviSante: IFicheSuiviSante = { id: 123 };
          const ficheSuiviSanteCollection: IFicheSuiviSante[] = [{ id: 456 }];
          expectedResult = service.addFicheSuiviSanteToCollectionIfMissing(ficheSuiviSanteCollection, ficheSuiviSante);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ficheSuiviSante);
        });

        it('should add only unique FicheSuiviSante to an array', () => {
          const ficheSuiviSanteArray: IFicheSuiviSante[] = [{ id: 123 }, { id: 456 }, { id: 76947 }];
          const ficheSuiviSanteCollection: IFicheSuiviSante[] = [{ id: 123 }];
          expectedResult = service.addFicheSuiviSanteToCollectionIfMissing(ficheSuiviSanteCollection, ...ficheSuiviSanteArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const ficheSuiviSante: IFicheSuiviSante = { id: 123 };
          const ficheSuiviSante2: IFicheSuiviSante = { id: 456 };
          expectedResult = service.addFicheSuiviSanteToCollectionIfMissing([], ficheSuiviSante, ficheSuiviSante2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(ficheSuiviSante);
          expect(expectedResult).toContain(ficheSuiviSante2);
        });

        it('should accept null and undefined values', () => {
          const ficheSuiviSante: IFicheSuiviSante = { id: 123 };
          expectedResult = service.addFicheSuiviSanteToCollectionIfMissing([], null, ficheSuiviSante, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(ficheSuiviSante);
        });

        it('should return initial array if no FicheSuiviSante is added', () => {
          const ficheSuiviSanteCollection: IFicheSuiviSante[] = [{ id: 123 }];
          expectedResult = service.addFicheSuiviSanteToCollectionIfMissing(ficheSuiviSanteCollection, undefined, null);
          expect(expectedResult).toEqual(ficheSuiviSanteCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
