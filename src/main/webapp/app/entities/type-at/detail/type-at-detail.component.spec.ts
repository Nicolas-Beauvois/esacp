import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypeAtDetailComponent } from './type-at-detail.component';

describe('Component Tests', () => {
  describe('TypeAt Management Detail Component', () => {
    let comp: TypeAtDetailComponent;
    let fixture: ComponentFixture<TypeAtDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TypeAtDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ typeAt: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TypeAtDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeAtDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeAt on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeAt).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
