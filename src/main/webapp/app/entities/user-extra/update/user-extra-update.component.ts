import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IUserExtra, UserExtra } from '../user-extra.model';
import { UserExtraService } from '../service/user-extra.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { IStatut } from 'app/entities/statut/statut.model';
import { StatutService } from 'app/entities/statut/service/statut.service';
import { ISexe } from 'app/entities/sexe/sexe.model';
import { SexeService } from 'app/entities/sexe/service/sexe.service';
import { IDepartement } from 'app/entities/departement/departement.model';
import { DepartementService } from 'app/entities/departement/service/departement.service';
import { IContrat } from 'app/entities/contrat/contrat.model';
import { ContratService } from 'app/entities/contrat/service/contrat.service';
import { ISite } from 'app/entities/site/site.model';
import { SiteService } from 'app/entities/site/service/site.service';
import { ICsp } from 'app/entities/csp/csp.model';
import { CspService } from 'app/entities/csp/service/csp.service';

@Component({
  selector: 'jhi-user-extra-update',
  templateUrl: './user-extra-update.component.html',
})
export class UserExtraUpdateComponent implements OnInit {
  isSaving = false;

  usersSharedCollection: IUser[] = [];
  statutsSharedCollection: IStatut[] = [];
  sexesSharedCollection: ISexe[] = [];
  departementsSharedCollection: IDepartement[] = [];
  contratsSharedCollection: IContrat[] = [];
  sitesSharedCollection: ISite[] = [];
  cspsSharedCollection: ICsp[] = [];

  editForm = this.fb.group({
    id: [],
    matricule: [],
    dateDeNaissance: [],
    isRedacteur: [],
    isPilote: [],
    isPorteur: [],
    isCodir: [],
    isHse: [],
    isValidateurHse: [],
    user: [],
    statut: [],
    sexe: [],
    departement: [],
    contrat: [],
    site: [],
    csp: [],
  });

