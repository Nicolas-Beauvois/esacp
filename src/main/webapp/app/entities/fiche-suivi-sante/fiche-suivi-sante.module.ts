import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FicheSuiviSanteComponent } from './list/fiche-suivi-sante.component';
import { FicheSuiviSanteDetailComponent } from './detail/fiche-suivi-sante-detail.component';
import { FicheSuiviSanteUpdateComponent } from './update/fiche-suivi-sante-update.component';
import { FicheSuiviSanteDeleteDialogComponent } from './delete/fiche-suivi-sante-delete-dialog.component';
import { FicheSuiviSanteRoutingModule } from './route/fiche-suivi-sante-routing.module';

@NgModule({
  imports: [SharedModule, FicheSuiviSanteRoutingModule],
  declarations: [
    FicheSuiviSanteComponent,
    FicheSuiviSanteDetailComponent,
    FicheSuiviSanteUpdateComponent,
    FicheSuiviSanteDeleteDialogComponent,
  ],
  entryComponents: [FicheSuiviSanteDeleteDialogComponent],
})
export class FicheSuiviSanteModule {}
