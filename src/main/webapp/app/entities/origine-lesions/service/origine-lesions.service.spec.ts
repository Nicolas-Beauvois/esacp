import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrigineLesions, OrigineLesions } from '../origine-lesions.model';

import { OrigineLesionsService } from './origine-lesions.service';

describe('Service Tests', () => {
  describe('OrigineLesions Service', () => {
    let service: OrigineLesionsService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrigineLesions;
    let expectedResult: IOrigineLesions | IOrigineLesions[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(OrigineLesionsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        origineLesions: 'AAAAAAA',
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

      it('should create a OrigineLesions', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new OrigineLesions()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OrigineLesions', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            origineLesions: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a OrigineLesions', () => {
        const patchObject = Object.assign({}, new OrigineLesions());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of OrigineLesions', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            origineLesions: 'BBBBBB',
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

      it('should delete a OrigineLesions', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addOrigineLesionsToCollectionIfMissing', () => {
        it('should add a OrigineLesions to an empty array', () => {
          const origineLesions: IOrigineLesions = { id: 123 };
          expectedResult = service.addOrigineLesionsToCollectionIfMissing([], origineLesions);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(origineLesions);
        });

        it('should not add a OrigineLesions to an array that contains it', () => {
          const origineLesions: IOrigineLesions = { id: 123 };
          const origineLesionsCollection: IOrigineLesions[] = [
            {
              ...origineLesions,
            },
            { id: 456 },
          ];
          expectedResult = service.addOrigineLesionsToCollectionIfMissing(origineLesionsCollection, origineLesions);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a OrigineLesions to an array that doesn't contain it", () => {
          const origineLesions: IOrigineLesions = { id: 123 };
          const origineLesionsCollection: IOrigineLesions[] = [{ id: 456 }];
          expectedResult = service.addOrigineLesionsToCollectionIfMissing(origineLesionsCollection, origineLesions);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(origineLesions);
        });

        it('should add only unique OrigineLesions to an array', () => {
          const origineLesionsArray: IOrigineLesions[] = [{ id: 123 }, { id: 456 }, { id: 98757 }];
          const origineLesionsCollection: IOrigineLesions[] = [{ id: 123 }];
          expectedResult = service.addOrigineLesionsToCollectionIfMissing(origineLesionsCollection, ...origineLesionsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const origineLesions: IOrigineLesions = { id: 123 };
          const origineLesions2: IOrigineLesions = { id: 456 };
          expectedResult = service.addOrigineLesionsToCollectionIfMissing([], origineLesions, origineLesions2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(origineLesions);
          expect(expectedResult).toContain(origineLesions2);
        });

        it('should accept null and undefined values', () => {
          const origineLesions: IOrigineLesions = { id: 123 };
          expectedResult = service.addOrigineLesionsToCollectionIfMissing([], null, origineLesions, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(origineLesions);
        });

        it('should return initial array if no OrigineLesions is added', () => {
          const origineLesionsCollection: IOrigineLesions[] = [{ id: 123 }];
          expectedResult = service.addOrigineLesionsToCollectionIfMissing(origineLesionsCollection, undefined, null);
          expect(expectedResult).toEqual(origineLesionsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
