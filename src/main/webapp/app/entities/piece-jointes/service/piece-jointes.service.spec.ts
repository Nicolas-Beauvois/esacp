import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPieceJointes, PieceJointes } from '../piece-jointes.model';

import { PieceJointesService } from './piece-jointes.service';

describe('Service Tests', () => {
  describe('PieceJointes Service', () => {
    let service: PieceJointesService;
    let httpMock: HttpTestingController;
    let elemDefault: IPieceJointes;
    let expectedResult: IPieceJointes | IPieceJointes[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(PieceJointesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        pj: 'AAAAAAA',
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

      it('should create a PieceJointes', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PieceJointes()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PieceJointes', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pj: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a PieceJointes', () => {
        const patchObject = Object.assign(
          {
            pj: 'BBBBBB',
          },
          new PieceJointes()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PieceJointes', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            pj: 'BBBBBB',
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

      it('should delete a PieceJointes', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addPieceJointesToCollectionIfMissing', () => {
        it('should add a PieceJointes to an empty array', () => {
          const pieceJointes: IPieceJointes = { id: 123 };
          expectedResult = service.addPieceJointesToCollectionIfMissing([], pieceJointes);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pieceJointes);
        });

        it('should not add a PieceJointes to an array that contains it', () => {
          const pieceJointes: IPieceJointes = { id: 123 };
          const pieceJointesCollection: IPieceJointes[] = [
            {
              ...pieceJointes,
            },
            { id: 456 },
          ];
          expectedResult = service.addPieceJointesToCollectionIfMissing(pieceJointesCollection, pieceJointes);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a PieceJointes to an array that doesn't contain it", () => {
          const pieceJointes: IPieceJointes = { id: 123 };
          const pieceJointesCollection: IPieceJointes[] = [{ id: 456 }];
          expectedResult = service.addPieceJointesToCollectionIfMissing(pieceJointesCollection, pieceJointes);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pieceJointes);
        });

        it('should add only unique PieceJointes to an array', () => {
          const pieceJointesArray: IPieceJointes[] = [{ id: 123 }, { id: 456 }, { id: 35836 }];
          const pieceJointesCollection: IPieceJointes[] = [{ id: 123 }];
          expectedResult = service.addPieceJointesToCollectionIfMissing(pieceJointesCollection, ...pieceJointesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const pieceJointes: IPieceJointes = { id: 123 };
          const pieceJointes2: IPieceJointes = { id: 456 };
          expectedResult = service.addPieceJointesToCollectionIfMissing([], pieceJointes, pieceJointes2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(pieceJointes);
          expect(expectedResult).toContain(pieceJointes2);
        });

        it('should accept null and undefined values', () => {
          const pieceJointes: IPieceJointes = { id: 123 };
          expectedResult = service.addPieceJointesToCollectionIfMissing([], null, pieceJointes, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(pieceJointes);
        });

        it('should return initial array if no PieceJointes is added', () => {
          const pieceJointesCollection: IPieceJointes[] = [{ id: 123 }];
          expectedResult = service.addPieceJointesToCollectionIfMissing(pieceJointesCollection, undefined, null);
          expect(expectedResult).toEqual(pieceJointesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