  constructor(
    protected userExtraService: UserExtraService,
    protected userService: UserService,
    protected statutService: StatutService,
    protected sexeService: SexeService,
    protected departementService: DepartementService,
    protected contratService: ContratService,
    protected siteService: SiteService,
    protected cspService: CspService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userExtra }) => {
      this.updateForm(userExtra);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userExtra = this.createFromForm();
    if (userExtra.id !== undefined) {
      this.subscribeToSaveResponse(this.userExtraService.update(userExtra));
    } else {
      this.subscribeToSaveResponse(this.userExtraService.create(userExtra));
    }
  }

  trackUserById(index: number, item: IUser): number {
    return item.id!;
  }

  trackStatutById(index: number, item: IStatut): number {
    return item.id!;
  }

  trackSexeById(index: number, item: ISexe): number {
    return item.id!;
  }

  trackDepartementById(index: number, item: IDepartement): number {
    return item.id!;
  }

  trackContratById(index: number, item: IContrat): number {
    return item.id!;
  }

  trackSiteById(index: number, item: ISite): number {
    return item.id!;
  }

  trackCspById(index: number, item: ICsp): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserExtra>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(userExtra: IUserExtra): void {
    this.editForm.patchValue({
      id: userExtra.id,
      matricule: userExtra.matricule,
      dateDeNaissance: userExtra.dateDeNaissance,
      isRedacteur: userExtra.isRedacteur,
      isPilote: userExtra.isPilote,
      isPorteur: userExtra.isPorteur,
      isCodir: userExtra.isCodir,
      isHse: userExtra.isHse,
      isValidateurHse: userExtra.isValidateurHse,
      user: userExtra.user,
      statut: userExtra.statut,
      sexe: userExtra.sexe,
      departement: userExtra.departement,
      contrat: userExtra.contrat,
      site: userExtra.site,
      csp: userExtra.csp,
    });

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing(this.usersSharedCollection, userExtra.user);
    this.statutsSharedCollection = this.statutService.addStatutToCollectionIfMissing(this.statutsSharedCollection, userExtra.statut);
    this.sexesSharedCollection = this.sexeService.addSexeToCollectionIfMissing(this.sexesSharedCollection, userExtra.sexe);
    this.departementsSharedCollection = this.departementService.addDepartementToCollectionIfMissing(
      this.departementsSharedCollection,
      userExtra.departement
    );
    this.contratsSharedCollection = this.contratService.addContratToCollectionIfMissing(this.contratsSharedCollection, userExtra.contrat);
    this.sitesSharedCollection = this.siteService.addSiteToCollectionIfMissing(this.sitesSharedCollection, userExtra.site);
    this.cspsSharedCollection = this.cspService.addCspToCollectionIfMissing(this.cspsSharedCollection, userExtra.csp);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing(users, this.editForm.get('user')!.value)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));

    this.statutService
      .query()
      .pipe(map((res: HttpResponse<IStatut[]>) => res.body ?? []))
      .pipe(map((statuts: IStatut[]) => this.statutService.addStatutToCollectionIfMissing(statuts, this.editForm.get('statut')!.value)))
      .subscribe((statuts: IStatut[]) => (this.statutsSharedCollection = statuts));

    this.sexeService
      .query()
      .pipe(map((res: HttpResponse<ISexe[]>) => res.body ?? []))
      .pipe(map((sexes: ISexe[]) => this.sexeService.addSexeToCollectionIfMissing(sexes, this.editForm.get('sexe')!.value)))
      .subscribe((sexes: ISexe[]) => (this.sexesSharedCollection = sexes));

    this.departementService
      .query()
      .pipe(map((res: HttpResponse<IDepartement[]>) => res.body ?? []))
      .pipe(
        map((departements: IDepartement[]) =>
          this.departementService.addDepartementToCollectionIfMissing(departements, this.editForm.get('departement')!.value)
        )
      )
      .subscribe((departements: IDepartement[]) => (this.departementsSharedCollection = departements));

    this.contratService
      .query()
      .pipe(map((res: HttpResponse<IContrat[]>) => res.body ?? []))
      .pipe(
        map((contrats: IContrat[]) => this.contratService.addContratToCollectionIfMissing(contrats, this.editForm.get('contrat')!.value))
      )
      .subscribe((contrats: IContrat[]) => (this.contratsSharedCollection = contrats));

    this.siteService
      .query()
      .pipe(map((res: HttpResponse<ISite[]>) => res.body ?? []))
      .pipe(map((sites: ISite[]) => this.siteService.addSiteToCollectionIfMissing(sites, this.editForm.get('site')!.value)))
      .subscribe((sites: ISite[]) => (this.sitesSharedCollection = sites));

    this.cspService
      .query()
      .pipe(map((res: HttpResponse<ICsp[]>) => res.body ?? []))
      .pipe(map((csps: ICsp[]) => this.cspService.addCspToCollectionIfMissing(csps, this.editForm.get('csp')!.value)))
      .subscribe((csps: ICsp[]) => (this.cspsSharedCollection = csps));
  }

  protected createFromForm(): IUserExtra {
    return {
      ...new UserExtra(),
      id: this.editForm.get(['id'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      dateDeNaissance: this.editForm.get(['dateDeNaissance'])!.value,
      isRedacteur: this.editForm.get(['isRedacteur'])!.value,
      isPilote: this.editForm.get(['isPilote'])!.value,
      isPorteur: this.editForm.get(['isPorteur'])!.value,
      isCodir: this.editForm.get(['isCodir'])!.value,
      isHse: this.editForm.get(['isHse'])!.value,
      isValidateurHse: this.editForm.get(['isValidateurHse'])!.value,
      user: this.editForm.get(['user'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      departement: this.editForm.get(['departement'])!.value,
      contrat: this.editForm.get(['contrat'])!.value,
      site: this.editForm.get(['site'])!.value,
      csp: this.editForm.get(['csp'])!.value,
    };
  }
}
