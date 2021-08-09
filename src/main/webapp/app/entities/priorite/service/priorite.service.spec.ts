import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPriorite, Priorite } from '../priorite.model';

import { PrioriteService } from './priorite.service';

describe('Service Tests', () => {
  describe('Priorite Service', () => {
    let service: PrioriteService;
    let httpMock: HttpTestingController;
    let elemDefault: IPriorite;
    let expectedResult: IPriorite | IPriorite[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PrioriteService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        priorite: 'AAAAAAA',
        accrPriorite: 'AAAAAAA',
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

      it('should create a Priorite', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Priorite()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Priorite', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            priorite: 'BBBBBB',
            accrPriorite: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Priorite', () => {
        const patchObject = Object.assign(
          {
            priorite: 'BBBBBB',
            accrPriorite: 'BBBBBB',
          },
          new Priorite()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Priorite', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            priorite: 'BBBBBB',
            accrPriorite: 'BBBBBB',
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

      it('should delete a Priorite', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPrioriteToCollectionIfMissing', () => {
        it('should add a Priorite to an empty array', () => {
          const priorite: IPriorite = { id: 123 };
          expectedResult = service.addPrioriteToCollectionIfMissing([], priorite);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(priorite);
        });

        it('should not add a Priorite to an array that contains it', () => {
          const priorite: IPriorite = { id: 123 };
          const prioriteCollection: IPriorite[] = [
            {
              ...priorite,
            },
            { id: 456 },
          ];
          expectedResult = service.addPrioriteToCollectionIfMissing(prioriteCollection, priorite);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Priorite to an array that doesn't contain it", () => {
          const priorite: IPriorite = { id: 123 };
          const prioriteCollection: IPriorite[] = [{ id: 456 }];
          expectedResult = service.addPrioriteToCollectionIfMissing(prioriteCollection, priorite);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(priorite);
        });

        it('should add only unique Priorite to an array', () => {
          const prioriteArray: IPriorite[] = [{ id: 123 }, { id: 456 }, { id: 20357 }];
          const prioriteCollection: IPriorite[] = [{ id: 123 }];
          expectedResult = service.addPrioriteToCollectionIfMissing(prioriteCollection, ...prioriteArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const priorite: IPriorite = { id: 123 };
          const priorite2: IPriorite = { id: 456 };
          expectedResult = service.addPrioriteToCollectionIfMissing([], priorite, priorite2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(priorite);
          expect(expectedResult).toContain(priorite2);
        });

        it('should accept null and undefined values', () => {
          const priorite: IPriorite = { id: 123 };
          expectedResult = service.addPrioriteToCollectionIfMissing([], null, priorite, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(priorite);
        });

        it('should return initial array if no Priorite is added', () => {
          const prioriteCollection: IPriorite[] = [{ id: 123 }];
          expectedResult = service.addPrioriteToCollectionIfMissing(prioriteCollection, undefined, null);
          expect(expectedResult).toEqual(prioriteCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
