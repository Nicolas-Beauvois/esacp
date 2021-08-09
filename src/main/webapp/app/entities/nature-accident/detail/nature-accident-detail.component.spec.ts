import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureAccidentDetailComponent } from './nature-accident-detail.component';

describe('Component Tests', () => {
  describe('NatureAccident Management Detail Component', () => {
    let comp: NatureAccidentDetailComponent;
    let fixture: ComponentFixture<NatureAccidentDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NatureAccidentDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ natureAccident: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NatureAccidentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NatureAccidentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load natureAccident on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.natureAccident).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
