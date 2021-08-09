jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMail, Mail } from '../mail.model';
import { MailService } from '../service/mail.service';

import { MailRoutingResolveService } from './mail-routing-resolve.service';

describe('Service Tests', () => {
  describe('Mail routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MailRoutingResolveService;
    let service: MailService;
    let resultMail: IMail | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MailRoutingResolveService);
      service = TestBed.inject(MailService);
      resultMail = undefined;
    });

    describe('resolve', () => {
      it('should return IMail returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMail = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMail).toEqual({ id: 123 });
      });

      it('should return new IMail if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMail = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMail).toEqual(new Mail());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Mail })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMail = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMail).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
