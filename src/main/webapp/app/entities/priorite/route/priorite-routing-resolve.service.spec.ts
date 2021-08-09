jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPriorite, Priorite } from '../priorite.model';
import { PrioriteService } from '../service/priorite.service';

import { PrioriteRoutingResolveService } from './priorite-routing-resolve.service';

describe('Service Tests', () => {
  describe('Priorite routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PrioriteRoutingResolveService;
    let service: PrioriteService;
    let resultPriorite: IPriorite | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PrioriteRoutingResolveService);
      service = TestBed.inject(PrioriteService);
      resultPriorite = undefined;
    });

    describe('resolve', () => {
      it('should return IPriorite returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPriorite = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPriorite).toEqual({ id: 123 });
      });

      it('should return new IPriorite if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPriorite = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPriorite).toEqual(new Priorite());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Priorite })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPriorite = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPriorite).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
