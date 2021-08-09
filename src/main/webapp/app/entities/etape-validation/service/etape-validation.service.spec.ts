import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEtapeValidation, EtapeValidation } from '../etape-validation.model';

import { EtapeValidationService } from './etape-validation.service';

describe('Service Tests', () => {
  describe('EtapeValidation Service', () => {
    let service: EtapeValidationService;
    let httpMock: HttpTestingController;
    let elemDefault: IEtapeValidation;
    let expectedResult: IEtapeValidation | IEtapeValidation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EtapeValidationService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        etapeValidation: 'AAAAAAA',
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

      it('should create a EtapeValidation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EtapeValidation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EtapeValidation', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            etapeValidation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EtapeValidation', () => {
        const patchObject = Object.assign({}, new EtapeValidation());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EtapeValidation', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            etapeValidation: 'BBBBBB',
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

      it('should delete a EtapeValidation', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEtapeValidationToCollectionIfMissing', () => {
        it('should add a EtapeValidation to an empty array', () => {
          const etapeValidation: IEtapeValidation = { id: 123 };
          expectedResult = service.addEtapeValidationToCollectionIfMissing([], etapeValidation);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(etapeValidation);
        });

        it('should not add a EtapeValidation to an array that contains it', () => {
          const etapeValidation: IEtapeValidation = { id: 123 };
          const etapeValidationCollection: IEtapeValidation[] = [
            {
              ...etapeValidation,
            },
            { id: 456 },
          ];
          expectedResult = service.addEtapeValidationToCollectionIfMissing(etapeValidationCollection, etapeValidation);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EtapeValidation to an array that doesn't contain it", () => {
          const etapeValidation: IEtapeValidation = { id: 123 };
          const etapeValidationCollection: IEtapeValidation[] = [{ id: 456 }];
          expectedResult = service.addEtapeValidationToCollectionIfMissing(etapeValidationCollection, etapeValidation);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(etapeValidation);
        });

        it('should add only unique EtapeValidation to an array', () => {
          const etapeValidationArray: IEtapeValidation[] = [{ id: 123 }, { id: 456 }, { id: 30273 }];
          const etapeValidationCollection: IEtapeValidation[] = [{ id: 123 }];
          expectedResult = service.addEtapeValidationToCollectionIfMissing(etapeValidationCollection, ...etapeValidationArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const etapeValidation: IEtapeValidation = { id: 123 };
          const etapeValidation2: IEtapeValidation = { id: 456 };
          expectedResult = service.addEtapeValidationToCollectionIfMissing([], etapeValidation, etapeValidation2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(etapeValidation);
          expect(expectedResult).toContain(etapeValidation2);
        });

        it('should accept null and undefined values', () => {
          const etapeValidation: IEtapeValidation = { id: 123 };
          expectedResult = service.addEtapeValidationToCollectionIfMissing([], null, etapeValidation, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(etapeValidation);
        });

        it('should return initial array if no EtapeValidation is added', () => {
          const etapeValidationCollection: IEtapeValidation[] = [{ id: 123 }];
          expectedResult = service.addEtapeValidationToCollectionIfMissing(etapeValidationCollection, undefined, null);
          expect(expectedResult).toEqual(etapeValidationCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
