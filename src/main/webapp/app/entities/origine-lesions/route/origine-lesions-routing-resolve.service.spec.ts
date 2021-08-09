jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOrigineLesions, OrigineLesions } from '../origine-lesions.model';
import { OrigineLesionsService } from '../service/origine-lesions.service';

import { OrigineLesionsRoutingResolveService } from './origine-lesions-routing-resolve.service';

describe('Service Tests', () => {
  describe('OrigineLesions routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: OrigineLesionsRoutingResolveService;
    let service: OrigineLesionsService;
    let resultOrigineLesions: IOrigineLesions | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(OrigineLesionsRoutingResolveService);
      service = TestBed.inject(OrigineLesionsService);
      resultOrigineLesions = undefined;
    });

    describe('resolve', () => {
      it('should return IOrigineLesions returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrigineLesions = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOrigineLesions).toEqual({ id: 123 });
      });

      it('should return new IOrigineLesions if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrigineLesions = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultOrigineLesions).toEqual(new OrigineLesions());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as OrigineLesions })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrigineLesions = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOrigineLesions).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
