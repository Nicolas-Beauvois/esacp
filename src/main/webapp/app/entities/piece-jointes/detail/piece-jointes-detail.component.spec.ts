import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PieceJointesDetailComponent } from './piece-jointes-detail.component';

describe('Component Tests', () => {
  describe('PieceJointes Management Detail Component', () => {
    let comp: PieceJointesDetailComponent;
    let fixture: ComponentFixture<PieceJointesDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PieceJointesDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ pieceJointes: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PieceJointesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PieceJointesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pieceJointes on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pieceJointes).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
