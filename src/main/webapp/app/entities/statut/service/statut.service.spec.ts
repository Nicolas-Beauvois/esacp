import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStatut, Statut } from '../statut.model';

import { StatutService } from './statut.service';

describe('Service Tests', () => {
  describe('Statut Service', () => {
    let service: StatutService;
    let httpMock: HttpTestingController;
    let elemDefault: IStatut;
    let expectedResult: IStatut | IStatut[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(StatutService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        statut: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Statut', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Statut()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Statut', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            statut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Statut', () => {
        const patchObject = Object.assign({}, new Statut());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Statut', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            statut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Statut', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addStatutToCollectionIfMissing', () => {
        it('should add a Statut to an empty array', () => {
          const statut: IStatut = { id: 123 };
          expectedResult = service.addStatutToCollectionIfMissing([], statut);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(statut);
        });

        it('should not add a Statut to an array that contains it', () => {
          const statut: IStatut = { id: 123 };
          const statutCollection: IStatut[] = [
            {
              ...statut,
            },
            { id: 456 },
          ];
          expectedResult = service.addStatutToCollectionIfMissing(statutCollection, statut);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Statut to an array that doesn't contain it", () => {
          const statut: IStatut = { id: 123 };
          const statutCollection: IStatut[] = [{ id: 456 }];
          expectedResult = service.addStatutToCollectionIfMissing(statutCollection, statut);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(statut);
        });

        it('should add only unique Statut to an array', () => {
          const statutArray: IStatut[] = [{ id: 123 }, { id: 456 }, { id: 9667 }];
          const statutCollection: IStatut[] = [{ id: 123 }];
          expectedResult = service.addStatutToCollectionIfMissing(statutCollection, ...statutArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const statut: IStatut = { id: 123 };
          const statut2: IStatut = { id: 456 };
          expectedResult = service.addStatutToCollectionIfMissing([], statut, statut2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(statut);
          expect(expectedResult).toContain(statut2);
        });

        it('should accept null and undefined values', () => {
          const statut: IStatut = { id: 123 };
          expectedResult = service.addStatutToCollectionIfMissing([], null, statut, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(statut);
        });

        it('should return initial array if no Statut is added', () => {
          const statutCollection: IStatut[] = [{ id: 123 }];
          expectedResult = service.addStatutToCollectionIfMissing(statutCollection, undefined, null);
          expect(expectedResult).toEqual(statutCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
