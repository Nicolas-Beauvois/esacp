import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITypeAction, TypeAction } from '../type-action.model';

import { TypeActionService } from './type-action.service';

describe('Service Tests', () => {
  describe('TypeAction Service', () => {
    let service: TypeActionService;
    let httpMock: HttpTestingController;
    let elemDefault: ITypeAction;
    let expectedResult: ITypeAction | ITypeAction[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TypeActionService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        typeAction: 'AAAAAAA',
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

      it('should create a TypeAction', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TypeAction()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TypeAction', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeAction: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a TypeAction', () => {
        const patchObject = Object.assign(
          {
            typeAction: 'BBBBBB',
          },
          new TypeAction()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TypeAction', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeAction: 'BBBBBB',
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

      it('should delete a TypeAction', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTypeActionToCollectionIfMissing', () => {
        it('should add a TypeAction to an empty array', () => {
          const typeAction: ITypeAction = { id: 123 };
          expectedResult = service.addTypeActionToCollectionIfMissing([], typeAction);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(typeAction);
        });

        it('should not add a TypeAction to an array that contains it', () => {
          const typeAction: ITypeAction = { id: 123 };
          const typeActionCollection: ITypeAction[] = [
            {
              ...typeAction,
            },
            { id: 456 },
          ];
          expectedResult = service.addTypeActionToCollectionIfMissing(typeActionCollection, typeAction);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TypeAction to an array that doesn't contain it", () => {
          const typeAction: ITypeAction = { id: 123 };
          const typeActionCollection: ITypeAction[] = [{ id: 456 }];
          expectedResult = service.addTypeActionToCollectionIfMissing(typeActionCollection, typeAction);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(typeAction);
        });

        it('should add only unique TypeAction to an array', () => {
          const typeActionArray: ITypeAction[] = [{ id: 123 }, { id: 456 }, { id: 89940 }];
          const typeActionCollection: ITypeAction[] = [{ id: 123 }];
          expectedResult = service.addTypeActionToCollectionIfMissing(typeActionCollection, ...typeActionArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const typeAction: ITypeAction = { id: 123 };
          const typeAction2: ITypeAction = { id: 456 };
          expectedResult = service.addTypeActionToCollectionIfMissing([], typeAction, typeAction2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(typeAction);
          expect(expectedResult).toContain(typeAction2);
        });

        it('should accept null and undefined values', () => {
          const typeAction: ITypeAction = { id: 123 };
          expectedResult = service.addTypeActionToCollectionIfMissing([], null, typeAction, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(typeAction);
        });

        it('should return initial array if no TypeAction is added', () => {
          const typeActionCollection: ITypeAction[] = [{ id: 123 }];
          expectedResult = service.addTypeActionToCollectionIfMissing(typeActionCollection, undefined, null);
          expect(expectedResult).toEqual(typeActionCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
