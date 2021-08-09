import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISiegeLesions, SiegeLesions } from '../siege-lesions.model';

import { SiegeLesionsService } from './siege-lesions.service';

describe('Service Tests', () => {
  describe('SiegeLesions Service', () => {
    let service: SiegeLesionsService;
    let httpMock: HttpTestingController;
    let elemDefault: ISiegeLesions;
    let expectedResult: ISiegeLesions | ISiegeLesions[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SiegeLesionsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        typeSiegeDeLesions: 'AAAAAAA',
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

      it('should create a SiegeLesions', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SiegeLesions()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SiegeLesions', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeSiegeDeLesions: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a SiegeLesions', () => {
        const patchObject = Object.assign(
          {
            typeSiegeDeLesions: 'BBBBBB',
          },
          new SiegeLesions()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SiegeLesions', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeSiegeDeLesions: 'BBBBBB',
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

      it('should delete a SiegeLesions', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSiegeLesionsToCollectionIfMissing', () => {
        it('should add a SiegeLesions to an empty array', () => {
          const siegeLesions: ISiegeLesions = { id: 123 };
          expectedResult = service.addSiegeLesionsToCollectionIfMissing([], siegeLesions);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(siegeLesions);
        });

        it('should not add a SiegeLesions to an array that contains it', () => {
          const siegeLesions: ISiegeLesions = { id: 123 };
          const siegeLesionsCollection: ISiegeLesions[] = [
            {
              ...siegeLesions,
            },
            { id: 456 },
          ];
          expectedResult = service.addSiegeLesionsToCollectionIfMissing(siegeLesionsCollection, siegeLesions);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a SiegeLesions to an array that doesn't contain it", () => {
          const siegeLesions: ISiegeLesions = { id: 123 };
          const siegeLesionsCollection: ISiegeLesions[] = [{ id: 456 }];
          expectedResult = service.addSiegeLesionsToCollectionIfMissing(siegeLesionsCollection, siegeLesions);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(siegeLesions);
        });

        it('should add only unique SiegeLesions to an array', () => {
          const siegeLesionsArray: ISiegeLesions[] = [{ id: 123 }, { id: 456 }, { id: 72700 }];
          const siegeLesionsCollection: ISiegeLesions[] = [{ id: 123 }];
          expectedResult = service.addSiegeLesionsToCollectionIfMissing(siegeLesionsCollection, ...siegeLesionsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const siegeLesions: ISiegeLesions = { id: 123 };
          const siegeLesions2: ISiegeLesions = { id: 456 };
          expectedResult = service.addSiegeLesionsToCollectionIfMissing([], siegeLesions, siegeLesions2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(siegeLesions);
          expect(expectedResult).toContain(siegeLesions2);
        });

        it('should accept null and undefined values', () => {
          const siegeLesions: ISiegeLesions = { id: 123 };
          expectedResult = service.addSiegeLesionsToCollectionIfMissing([], null, siegeLesions, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(siegeLesions);
        });

        it('should return initial array if no SiegeLesions is added', () => {
          const siegeLesionsCollection: ISiegeLesions[] = [{ id: 123 }];
          expectedResult = service.addSiegeLesionsToCollectionIfMissing(siegeLesionsCollection, undefined, null);
          expect(expectedResult).toEqual(siegeLesionsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
