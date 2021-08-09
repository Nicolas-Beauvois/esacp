import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRepartition, Repartition } from '../repartition.model';

import { RepartitionService } from './repartition.service';

describe('Service Tests', () => {
  describe('Repartition Service', () => {
    let service: RepartitionService;
    let httpMock: HttpTestingController;
    let elemDefault: IRepartition;
    let expectedResult: IRepartition | IRepartition[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RepartitionService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        repartition: 'AAAAAAA',
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

      it('should create a Repartition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Repartition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Repartition', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            repartition: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Repartition', () => {
        const patchObject = Object.assign({}, new Repartition());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Repartition', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            repartition: 'BBBBBB',
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

      it('should delete a Repartition', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRepartitionToCollectionIfMissing', () => {
        it('should add a Repartition to an empty array', () => {
          const repartition: IRepartition = { id: 123 };
          expectedResult = service.addRepartitionToCollectionIfMissing([], repartition);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(repartition);
        });

        it('should not add a Repartition to an array that contains it', () => {
          const repartition: IRepartition = { id: 123 };
          const repartitionCollection: IRepartition[] = [
            {
              ...repartition,
            },
            { id: 456 },
          ];
          expectedResult = service.addRepartitionToCollectionIfMissing(repartitionCollection, repartition);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Repartition to an array that doesn't contain it", () => {
          const repartition: IRepartition = { id: 123 };
          const repartitionCollection: IRepartition[] = [{ id: 456 }];
          expectedResult = service.addRepartitionToCollectionIfMissing(repartitionCollection, repartition);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(repartition);
        });

        it('should add only unique Repartition to an array', () => {
          const repartitionArray: IRepartition[] = [{ id: 123 }, { id: 456 }, { id: 74283 }];
          const repartitionCollection: IRepartition[] = [{ id: 123 }];
          expectedResult = service.addRepartitionToCollectionIfMissing(repartitionCollection, ...repartitionArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const repartition: IRepartition = { id: 123 };
          const repartition2: IRepartition = { id: 456 };
          expectedResult = service.addRepartitionToCollectionIfMissing([], repartition, repartition2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(repartition);
          expect(expectedResult).toContain(repartition2);
        });

        it('should accept null and undefined values', () => {
          const repartition: IRepartition = { id: 123 };
          expectedResult = service.addRepartitionToCollectionIfMissing([], null, repartition, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(repartition);
        });

        it('should return initial array if no Repartition is added', () => {
          const repartitionCollection: IRepartition[] = [{ id: 123 }];
          expectedResult = service.addRepartitionToCollectionIfMissing(repartitionCollection, undefined, null);
          expect(expectedResult).toEqual(repartitionCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
