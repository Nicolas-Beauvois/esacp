jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ListingMailService } from '../service/listing-mail.service';
import { IListingMail, ListingMail } from '../listing-mail.model';

import { ListingMailUpdateComponent } from './listing-mail-update.component';

describe('Component Tests', () => {
  describe('ListingMail Management Update Component', () => {
    let comp: ListingMailUpdateComponent;
    let fixture: ComponentFixture<ListingMailUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let listingMailService: ListingMailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ListingMailUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ListingMailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ListingMailUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      listingMailService = TestBed.inject(ListingMailService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const listingMail: IListingMail = { id: 456 };

        activatedRoute.data = of({ listingMail });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(listingMail));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ListingMail>>();
        const listingMail = { id: 123 };
        jest.spyOn(listingMailService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ listingMail });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: listingMail }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(listingMailService.update).toHaveBeenCalledWith(listingMail);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ListingMail>>();
        const listingMail = new ListingMail();
        jest.spyOn(listingMailService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ listingMail });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: listingMail }));
        saveSubject.complete();

        // THEN
        expect(listingMailService.create).toHaveBeenCalledWith(listingMail);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ListingMail>>();
        const listingMail = { id: 123 };
        jest.spyOn(listingMailService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ listingMail });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(listingMailService.update).toHaveBeenCalledWith(listingMail);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
