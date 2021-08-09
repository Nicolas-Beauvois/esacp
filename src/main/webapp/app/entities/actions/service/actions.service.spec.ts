import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IActions, Actions } from '../actions.model';

import { ActionsService } from './actions.service';

describe('Service Tests', () => {
  describe('Actions Service', () => {
    let service: ActionsService;
    let httpMock: HttpTestingController;
    let elemDefault: IActions;
    let expectedResult: IActions | IActions[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ActionsService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        redacteur: 'AAAAAAA',
        isActionImmediate: false,
        dateEtHeureCreation: currentDate,
        titre: 'AAAAAAA',
        descriptionAction: 'AAAAAAA',
        descriptionReponse: 'AAAAAAA',
        delai: currentDate,
        etat: 'AAAAAAA',
        pilote: 'AAAAAAA',
        dateEtHeureValidationPilote: currentDate,
        porteur: 'AAAAAAA',
        dateEtHeureValidationPorteur: currentDate,
        hse: 'AAAAAAA',
        dateEtHeureValidationHse: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateEtHeureCreation: currentDate.format(DATE_FORMAT),
            delai: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationPilote: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationHse: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Actions', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateEtHeureCreation: currentDate.format(DATE_FORMAT),
            delai: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationPilote: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationHse: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtHeureCreation: currentDate,
            delai: currentDate,
            dateEtHeureValidationPilote: currentDate,
            dateEtHeureValidationPorteur: currentDate,
            dateEtHeureValidationHse: currentDate,
          },
          returnedFromService
        );

        service.create(new Actions()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Actions', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            redacteur: 'BBBBBB',
            isActionImmediate: true,
            dateEtHeureCreation: currentDate.format(DATE_FORMAT),
            titre: 'BBBBBB',
            descriptionAction: 'BBBBBB',
            descriptionReponse: 'BBBBBB',
            delai: currentDate.format(DATE_FORMAT),
            etat: 'BBBBBB',
            pilote: 'BBBBBB',
            dateEtHeureValidationPilote: currentDate.format(DATE_FORMAT),
            porteur: 'BBBBBB',
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
            hse: 'BBBBBB',
            dateEtHeureValidationHse: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtHeureCreation: currentDate,
            delai: currentDate,
            dateEtHeureValidationPilote: currentDate,
            dateEtHeureValidationPorteur: currentDate,
            dateEtHeureValidationHse: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Actions', () => {
        const patchObject = Object.assign(
          {
            redacteur: 'BBBBBB',
            titre: 'BBBBBB',
            descriptionAction: 'BBBBBB',
            delai: currentDate.format(DATE_FORMAT),
            etat: 'BBBBBB',
            porteur: 'BBBBBB',
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
          },
          new Actions()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateEtHeureCreation: currentDate,
            delai: currentDate,
            dateEtHeureValidationPilote: currentDate,
            dateEtHeureValidationPorteur: currentDate,
            dateEtHeureValidationHse: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Actions', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            redacteur: 'BBBBBB',
            isActionImmediate: true,
            dateEtHeureCreation: currentDate.format(DATE_FORMAT),
            titre: 'BBBBBB',
            descriptionAction: 'BBBBBB',
            descriptionReponse: 'BBBBBB',
            delai: currentDate.format(DATE_FORMAT),
            etat: 'BBBBBB',
            pilote: 'BBBBBB',
            dateEtHeureValidationPilote: currentDate.format(DATE_FORMAT),
            porteur: 'BBBBBB',
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
            hse: 'BBBBBB',
            dateEtHeureValidationHse: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateEtHeureCreation: currentDate,
            delai: currentDate,
            dateEtHeureValidationPilote: currentDate,
            dateEtHeureValidationPorteur: currentDate,
            dateEtHeureValidationHse: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Actions', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addActionsToCollectionIfMissing', () => {
        it('should add a Actions to an empty array', () => {
          const actions: IActions = { id: 123 };
          expectedResult = service.addActionsToCollectionIfMissing([], actions);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(actions);
        });

        it('should not add a Actions to an array that contains it', () => {
          const actions: IActions = { id: 123 };
          const actionsCollection: IActions[] = [
            {
              ...actions,
            },
            { id: 456 },
          ];
          expectedResult = service.addActionsToCollectionIfMissing(actionsCollection, actions);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Actions to an array that doesn't contain it", () => {
          const actions: IActions = { id: 123 };
          const actionsCollection: IActions[] = [{ id: 456 }];
          expectedResult = service.addActionsToCollectionIfMissing(actionsCollection, actions);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(actions);
        });

        it('should add only unique Actions to an array', () => {
          const actionsArray: IActions[] = [{ id: 123 }, { id: 456 }, { id: 78071 }];
          const actionsCollection: IActions[] = [{ id: 123 }];
          expectedResult = service.addActionsToCollectionIfMissing(actionsCollection, ...actionsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const actions: IActions = { id: 123 };
          const actions2: IActions = { id: 456 };
          expectedResult = service.addActionsToCollectionIfMissing([], actions, actions2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(actions);
          expect(expectedResult).toContain(actions2);
        });

        it('should accept null and undefined values', () => {
          const actions: IActions = { id: 123 };
          expectedResult = service.addActionsToCollectionIfMissing([], null, actions, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(actions);
        });

        it('should return initial array if no Actions is added', () => {
          const actionsCollection: IActions[] = [{ id: 123 }];
          expectedResult = service.addActionsToCollectionIfMissing(actionsCollection, undefined, null);
          expect(expectedResult).toEqual(actionsCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
