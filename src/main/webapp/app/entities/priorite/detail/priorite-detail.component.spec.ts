import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PrioriteDetailComponent } from './priorite-detail.component';

describe('Component Tests', () => {
  describe('Priorite Management Detail Component', () => {
    let comp: PrioriteDetailComponent;
    let fixture: ComponentFixture<PrioriteDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PrioriteDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ priorite: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PrioriteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrioriteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load priorite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.priorite).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
