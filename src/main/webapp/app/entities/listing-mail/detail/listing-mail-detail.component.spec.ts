import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ListingMailDetailComponent } from './listing-mail-detail.component';

describe('Component Tests', () => {
  describe('ListingMail Management Detail Component', () => {
    let comp: ListingMailDetailComponent;
    let fixture: ComponentFixture<ListingMailDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ListingMailDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ listingMail: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ListingMailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ListingMailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load listingMail on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.listingMail).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
