import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrigineLesionsDetailComponent } from './origine-lesions-detail.component';

describe('Component Tests', () => {
  describe('OrigineLesions Management Detail Component', () => {
    let comp: OrigineLesionsDetailComponent;
    let fixture: ComponentFixture<OrigineLesionsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [OrigineLesionsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ origineLesions: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(OrigineLesionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrigineLesionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load origineLesions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.origineLesions).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
