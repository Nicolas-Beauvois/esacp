import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICorrectPrevent, CorrectPrevent } from '../correct-prevent.model';

import { CorrectPreventService } from './correct-prevent.service';

describe('Service Tests', () => {
  describe('CorrectPrevent Service', () => {
    let service: CorrectPreventService;
    let httpMock: HttpTestingController;
    let elemDefault: ICorrectPrevent;
    let expectedResult: ICorrectPrevent | ICorrectPrevent[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CorrectPreventService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        correctPrevent: 'AAAAAAA',
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

      it('should create a CorrectPrevent', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CorrectPrevent()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CorrectPrevent', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            correctPrevent: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CorrectPrevent', () => {
        const patchObject = Object.assign(
          {
            correctPrevent: 'BBBBBB',
          },
          new CorrectPrevent()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CorrectPrevent', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            correctPrevent: 'BBBBBB',
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

      it('should delete a CorrectPrevent', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCorrectPreventToCollectionIfMissing', () => {
        it('should add a CorrectPrevent to an empty array', () => {
          const correctPrevent: ICorrectPrevent = { id: 123 };
          expectedResult = service.addCorrectPreventToCollectionIfMissing([], correctPrevent);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(correctPrevent);
        });

        it('should not add a CorrectPrevent to an array that contains it', () => {
          const correctPrevent: ICorrectPrevent = { id: 123 };
          const correctPreventCollection: ICorrectPrevent[] = [
            {
              ...correctPrevent,
            },
            { id: 456 },
          ];
          expectedResult = service.addCorrectPreventToCollectionIfMissing(correctPreventCollection, correctPrevent);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CorrectPrevent to an array that doesn't contain it", () => {
          const correctPrevent: ICorrectPrevent = { id: 123 };
          const correctPreventCollection: ICorrectPrevent[] = [{ id: 456 }];
          expectedResult = service.addCorrectPreventToCollectionIfMissing(correctPreventCollection, correctPrevent);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(correctPrevent);
        });

        it('should add only unique CorrectPrevent to an array', () => {
          const correctPreventArray: ICorrectPrevent[] = [{ id: 123 }, { id: 456 }, { id: 59982 }];
          const correctPreventCollection: ICorrectPrevent[] = [{ id: 123 }];
          expectedResult = service.addCorrectPreventToCollectionIfMissing(correctPreventCollection, ...correctPreventArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const correctPrevent: ICorrectPrevent = { id: 123 };
          const correctPrevent2: ICorrectPrevent = { id: 456 };
          expectedResult = service.addCorrectPreventToCollectionIfMissing([], correctPrevent, correctPrevent2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(correctPrevent);
          expect(expectedResult).toContain(correctPrevent2);
        });

        it('should accept null and undefined values', () => {
          const correctPrevent: ICorrectPrevent = { id: 123 };
          expectedResult = service.addCorrectPreventToCollectionIfMissing([], null, correctPrevent, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(correctPrevent);
        });

        it('should return initial array if no CorrectPrevent is added', () => {
          const correctPreventCollection: ICorrectPrevent[] = [{ id: 123 }];
          expectedResult = service.addCorrectPreventToCollectionIfMissing(correctPreventCollection, undefined, null);
          expect(expectedResult).toEqual(correctPreventCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
