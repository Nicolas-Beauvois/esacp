jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { NatureAccidentService } from '../service/nature-accident.service';

import { NatureAccidentDeleteDialogComponent } from './nature-accident-delete-dialog.component';

describe('Component Tests', () => {
  describe('NatureAccident Management Delete Component', () => {
    let comp: NatureAccidentDeleteDialogComponent;
    let fixture: ComponentFixture<NatureAccidentDeleteDialogComponent>;
    let service: NatureAccidentService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NatureAccidentDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(NatureAccidentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NatureAccidentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NatureAccidentService);
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
