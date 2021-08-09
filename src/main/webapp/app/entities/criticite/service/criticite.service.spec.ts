import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICriticite, Criticite } from '../criticite.model';

import { CriticiteService } from './criticite.service';

describe('Service Tests', () => {
  describe('Criticite Service', () => {
    let service: CriticiteService;
    let httpMock: HttpTestingController;
    let elemDefault: ICriticite;
    let expectedResult: ICriticite | ICriticite[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CriticiteService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        criticite: 'AAAAAAA',
        criticiteAcronyme: 'AAAAAAA',
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

      it('should create a Criticite', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Criticite()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Criticite', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            criticite: 'BBBBBB',
            criticiteAcronyme: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Criticite', () => {
        const patchObject = Object.assign(
          {
            criticite: 'BBBBBB',
            criticiteAcronyme: 'BBBBBB',
          },
          new Criticite()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Criticite', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            criticite: 'BBBBBB',
            criticiteAcronyme: 'BBBBBB',
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

      it('should delete a Criticite', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCriticiteToCollectionIfMissing', () => {
        it('should add a Criticite to an empty array', () => {
          const criticite: ICriticite = { id: 123 };
          expectedResult = service.addCriticiteToCollectionIfMissing([], criticite);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(criticite);
        });

        it('should not add a Criticite to an array that contains it', () => {
          const criticite: ICriticite = { id: 123 };
          const criticiteCollection: ICriticite[] = [
            {
              ...criticite,
            },
            { id: 456 },
          ];
          expectedResult = service.addCriticiteToCollectionIfMissing(criticiteCollection, criticite);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Criticite to an array that doesn't contain it", () => {
          const criticite: ICriticite = { id: 123 };
          const criticiteCollection: ICriticite[] = [{ id: 456 }];
          expectedResult = service.addCriticiteToCollectionIfMissing(criticiteCollection, criticite);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(criticite);
        });

        it('should add only unique Criticite to an array', () => {
          const criticiteArray: ICriticite[] = [{ id: 123 }, { id: 456 }, { id: 61891 }];
          const criticiteCollection: ICriticite[] = [{ id: 123 }];
          expectedResult = service.addCriticiteToCollectionIfMissing(criticiteCollection, ...criticiteArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const criticite: ICriticite = { id: 123 };
          const criticite2: ICriticite = { id: 456 };
          expectedResult = service.addCriticiteToCollectionIfMissing([], criticite, criticite2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(criticite);
          expect(expectedResult).toContain(criticite2);
        });

        it('should accept null and undefined values', () => {
          const criticite: ICriticite = { id: 123 };
          expectedResult = service.addCriticiteToCollectionIfMissing([], null, criticite, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(criticite);
        });

        it('should return initial array if no Criticite is added', () => {
          const criticiteCollection: ICriticite[] = [{ id: 123 }];
          expectedResult = service.addCriticiteToCollectionIfMissing(criticiteCollection, undefined, null);
          expect(expectedResult).toEqual(criticiteCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
