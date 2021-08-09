import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FicheSuiviSanteDetailComponent } from './fiche-suivi-sante-detail.component';

describe('Component Tests', () => {
  describe('FicheSuiviSante Management Detail Component', () => {
    let comp: FicheSuiviSanteDetailComponent;
    let fixture: ComponentFixture<FicheSuiviSanteDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FicheSuiviSanteDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ ficheSuiviSante: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FicheSuiviSanteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FicheSuiviSanteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ficheSuiviSante on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ficheSuiviSante).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
