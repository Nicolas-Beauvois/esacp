import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TypeAtComponent } from './list/type-at.component';
import { TypeAtDetailComponent } from './detail/type-at-detail.component';
import { TypeAtUpdateComponent } from './update/type-at-update.component';
import { TypeAtDeleteDialogComponent } from './delete/type-at-delete-dialog.component';
import { TypeAtRoutingModule } from './route/type-at-routing.module';

@NgModule({
  imports: [SharedModule, TypeAtRoutingModule],
  declarations: [TypeAtComponent, TypeAtDetailComponent, TypeAtUpdateComponent, TypeAtDeleteDialogComponent],
  entryComponents: [TypeAtDeleteDialogComponent],
})
export class TypeAtModule {}
