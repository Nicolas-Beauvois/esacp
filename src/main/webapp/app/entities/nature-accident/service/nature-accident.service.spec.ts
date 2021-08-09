import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INatureAccident, NatureAccident } from '../nature-accident.model';

import { NatureAccidentService } from './nature-accident.service';

describe('Service Tests', () => {
  describe('NatureAccident Service', () => {
    let service: NatureAccidentService;
    let httpMock: HttpTestingController;
    let elemDefault: INatureAccident;
    let expectedResult: INatureAccident | INatureAccident[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NatureAccidentService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        typeNatureAccident: 'AAAAAAA',
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

      it('should create a NatureAccident', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new NatureAccident()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NatureAccident', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeNatureAccident: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a NatureAccident', () => {
        const patchObject = Object.assign({}, new NatureAccident());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of NatureAccident', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeNatureAccident: 'BBBBBB',
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

      it('should delete a NatureAccident', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNatureAccidentToCollectionIfMissing', () => {
        it('should add a NatureAccident to an empty array', () => {
          const natureAccident: INatureAccident = { id: 123 };
          expectedResult = service.addNatureAccidentToCollectionIfMissing([], natureAccident);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(natureAccident);
        });

        it('should not add a NatureAccident to an array that contains it', () => {
          const natureAccident: INatureAccident = { id: 123 };
          const natureAccidentCollection: INatureAccident[] = [
            {
              ...natureAccident,
            },
            { id: 456 },
          ];
          expectedResult = service.addNatureAccidentToCollectionIfMissing(natureAccidentCollection, natureAccident);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a NatureAccident to an array that doesn't contain it", () => {
          const natureAccident: INatureAccident = { id: 123 };
          const natureAccidentCollection: INatureAccident[] = [{ id: 456 }];
          expectedResult = service.addNatureAccidentToCollectionIfMissing(natureAccidentCollection, natureAccident);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(natureAccident);
        });

        it('should add only unique NatureAccident to an array', () => {
          const natureAccidentArray: INatureAccident[] = [{ id: 123 }, { id: 456 }, { id: 2862 }];
          const natureAccidentCollection: INatureAccident[] = [{ id: 123 }];
          expectedResult = service.addNatureAccidentToCollectionIfMissing(natureAccidentCollection, ...natureAccidentArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const natureAccident: INatureAccident = { id: 123 };
          const natureAccident2: INatureAccident = { id: 456 };
          expectedResult = service.addNatureAccidentToCollectionIfMissing([], natureAccident, natureAccident2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(natureAccident);
          expect(expectedResult).toContain(natureAccident2);
        });

        it('should accept null and undefined values', () => {
          const natureAccident: INatureAccident = { id: 123 };
          expectedResult = service.addNatureAccidentToCollectionIfMissing([], null, natureAccident, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(natureAccident);
        });

        it('should return initial array if no NatureAccident is added', () => {
          const natureAccidentCollection: INatureAccident[] = [{ id: 123 }];
          expectedResult = service.addNatureAccidentToCollectionIfMissing(natureAccidentCollection, undefined, null);
          expect(expectedResult).toEqual(natureAccidentCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
