import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SiegeLesionsDetailComponent } from './siege-lesions-detail.component';

describe('Component Tests', () => {
  describe('SiegeLesions Management Detail Component', () => {
    let comp: SiegeLesionsDetailComponent;
    let fixture: ComponentFixture<SiegeLesionsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [SiegeLesionsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ siegeLesions: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(SiegeLesionsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SiegeLesionsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load siegeLesions on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.siegeLesions).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
