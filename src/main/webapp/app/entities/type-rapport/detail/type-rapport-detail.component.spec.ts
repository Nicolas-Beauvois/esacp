import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TypeRapportDetailComponent } from './type-rapport-detail.component';

describe('Component Tests', () => {
  describe('TypeRapport Management Detail Component', () => {
    let comp: TypeRapportDetailComponent;
    let fixture: ComponentFixture<TypeRapportDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TypeRapportDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ typeRapport: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TypeRapportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeRapportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typeRapport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeRapport).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
