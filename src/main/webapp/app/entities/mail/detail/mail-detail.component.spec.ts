import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MailDetailComponent } from './mail-detail.component';

describe('Component Tests', () => {
  describe('Mail Management Detail Component', () => {
    let comp: MailDetailComponent;
    let fixture: ComponentFixture<MailDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MailDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ mail: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mail on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mail).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
