import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMail, Mail } from '../mail.model';

import { MailService } from './mail.service';

describe('Service Tests', () => {
  describe('Mail Service', () => {
    let service: MailService;
    let httpMock: HttpTestingController;
    let elemDefault: IMail;
    let expectedResult: IMail | IMail[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MailService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        typeMail: 'AAAAAAA',
        msgMail: 'AAAAAAA',
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

      it('should create a Mail', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Mail()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Mail', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeMail: 'BBBBBB',
            msgMail: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Mail', () => {
        const patchObject = Object.assign({}, new Mail());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Mail', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            typeMail: 'BBBBBB',
            msgMail: 'BBBBBB',
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

      it('should delete a Mail', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMailToCollectionIfMissing', () => {
        it('should add a Mail to an empty array', () => {
          const mail: IMail = { id: 123 };
          expectedResult = service.addMailToCollectionIfMissing([], mail);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(mail);
        });

        it('should not add a Mail to an array that contains it', () => {
          const mail: IMail = { id: 123 };
          const mailCollection: IMail[] = [
            {
              ...mail,
            },
            { id: 456 },
          ];
          expectedResult = service.addMailToCollectionIfMissing(mailCollection, mail);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Mail to an array that doesn't contain it", () => {
          const mail: IMail = { id: 123 };
          const mailCollection: IMail[] = [{ id: 456 }];
          expectedResult = service.addMailToCollectionIfMissing(mailCollection, mail);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(mail);
        });

        it('should add only unique Mail to an array', () => {
          const mailArray: IMail[] = [{ id: 123 }, { id: 456 }, { id: 12328 }];
          const mailCollection: IMail[] = [{ id: 123 }];
          expectedResult = service.addMailToCollectionIfMissing(mailCollection, ...mailArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const mail: IMail = { id: 123 };
          const mail2: IMail = { id: 456 };
          expectedResult = service.addMailToCollectionIfMissing([], mail, mail2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(mail);
          expect(expectedResult).toContain(mail2);
        });

        it('should accept null and undefined values', () => {
          const mail: IMail = { id: 123 };
          expectedResult = service.addMailToCollectionIfMissing([], null, mail, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(mail);
        });

        it('should return initial array if no Mail is added', () => {
          const mailCollection: IMail[] = [{ id: 123 }];
          expectedResult = service.addMailToCollectionIfMissing(mailCollection, undefined, null);
          expect(expectedResult).toEqual(mailCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
