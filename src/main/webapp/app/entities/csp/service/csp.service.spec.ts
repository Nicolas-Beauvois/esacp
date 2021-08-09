import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICsp, Csp } from '../csp.model';

import { CspService } from './csp.service';

describe('Service Tests', () => {
  describe('Csp Service', () => {
    let service: CspService;
    let httpMock: HttpTestingController;
    let elemDefault: ICsp;
    let expectedResult: ICsp | ICsp[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CspService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        csp: 'AAAAAAA',
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

      it('should create a Csp', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Csp()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Csp', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            csp: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Csp', () => {
        const patchObject = Object.assign(
          {
            csp: 'BBBBBB',
          },
          new Csp()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Csp', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            csp: 'BBBBBB',
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

      it('should delete a Csp', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCspToCollectionIfMissing', () => {
        it('should add a Csp to an empty array', () => {
          const csp: ICsp = { id: 123 };
          expectedResult = service.addCspToCollectionIfMissing([], csp);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(csp);
        });

        it('should not add a Csp to an array that contains it', () => {
          const csp: ICsp = { id: 123 };
          const cspCollection: ICsp[] = [
            {
              ...csp,
            },
            { id: 456 },
          ];
          expectedResult = service.addCspToCollectionIfMissing(cspCollection, csp);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Csp to an array that doesn't contain it", () => {
          const csp: ICsp = { id: 123 };
          const cspCollection: ICsp[] = [{ id: 456 }];
          expectedResult = service.addCspToCollectionIfMissing(cspCollection, csp);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(csp);
        });

        it('should add only unique Csp to an array', () => {
          const cspArray: ICsp[] = [{ id: 123 }, { id: 456 }, { id: 88666 }];
          const cspCollection: ICsp[] = [{ id: 123 }];
          expectedResult = service.addCspToCollectionIfMissing(cspCollection, ...cspArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const csp: ICsp = { id: 123 };
          const csp2: ICsp = { id: 456 };
          expectedResult = service.addCspToCollectionIfMissing([], csp, csp2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(csp);
          expect(expectedResult).toContain(csp2);
        });

        it('should accept null and undefined values', () => {
          const csp: ICsp = { id: 123 };
          expectedResult = service.addCspToCollectionIfMissing([], null, csp, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(csp);
        });

        it('should return initial array if no Csp is added', () => {
          const cspCollection: ICsp[] = [{ id: 123 }];
          expectedResult = service.addCspToCollectionIfMissing(cspCollection, undefined, null);
          expect(expectedResult).toEqual(cspCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
