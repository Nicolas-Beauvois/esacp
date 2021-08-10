import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { FORMULAIREAT_ROUTE } from './formulaire-at.route';
import { FormulaireATComponent } from './formulaire-at.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([FORMULAIREAT_ROUTE])],
  declarations: [FormulaireATComponent],
  exports: [FormulaireATComponent],
})
export class FormulaireATModule {}
