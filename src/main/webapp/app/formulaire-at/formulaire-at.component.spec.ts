import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormulaireATComponent } from './formulaire-at.component';

describe('FormulaireATComponent', () => {
  let component: FormulaireATComponent;
  let fixture: ComponentFixture<FormulaireATComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FormulaireATComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FormulaireATComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
