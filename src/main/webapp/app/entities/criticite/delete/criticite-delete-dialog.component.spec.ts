jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { CriticiteService } from '../service/criticite.service';

import { CriticiteDeleteDialogComponent } from './criticite-delete-dialog.component';

describe('Component Tests', () => {
  describe('Criticite Management Delete Component', () => {
    let comp: CriticiteDeleteDialogComponent;
    let fixture: ComponentFixture<CriticiteDeleteDialogComponent>;
    let service: CriticiteService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CriticiteDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(CriticiteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CriticiteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(CriticiteService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
