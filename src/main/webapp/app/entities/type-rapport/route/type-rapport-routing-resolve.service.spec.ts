jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITypeRapport, TypeRapport } from '../type-rapport.model';
import { TypeRapportService } from '../service/type-rapport.service';

import { TypeRapportRoutingResolveService } from './type-rapport-routing-resolve.service';

describe('Service Tests', () => {
  describe('TypeRapport routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TypeRapportRoutingResolveService;
    let service: TypeRapportService;
    let resultTypeRapport: ITypeRapport | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TypeRapportRoutingResolveService);
      service = TestBed.inject(TypeRapportService);
      resultTypeRapport = undefined;
    });

    describe('resolve', () => {
      it('should return ITypeRapport returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTypeRapport = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTypeRapport).toEqual({ id: 123 });
      });

      it('should return new ITypeRapport if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTypeRapport = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTypeRapport).toEqual(new TypeRapport());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TypeRapport })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTypeRapport = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTypeRapport).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
