import { Route } from '@angular/router';

import { FormulaireATComponent } from './formulaire-at.component';

export const FORMULAIREAT_ROUTE: Route = {
  path: '',
  component: FormulaireATComponent,
  data: {
    pageTitle: 'login.title',
  },
};
