jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFicheSuiviSante, FicheSuiviSante } from '../fiche-suivi-sante.model';
import { FicheSuiviSanteService } from '../service/fiche-suivi-sante.service';

import { FicheSuiviSanteRoutingResolveService } from './fiche-suivi-sante-routing-resolve.service';

describe('Service Tests', () => {
  describe('FicheSuiviSante routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FicheSuiviSanteRoutingResolveService;
    let service: FicheSuiviSanteService;
    let resultFicheSuiviSante: IFicheSuiviSante | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FicheSuiviSanteRoutingResolveService);
      service = TestBed.inject(FicheSuiviSanteService);
      resultFicheSuiviSante = undefined;
    });

    describe('resolve', () => {
      it('should return IFicheSuiviSante returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFicheSuiviSante = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFicheSuiviSante).toEqual({ id: 123 });
      });

      it('should return new IFicheSuiviSante if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFicheSuiviSante = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFicheSuiviSante).toEqual(new FicheSuiviSante());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FicheSuiviSante })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFicheSuiviSante = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFicheSuiviSante).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
