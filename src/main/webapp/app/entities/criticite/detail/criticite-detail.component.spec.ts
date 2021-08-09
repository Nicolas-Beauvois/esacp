import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CriticiteDetailComponent } from './criticite-detail.component';

describe('Component Tests', () => {
  describe('Criticite Management Detail Component', () => {
    let comp: CriticiteDetailComponent;
    let fixture: ComponentFixture<CriticiteDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CriticiteDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ criticite: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CriticiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CriticiteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load criticite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.criticite).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
