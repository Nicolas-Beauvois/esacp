jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IActions, Actions } from '../actions.model';
import { ActionsService } from '../service/actions.service';

import { ActionsRoutingResolveService } from './actions-routing-resolve.service';

describe('Service Tests', () => {
  describe('Actions routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ActionsRoutingResolveService;
    let service: ActionsService;
    let resultActions: IActions | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ActionsRoutingResolveService);
      service = TestBed.inject(ActionsService);
      resultActions = undefined;
    });

    describe('resolve', () => {
      it('should return IActions returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultActions = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultActions).toEqual({ id: 123 });
      });

      it('should return new IActions if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultActions = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultActions).toEqual(new Actions());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Actions })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultActions = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultActions).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
