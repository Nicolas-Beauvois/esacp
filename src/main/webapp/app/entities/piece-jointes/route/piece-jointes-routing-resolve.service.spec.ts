jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPieceJointes, PieceJointes } from '../piece-jointes.model';
import { PieceJointesService } from '../service/piece-jointes.service';

import { PieceJointesRoutingResolveService } from './piece-jointes-routing-resolve.service';

describe('Service Tests', () => {
  describe('PieceJointes routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: PieceJointesRoutingResolveService;
    let service: PieceJointesService;
    let resultPieceJointes: IPieceJointes | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(PieceJointesRoutingResolveService);
      service = TestBed.inject(PieceJointesService);
      resultPieceJointes = undefined;
    });

    describe('resolve', () => {
      it('should return IPieceJointes returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPieceJointes = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPieceJointes).toEqual({ id: 123 });
      });

      it('should return new IPieceJointes if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPieceJointes = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultPieceJointes).toEqual(new PieceJointes());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PieceJointes })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultPieceJointes = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultPieceJointes).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
