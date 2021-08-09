import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CriticiteComponent } from './list/criticite.component';
import { CriticiteDetailComponent } from './detail/criticite-detail.component';
import { CriticiteUpdateComponent } from './update/criticite-update.component';
import { CriticiteDeleteDialogComponent } from './delete/criticite-delete-dialog.component';
import { CriticiteRoutingModule } from './route/criticite-routing.module';

@NgModule({
  imports: [SharedModule, CriticiteRoutingModule],
  declarations: [CriticiteComponent, CriticiteDetailComponent, CriticiteUpdateComponent, CriticiteDeleteDialogComponent],
  entryComponents: [CriticiteDeleteDialogComponent],
})
export class CriticiteModule {}
