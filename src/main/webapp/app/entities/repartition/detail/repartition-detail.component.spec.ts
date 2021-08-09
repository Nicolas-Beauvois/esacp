import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RepartitionDetailComponent } from './repartition-detail.component';

describe('Component Tests', () => {
  describe('Repartition Management Detail Component', () => {
    let comp: RepartitionDetailComponent;
    let fixture: ComponentFixture<RepartitionDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RepartitionDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ repartition: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RepartitionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RepartitionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load repartition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.repartition).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
