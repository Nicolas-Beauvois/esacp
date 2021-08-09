jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEtapeValidation, EtapeValidation } from '../etape-validation.model';
import { EtapeValidationService } from '../service/etape-validation.service';

import { EtapeValidationRoutingResolveService } from './etape-validation-routing-resolve.service';

describe('Service Tests', () => {
  describe('EtapeValidation routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EtapeValidationRoutingResolveService;
    let service: EtapeValidationService;
    let resultEtapeValidation: IEtapeValidation | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EtapeValidationRoutingResolveService);
      service = TestBed.inject(EtapeValidationService);
      resultEtapeValidation = undefined;
    });

    describe('resolve', () => {
      it('should return IEtapeValidation returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEtapeValidation = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEtapeValidation).toEqual({ id: 123 });
      });

      it('should return new IEtapeValidation if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEtapeValidation = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEtapeValidation).toEqual(new EtapeValidation());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as EtapeValidation })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEtapeValidation = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEtapeValidation).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
