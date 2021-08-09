import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypeActionDetailComponent } from './type-action-detail.component';

describe('Component Tests', () => {
  describe('TypeAction Management Detail Component', () => {
    let comp: TypeActionDetailComponent;
    let fixture: ComponentFixture<TypeActionDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TypeActionDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ typeAction: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TypeActionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeActionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeAction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeAction).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
