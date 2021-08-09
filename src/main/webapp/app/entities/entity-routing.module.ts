import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-extra',
        data: { pageTitle: 'esacpApp.userExtra.home.title' },
        loadChildren: () => import('./user-extra/user-extra.module').then(m => m.UserExtraModule),
      },
      {
        path: 'sexe',
        data: { pageTitle: 'esacpApp.sexe.home.title' },
        loadChildren: () => import('./sexe/sexe.module').then(m => m.SexeModule),
      },
      {
        path: 'correct-prevent',
        data: { pageTitle: 'esacpApp.correctPrevent.home.title' },
        loadChildren: () => import('./correct-prevent/correct-prevent.module').then(m => m.CorrectPreventModule),
      },
      {
        path: 'csp',
        data: { pageTitle: 'esacpApp.csp.home.title' },
        loadChildren: () => import('./csp/csp.module').then(m => m.CspModule),
      },
      {
        path: 'fiche-suivi-sante',
        data: { pageTitle: 'esacpApp.ficheSuiviSante.home.title' },
        loadChildren: () => import('./fiche-suivi-sante/fiche-suivi-sante.module').then(m => m.FicheSuiviSanteModule),
      },
      {
        path: 'actions',
        data: { pageTitle: 'esacpApp.actions.home.title' },
        loadChildren: () => import('./actions/actions.module').then(m => m.ActionsModule),
      },
      {
        path: 'categorie',
        data: { pageTitle: 'esacpApp.categorie.home.title' },
        loadChildren: () => import('./categorie/categorie.module').then(m => m.CategorieModule),
      },
      {
        path: 'contrat',
        data: { pageTitle: 'esacpApp.contrat.home.title' },
        loadChildren: () => import('./contrat/contrat.module').then(m => m.ContratModule),
      },
      {
        path: 'criticite',
        data: { pageTitle: 'esacpApp.criticite.home.title' },
        loadChildren: () => import('./criticite/criticite.module').then(m => m.CriticiteModule),
      },
      {
        path: 'equipement',
        data: { pageTitle: 'esacpApp.equipement.home.title' },
        loadChildren: () => import('./equipement/equipement.module').then(m => m.EquipementModule),
      },
      {
        path: 'etape-validation',
        data: { pageTitle: 'esacpApp.etapeValidation.home.title' },
        loadChildren: () => import('./etape-validation/etape-validation.module').then(m => m.EtapeValidationModule),
      },
      {
        path: 'listing-mail',
        data: { pageTitle: 'esacpApp.listingMail.home.title' },
        loadChildren: () => import('./listing-mail/listing-mail.module').then(m => m.ListingMailModule),
      },
      {
        path: 'mail',
        data: { pageTitle: 'esacpApp.mail.home.title' },
        loadChildren: () => import('./mail/mail.module').then(m => m.MailModule),
      },
      {
        path: 'nature-accident',
        data: { pageTitle: 'esacpApp.natureAccident.home.title' },
        loadChildren: () => import('./nature-accident/nature-accident.module').then(m => m.NatureAccidentModule),
      },
      {
        path: 'origine-lesions',
        data: { pageTitle: 'esacpApp.origineLesions.home.title' },
        loadChildren: () => import('./origine-lesions/origine-lesions.module').then(m => m.OrigineLesionsModule),
      },
      {
        path: 'piece-jointes',
        data: { pageTitle: 'esacpApp.pieceJointes.home.title' },
        loadChildren: () => import('./piece-jointes/piece-jointes.module').then(m => m.PieceJointesModule),
      },
      {
        path: 'priorite',
        data: { pageTitle: 'esacpApp.priorite.home.title' },
        loadChildren: () => import('./priorite/priorite.module').then(m => m.PrioriteModule),
      },
      {
        path: 'rapport',
        data: { pageTitle: 'esacpApp.rapport.home.title' },
        loadChildren: () => import('./rapport/rapport.module').then(m => m.RapportModule),
      },
      {
        path: 'departement',
        data: { pageTitle: 'esacpApp.departement.home.title' },
        loadChildren: () => import('./departement/departement.module').then(m => m.DepartementModule),
      },
      {
        path: 'siege-lesions',
        data: { pageTitle: 'esacpApp.siegeLesions.home.title' },
        loadChildren: () => import('./siege-lesions/siege-lesions.module').then(m => m.SiegeLesionsModule),
      },
      {
        path: 'site',
        data: { pageTitle: 'esacpApp.site.home.title' },
        loadChildren: () => import('./site/site.module').then(m => m.SiteModule),
      },
      {
        path: 'statut',
        data: { pageTitle: 'esacpApp.statut.home.title' },
        loadChildren: () => import('./statut/statut.module').then(m => m.StatutModule),
      },
      {
        path: 'type',
        data: { pageTitle: 'esacpApp.type.home.title' },
        loadChildren: () => import('./type/type.module').then(m => m.TypeModule),
      },
      {
        path: 'repartition',
        data: { pageTitle: 'esacpApp.repartition.home.title' },
        loadChildren: () => import('./repartition/repartition.module').then(m => m.RepartitionModule),
      },
      {
        path: 'type-at',
        data: { pageTitle: 'esacpApp.typeAt.home.title' },
        loadChildren: () => import('./type-at/type-at.module').then(m => m.TypeAtModule),
      },
      {
        path: 'type-action',
        data: { pageTitle: 'esacpApp.typeAction.home.title' },
        loadChildren: () => import('./type-action/type-action.module').then(m => m.TypeActionModule),
      },
      {
        path: 'type-rapport',
        data: { pageTitle: 'esacpApp.typeRapport.home.title' },
        loadChildren: () => import('./type-rapport/type-rapport.module').then(m => m.TypeRapportModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
