jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INatureAccident, NatureAccident } from '../nature-accident.model';
import { NatureAccidentService } from '../service/nature-accident.service';

import { NatureAccidentRoutingResolveService } from './nature-accident-routing-resolve.service';

describe('Service Tests', () => {
  describe('NatureAccident routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: NatureAccidentRoutingResolveService;
    let service: NatureAccidentService;
    let resultNatureAccident: INatureAccident | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(NatureAccidentRoutingResolveService);
      service = TestBed.inject(NatureAccidentService);
      resultNatureAccident = undefined;
    });

    describe('resolve', () => {
      it('should return INatureAccident returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNatureAccident = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNatureAccident).toEqual({ id: 123 });
      });

      it('should return new INatureAccident if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNatureAccident = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultNatureAccident).toEqual(new NatureAccident());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NatureAccident })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNatureAccident = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNatureAccident).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
