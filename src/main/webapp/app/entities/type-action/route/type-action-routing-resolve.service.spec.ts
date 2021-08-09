jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITypeAction, TypeAction } from '../type-action.model';
import { TypeActionService } from '../service/type-action.service';

import { TypeActionRoutingResolveService } from './type-action-routing-resolve.service';

describe('Service Tests', () => {
  describe('TypeAction routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TypeActionRoutingResolveService;
    let service: TypeActionService;
    let resultTypeAction: ITypeAction | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TypeActionRoutingResolveService);
      service = TestBed.inject(TypeActionService);
      resultTypeAction = undefined;
    });

    describe('resolve', () => {
      it('should return ITypeAction returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTypeAction = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTypeAction).toEqual({ id: 123 });
      });

      it('should return new ITypeAction if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTypeAction = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTypeAction).toEqual(new TypeAction());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TypeAction })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTypeAction = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTypeAction).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
