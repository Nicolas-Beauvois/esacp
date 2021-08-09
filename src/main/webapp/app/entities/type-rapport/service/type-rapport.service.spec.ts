import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITypeRapport, TypeRapport } from '../type-rapport.model';

import { TypeRapportService } from './type-rapport.service';

describe('Service Tests', () => {
  describe('TypeRapport Service', () => {
    let service: TypeRapportService;
    let httpMock: HttpTestingController;
    let elemDefault: ITypeRapport;
    let expectedResult: ITypeRapport | ITypeRapport[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TypeRapportService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        typeRapport: 'AAAAAAA',
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

      it('should create a TypeRapport', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TypeRapport()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TypeRapport', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeRapport: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a TypeRapport', () => {
        const patchObject = Object.assign(
          {
            typeRapport: 'BBBBBB',
          },
          new TypeRapport()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TypeRapport', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeRapport: 'BBBBBB',
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

      it('should delete a TypeRapport', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTypeRapportToCollectionIfMissing', () => {
        it('should add a TypeRapport to an empty array', () => {
          const typeRapport: ITypeRapport = { id: 123 };
          expectedResult = service.addTypeRapportToCollectionIfMissing([], typeRapport);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(typeRapport);
        });

        it('should not add a TypeRapport to an array that contains it', () => {
          const typeRapport: ITypeRapport = { id: 123 };
          const typeRapportCollection: ITypeRapport[] = [
            {
              ...typeRapport,
            },
            { id: 456 },
          ];
          expectedResult = service.addTypeRapportToCollectionIfMissing(typeRapportCollection, typeRapport);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TypeRapport to an array that doesn't contain it", () => {
          const typeRapport: ITypeRapport = { id: 123 };
          const typeRapportCollection: ITypeRapport[] = [{ id: 456 }];
          expectedResult = service.addTypeRapportToCollectionIfMissing(typeRapportCollection, typeRapport);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(typeRapport);
        });

        it('should add only unique TypeRapport to an array', () => {
          const typeRapportArray: ITypeRapport[] = [{ id: 123 }, { id: 456 }, { id: 32135 }];
          const typeRapportCollection: ITypeRapport[] = [{ id: 123 }];
          expectedResult = service.addTypeRapportToCollectionIfMissing(typeRapportCollection, ...typeRapportArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const typeRapport: ITypeRapport = { id: 123 };
          const typeRapport2: ITypeRapport = { id: 456 };
          expectedResult = service.addTypeRapportToCollectionIfMissing([], typeRapport, typeRapport2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(typeRapport);
          expect(expectedResult).toContain(typeRapport2);
        });

        it('should accept null and undefined values', () => {
          const typeRapport: ITypeRapport = { id: 123 };
          expectedResult = service.addTypeRapportToCollectionIfMissing([], null, typeRapport, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(typeRapport);
        });

        it('should return initial array if no TypeRapport is added', () => {
          const typeRapportCollection: ITypeRapport[] = [{ id: 123 }];
          expectedResult = service.addTypeRapportToCollectionIfMissing(typeRapportCollection, undefined, null);
          expect(expectedResult).toEqual(typeRapportCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
