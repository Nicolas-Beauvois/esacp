import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EtapeValidationDetailComponent } from './etape-validation-detail.component';

describe('Component Tests', () => {
  describe('EtapeValidation Management Detail Component', () => {
    let comp: EtapeValidationDetailComponent;
    let fixture: ComponentFixture<EtapeValidationDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EtapeValidationDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ etapeValidation: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EtapeValidationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EtapeValidationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load etapeValidation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.etapeValidation).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
