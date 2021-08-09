import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CspDetailComponent } from './csp-detail.component';

describe('Component Tests', () => {
  describe('Csp Management Detail Component', () => {
    let comp: CspDetailComponent;
    let fixture: ComponentFixture<CspDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CspDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ csp: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CspDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CspDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load csp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.csp).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
