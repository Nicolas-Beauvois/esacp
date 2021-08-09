import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITypeAt, TypeAt } from '../type-at.model';

import { TypeAtService } from './type-at.service';

describe('Service Tests', () => {
  describe('TypeAt Service', () => {
    let service: TypeAtService;
    let httpMock: HttpTestingController;
    let elemDefault: ITypeAt;
    let expectedResult: ITypeAt | ITypeAt[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TypeAtService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        typeAt: 'AAAAAAA',
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

      it('should create a TypeAt', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TypeAt()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TypeAt', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeAt: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a TypeAt', () => {
        const patchObject = Object.assign(
          {
            typeAt: 'BBBBBB',
          },
          new TypeAt()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TypeAt', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeAt: 'BBBBBB',
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

      it('should delete a TypeAt', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTypeAtToCollectionIfMissing', () => {
        it('should add a TypeAt to an empty array', () => {
          const typeAt: ITypeAt = { id: 123 };
          expectedResult = service.addTypeAtToCollectionIfMissing([], typeAt);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(typeAt);
        });

        it('should not add a TypeAt to an array that contains it', () => {
          const typeAt: ITypeAt = { id: 123 };
          const typeAtCollection: ITypeAt[] = [
            {
              ...typeAt,
            },
            { id: 456 },
          ];
          expectedResult = service.addTypeAtToCollectionIfMissing(typeAtCollection, typeAt);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TypeAt to an array that doesn't contain it", () => {
          const typeAt: ITypeAt = { id: 123 };
          const typeAtCollection: ITypeAt[] = [{ id: 456 }];
          expectedResult = service.addTypeAtToCollectionIfMissing(typeAtCollection, typeAt);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(typeAt);
        });

        it('should add only unique TypeAt to an array', () => {
          const typeAtArray: ITypeAt[] = [{ id: 123 }, { id: 456 }, { id: 54035 }];
          const typeAtCollection: ITypeAt[] = [{ id: 123 }];
          expectedResult = service.addTypeAtToCollectionIfMissing(typeAtCollection, ...typeAtArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const typeAt: ITypeAt = { id: 123 };
          const typeAt2: ITypeAt = { id: 456 };
          expectedResult = service.addTypeAtToCollectionIfMissing([], typeAt, typeAt2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(typeAt);
          expect(expectedResult).toContain(typeAt2);
        });

        it('should accept null and undefined values', () => {
          const typeAt: ITypeAt = { id: 123 };
          expectedResult = service.addTypeAtToCollectionIfMissing([], null, typeAt, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(typeAt);
        });

        it('should return initial array if no TypeAt is added', () => {
          const typeAtCollection: ITypeAt[] = [{ id: 123 }];
          expectedResult = service.addTypeAtToCollectionIfMissing(typeAtCollection, undefined, null);
          expect(expectedResult).toEqual(typeAtCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
