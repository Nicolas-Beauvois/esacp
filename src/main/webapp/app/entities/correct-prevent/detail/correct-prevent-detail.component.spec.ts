import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CorrectPreventDetailComponent } from './correct-prevent-detail.component';

describe('Component Tests', () => {
  describe('CorrectPrevent Management Detail Component', () => {
    let comp: CorrectPreventDetailComponent;
    let fixture: ComponentFixture<CorrectPreventDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CorrectPreventDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ correctPrevent: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CorrectPreventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CorrectPreventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load correctPrevent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.correctPrevent).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
