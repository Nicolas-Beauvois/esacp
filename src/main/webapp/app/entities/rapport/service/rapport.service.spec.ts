import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRapport, Rapport } from '../rapport.model';

import { RapportService } from './rapport.service';

describe('Service Tests', () => {
  describe('Rapport Service', () => {
    let service: RapportService;
    let httpMock: HttpTestingController;
    let elemDefault: IRapport;
    let expectedResult: IRapport | IRapport[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RapportService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        redacteur: 'AAAAAAA',
        dateDeCreation: currentDate,
        uap: 'AAAAAAA',
        dateEtHeureConnaissanceAt: currentDate,
        prevenuComment: 'AAAAAAA',
        nomPremierePersonnePrevenu: 'AAAAAAA',
        dateEtHeurePrevenu: currentDate,
        isTemoin: false,
        commentaireTemoin: 'AAAAAAA',
        isTiersEnCause: false,
        commentaireTiersEnCause: 'AAAAAAA',
        isAutreVictime: false,
        commentaireAutreVictime: 'AAAAAAA',
        isRapportDePolice: false,
        commentaireRapportDePolice: 'AAAAAAA',
        isVictimeTransports: false,
        commentaireVictimeTransporter: 'AAAAAAA',
        dateEtHeureMomentAccident: currentDate,
        lieuAccident: 'AAAAAAA',
        isIdentifierDu: false,
        circonstanceAccident: 'AAAAAAA',
        materielEnCause: 'AAAAAAA',
        remarques: 'AAAAAAA',
        pilote: 'AAAAAAA',
        dateEtHeureValidationPilote: currentDate,
        porteur: 'AAAAAAA',
        dateEtHeureValidationPorteur: currentDate,
        hse: 'AAAAAAA',
        dateEtHeureValidationHse: currentDate,
        isIntervention8300: false,
        isInterventionInfirmiere: false,
        commentaireInfirmere: 'AAAAAAA',
        isInterventionMedecin: false,
        commentaireMedecin: 'AAAAAAA',
        isInterventionsecouriste: false,
        commentaireSecouriste: 'AAAAAAA',
        isInterventionsecouristeExterieur: false,
        retourAuPosteDeTravail: false,
        travailLegerPossible: 'AAAAAAA',
        analyseAChaudInfirmiere: false,
        analyseAChaudCodir: false,
        analyseAChaudHse: false,
        analyseAChaudNplus1: false,
        analyseAChaudCssCt: false,
        analyseAChaudCommentaire: 'AAAAAAA',
        pourquoi1: 'AAAAAAA',
        pourquoi2: 'AAAAAAA',
        pourquoi3: 'AAAAAAA',
        pourquoi4: 'AAAAAAA',
        pourquoi5: 'AAAAAAA',
        bras: false,
        chevilles: false,
        colonne: false,
        cou: false,
        coude: false,
        cos: false,
        epaule: false,
        genou: false,
        jambes: false,
        mains: false,
        oeil: false,
        pieds: false,
        poignet: false,
        tete: false,
        torse: false,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateDeCreation: currentDate.format(DATE_FORMAT),
            dateEtHeureConnaissanceAt: currentDate.format(DATE_FORMAT),
            dateEtHeurePrevenu: currentDate.format(DATE_FORMAT),
            dateEtHeureMomentAccident: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationPilote: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationHse: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Rapport', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateDeCreation: currentDate.format(DATE_FORMAT),
            dateEtHeureConnaissanceAt: currentDate.format(DATE_FORMAT),
            dateEtHeurePrevenu: currentDate.format(DATE_FORMAT),
            dateEtHeureMomentAccident: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationPilote: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
            dateEtHeureValidationHse: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeCreation: currentDate,
            dateEtHeureConnaissanceAt: currentDate,
            dateEtHeurePrevenu: currentDate,
            dateEtHeureMomentAccident: currentDate,
            dateEtHeureValidationPilote: currentDate,
            dateEtHeureValidationPorteur: currentDate,
            dateEtHeureValidationHse: currentDate,
          },
          returnedFromService
        );

        service.create(new Rapport()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Rapport', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            redacteur: 'BBBBBB',
            dateDeCreation: currentDate.format(DATE_FORMAT),
            uap: 'BBBBBB',
            dateEtHeureConnaissanceAt: currentDate.format(DATE_FORMAT),
            prevenuComment: 'BBBBBB',
            nomPremierePersonnePrevenu: 'BBBBBB',
            dateEtHeurePrevenu: currentDate.format(DATE_FORMAT),
            isTemoin: true,
            commentaireTemoin: 'BBBBBB',
            isTiersEnCause: true,
            commentaireTiersEnCause: 'BBBBBB',
            isAutreVictime: true,
            commentaireAutreVictime: 'BBBBBB',
            isRapportDePolice: true,
            commentaireRapportDePolice: 'BBBBBB',
            isVictimeTransports: true,
            commentaireVictimeTransporter: 'BBBBBB',
            dateEtHeureMomentAccident: currentDate.format(DATE_FORMAT),
            lieuAccident: 'BBBBBB',
            isIdentifierDu: true,
            circonstanceAccident: 'BBBBBB',
            materielEnCause: 'BBBBBB',
            remarques: 'BBBBBB',
            pilote: 'BBBBBB',
            dateEtHeureValidationPilote: currentDate.format(DATE_FORMAT),
            porteur: 'BBBBBB',
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
            hse: 'BBBBBB',
            dateEtHeureValidationHse: currentDate.format(DATE_FORMAT),
            isIntervention8300: true,
            isInterventionInfirmiere: true,
            commentaireInfirmere: 'BBBBBB',
            isInterventionMedecin: true,
            commentaireMedecin: 'BBBBBB',
            isInterventionsecouriste: true,
            commentaireSecouriste: 'BBBBBB',
            isInterventionsecouristeExterieur: true,
            retourAuPosteDeTravail: true,
            travailLegerPossible: 'BBBBBB',
            analyseAChaudInfirmiere: true,
            analyseAChaudCodir: true,
            analyseAChaudHse: true,
            analyseAChaudNplus1: true,
            analyseAChaudCssCt: true,
            analyseAChaudCommentaire: 'BBBBBB',
            pourquoi1: 'BBBBBB',
            pourquoi2: 'BBBBBB',
            pourquoi3: 'BBBBBB',
            pourquoi4: 'BBBBBB',
            pourquoi5: 'BBBBBB',
            bras: true,
            chevilles: true,
            colonne: true,
            cou: true,
            coude: true,
            cos: true,
            epaule: true,
            genou: true,
            jambes: true,
            mains: true,
            oeil: true,
            pieds: true,
            poignet: true,
            tete: true,
            torse: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeCreation: currentDate,
            dateEtHeureConnaissanceAt: currentDate,
            dateEtHeurePrevenu: currentDate,
            dateEtHeureMomentAccident: currentDate,
            dateEtHeureValidationPilote: currentDate,
            dateEtHeureValidationPorteur: currentDate,
            dateEtHeureValidationHse: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Rapport', () => {
        const patchObject = Object.assign(
          {
            redacteur: 'BBBBBB',
            dateDeCreation: currentDate.format(DATE_FORMAT),
            dateEtHeureConnaissanceAt: currentDate.format(DATE_FORMAT),
            prevenuComment: 'BBBBBB',
            nomPremierePersonnePrevenu: 'BBBBBB',
            isTemoin: true,
            isAutreVictime: true,
            commentaireAutreVictime: 'BBBBBB',
            isRapportDePolice: true,
            isVictimeTransports: true,
            dateEtHeureMomentAccident: currentDate.format(DATE_FORMAT),
            lieuAccident: 'BBBBBB',
            pilote: 'BBBBBB',
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
            hse: 'BBBBBB',
            isIntervention8300: true,
            isInterventionMedecin: true,
            commentaireSecouriste: 'BBBBBB',
            travailLegerPossible: 'BBBBBB',
            analyseAChaudCodir: true,
            analyseAChaudHse: true,
            analyseAChaudCssCt: true,
            pourquoi1: 'BBBBBB',
            pourquoi2: 'BBBBBB',
            epaule: true,
            jambes: true,
            mains: true,
            oeil: true,
            pieds: true,
            poignet: true,
            tete: true,
          },
          new Rapport()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateDeCreation: currentDate,
            dateEtHeureConnaissanceAt: currentDate,
            dateEtHeurePrevenu: currentDate,
            dateEtHeureMomentAccident: currentDate,
            dateEtHeureValidationPilote: currentDate,
            dateEtHeureValidationPorteur: currentDate,
            dateEtHeureValidationHse: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Rapport', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            redacteur: 'BBBBBB',
            dateDeCreation: currentDate.format(DATE_FORMAT),
            uap: 'BBBBBB',
            dateEtHeureConnaissanceAt: currentDate.format(DATE_FORMAT),
            prevenuComment: 'BBBBBB',
            nomPremierePersonnePrevenu: 'BBBBBB',
            dateEtHeurePrevenu: currentDate.format(DATE_FORMAT),
            isTemoin: true,
            commentaireTemoin: 'BBBBBB',
            isTiersEnCause: true,
            commentaireTiersEnCause: 'BBBBBB',
            isAutreVictime: true,
            commentaireAutreVictime: 'BBBBBB',
            isRapportDePolice: true,
            commentaireRapportDePolice: 'BBBBBB',
            isVictimeTransports: true,
            commentaireVictimeTransporter: 'BBBBBB',
            dateEtHeureMomentAccident: currentDate.format(DATE_FORMAT),
            lieuAccident: 'BBBBBB',
            isIdentifierDu: true,
            circonstanceAccident: 'BBBBBB',
            materielEnCause: 'BBBBBB',
            remarques: 'BBBBBB',
            pilote: 'BBBBBB',
            dateEtHeureValidationPilote: currentDate.format(DATE_FORMAT),
            porteur: 'BBBBBB',
            dateEtHeureValidationPorteur: currentDate.format(DATE_FORMAT),
            hse: 'BBBBBB',
            dateEtHeureValidationHse: currentDate.format(DATE_FORMAT),
            isIntervention8300: true,
            isInterventionInfirmiere: true,
            commentaireInfirmere: 'BBBBBB',
            isInterventionMedecin: true,
            commentaireMedecin: 'BBBBBB',
            isInterventionsecouriste: true,
            commentaireSecouriste: 'BBBBBB',
            isInterventionsecouristeExterieur: true,
            retourAuPosteDeTravail: true,
            travailLegerPossible: 'BBBBBB',
            analyseAChaudInfirmiere: true,
            analyseAChaudCodir: true,
            analyseAChaudHse: true,
            analyseAChaudNplus1: true,
            analyseAChaudCssCt: true,
            analyseAChaudCommentaire: 'BBBBBB',
            pourquoi1: 'BBBBBB',
            pourquoi2: 'BBBBBB',
            pourquoi3: 'BBBBBB',
            pourquoi4: 'BBBBBB',
            pourquoi5: 'BBBBBB',
            bras: true,
            chevilles: true,
            colonne: true,
            cou: true,
            coude: true,
            cos: true,
            epaule: true,
            genou: true,
            jambes: true,
            mains: true,
            oeil: true,
            pieds: true,
            poignet: true,
            tete: true,
            torse: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateDeCreation: currentDate,
            dateEtHeureConnaissanceAt: currentDate,
            dateEtHeurePrevenu: currentDate,
            dateEtHeureMomentAccident: currentDate,
            dateEtHeureValidationPilote: currentDate,
            dateEtHeureValidationPorteur: currentDate,
            dateEtHeureValidationHse: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Rapport', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRapportToCollectionIfMissing', () => {
        it('should add a Rapport to an empty array', () => {
          const rapport: IRapport = { id: 123 };
          expectedResult = service.addRapportToCollectionIfMissing([], rapport);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rapport);
        });

        it('should not add a Rapport to an array that contains it', () => {
          const rapport: IRapport = { id: 123 };
          const rapportCollection: IRapport[] = [
            {
              ...rapport,
            },
            { id: 456 },
          ];
          expectedResult = service.addRapportToCollectionIfMissing(rapportCollection, rapport);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Rapport to an array that doesn't contain it", () => {
          const rapport: IRapport = { id: 123 };
          const rapportCollection: IRapport[] = [{ id: 456 }];
          expectedResult = service.addRapportToCollectionIfMissing(rapportCollection, rapport);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rapport);
        });

        it('should add only unique Rapport to an array', () => {
          const rapportArray: IRapport[] = [{ id: 123 }, { id: 456 }, { id: 58179 }];
          const rapportCollection: IRapport[] = [{ id: 123 }];
          expectedResult = service.addRapportToCollectionIfMissing(rapportCollection, ...rapportArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const rapport: IRapport = { id: 123 };
          const rapport2: IRapport = { id: 456 };
          expectedResult = service.addRapportToCollectionIfMissing([], rapport, rapport2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(rapport);
          expect(expectedResult).toContain(rapport2);
        });

        it('should accept null and undefined values', () => {
          const rapport: IRapport = { id: 123 };
          expectedResult = service.addRapportToCollectionIfMissing([], null, rapport, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(rapport);
        });

        it('should return initial array if no Rapport is added', () => {
          const rapportCollection: IRapport[] = [{ id: 123 }];
          expectedResult = service.addRapportToCollectionIfMissing(rapportCollection, undefined, null);
          expect(expectedResult).toEqual(rapportCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
