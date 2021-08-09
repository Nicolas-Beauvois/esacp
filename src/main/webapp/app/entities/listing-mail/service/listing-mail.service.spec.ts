import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IListingMail, ListingMail } from '../listing-mail.model';

import { ListingMailService } from './listing-mail.service';

describe('Service Tests', () => {
  describe('ListingMail Service', () => {
    let service: ListingMailService;
    let httpMock: HttpTestingController;
    let elemDefault: IListingMail;
    let expectedResult: IListingMail | IListingMail[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ListingMailService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        typeMessage: 'AAAAAAA',
        email: 'AAAAAAA',
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

      it('should create a ListingMail', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ListingMail()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ListingMail', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeMessage: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ListingMail', () => {
        const patchObject = Object.assign(
          {
            email: 'BBBBBB',
          },
          new ListingMail()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ListingMail', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeMessage: 'BBBBBB',
            email: 'BBBBBB',
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

      it('should delete a ListingMail', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addListingMailToCollectionIfMissing', () => {
        it('should add a ListingMail to an empty array', () => {
          const listingMail: IListingMail = { id: 123 };
          expectedResult = service.addListingMailToCollectionIfMissing([], listingMail);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(listingMail);
        });

        it('should not add a ListingMail to an array that contains it', () => {
          const listingMail: IListingMail = { id: 123 };
          const listingMailCollection: IListingMail[] = [
            {
              ...listingMail,
            },
            { id: 456 },
          ];
          expectedResult = service.addListingMailToCollectionIfMissing(listingMailCollection, listingMail);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ListingMail to an array that doesn't contain it", () => {
          const listingMail: IListingMail = { id: 123 };
          const listingMailCollection: IListingMail[] = [{ id: 456 }];
          expectedResult = service.addListingMailToCollectionIfMissing(listingMailCollection, listingMail);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(listingMail);
        });

        it('should add only unique ListingMail to an array', () => {
          const listingMailArray: IListingMail[] = [{ id: 123 }, { id: 456 }, { id: 90558 }];
          const listingMailCollection: IListingMail[] = [{ id: 123 }];
          expectedResult = service.addListingMailToCollectionIfMissing(listingMailCollection, ...listingMailArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const listingMail: IListingMail = { id: 123 };
          const listingMail2: IListingMail = { id: 456 };
          expectedResult = service.addListingMailToCollectionIfMissing([], listingMail, listingMail2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(listingMail);
          expect(expectedResult).toContain(listingMail2);
        });

        it('should accept null and undefined values', () => {
          const listingMail: IListingMail = { id: 123 };
          expectedResult = service.addListingMailToCollectionIfMissing([], null, listingMail, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(listingMail);
        });

        it('should return initial array if no ListingMail is added', () => {
          const listingMailCollection: IListingMail[] = [{ id: 123 }];
          expectedResult = service.addListingMailToCollectionIfMissing(listingMailCollection, undefined, null);
          expect(expectedResult).toEqual(listingMailCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
