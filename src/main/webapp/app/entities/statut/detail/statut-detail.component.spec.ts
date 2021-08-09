import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StatutDetailComponent } from './statut-detail.component';

describe('Component Tests', () => {
  describe('Statut Management Detail Component', () => {
    let comp: StatutDetailComponent;
    let fixture: ComponentFixture<StatutDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [StatutDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ statut: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(StatutDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatutDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load statut on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.statut).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
