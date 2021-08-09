package fr.delpharm.esacp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.delpharm.esacp.IntegrationTest;
import fr.delpharm.esacp.domain.Rapport;
import fr.delpharm.esacp.repository.RapportRepository;
import fr.delpharm.esacp.service.dto.RapportDTO;
import fr.delpharm.esacp.service.mapper.RapportMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RapportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RapportResourceIT {

    private static final String DEFAULT_REDACTEUR = "AAAAAAAAAA";
    private static final String UPDATED_REDACTEUR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UAP = "AAAAAAAAAA";
    private static final String UPDATED_UAP = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ET_HEURE_CONNAISSANCE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_CONNAISSANCE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PREVENU_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_PREVENU_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PREMIERE_PERSONNE_PREVENU = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PREMIERE_PERSONNE_PREVENU = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ET_HEURE_PREVENU = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_PREVENU = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_TEMOIN = false;
    private static final Boolean UPDATED_IS_TEMOIN = true;

    private static final String DEFAULT_COMMENTAIRE_TEMOIN = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_TEMOIN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_TIERS_EN_CAUSE = false;
    private static final Boolean UPDATED_IS_TIERS_EN_CAUSE = true;

    private static final String DEFAULT_COMMENTAIRE_TIERS_EN_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_TIERS_EN_CAUSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_AUTRE_VICTIME = false;
    private static final Boolean UPDATED_IS_AUTRE_VICTIME = true;

    private static final String DEFAULT_COMMENTAIRE_AUTRE_VICTIME = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_AUTRE_VICTIME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_RAPPORT_DE_POLICE = false;
    private static final Boolean UPDATED_IS_RAPPORT_DE_POLICE = true;

    private static final String DEFAULT_COMMENTAIRE_RAPPORT_DE_POLICE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_RAPPORT_DE_POLICE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VICTIME_TRANSPORTS = false;
    private static final Boolean UPDATED_IS_VICTIME_TRANSPORTS = true;

    private static final String DEFAULT_COMMENTAIRE_VICTIME_TRANSPORTER = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_VICTIME_TRANSPORTER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ET_HEURE_MOMENT_ACCIDENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_MOMENT_ACCIDENT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_ACCIDENT = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_ACCIDENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_IDENTIFIER_DU = false;
    private static final Boolean UPDATED_IS_IDENTIFIER_DU = true;

    private static final String DEFAULT_CIRCONSTANCE_ACCIDENT = "AAAAAAAAAA";
    private static final String UPDATED_CIRCONSTANCE_ACCIDENT = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIEL_EN_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIEL_EN_CAUSE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARQUES = "AAAAAAAAAA";
    private static final String UPDATED_REMARQUES = "BBBBBBBBBB";

    private static final String DEFAULT_PILOTE = "AAAAAAAAAA";
    private static final String UPDATED_PILOTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PORTEUR = "AAAAAAAAAA";
    private static final String UPDATED_PORTEUR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HSE = "AAAAAAAAAA";
    private static final String UPDATED_HSE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ET_HEURE_VALIDATION_HSE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ET_HEURE_VALIDATION_HSE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_INTERVENTION_8300 = false;
    private static final Boolean UPDATED_IS_INTERVENTION_8300 = true;

    private static final Boolean DEFAULT_IS_INTERVENTION_INFIRMIERE = false;
    private static final Boolean UPDATED_IS_INTERVENTION_INFIRMIERE = true;

    private static final String DEFAULT_COMMENTAIRE_INFIRMERE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_INFIRMERE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_INTERVENTION_MEDECIN = false;
    private static final Boolean UPDATED_IS_INTERVENTION_MEDECIN = true;

    private static final String DEFAULT_COMMENTAIRE_MEDECIN = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_MEDECIN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_INTERVENTIONSECOURISTE = false;
    private static final Boolean UPDATED_IS_INTERVENTIONSECOURISTE = true;

    private static final String DEFAULT_COMMENTAIRE_SECOURISTE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE_SECOURISTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_INTERVENTIONSECOURISTE_EXTERIEUR = false;
    private static final Boolean UPDATED_IS_INTERVENTIONSECOURISTE_EXTERIEUR = true;

    private static final Boolean DEFAULT_RETOUR_AU_POSTE_DE_TRAVAIL = false;
    private static final Boolean UPDATED_RETOUR_AU_POSTE_DE_TRAVAIL = true;

    private static final String DEFAULT_TRAVAIL_LEGER_POSSIBLE = "AAAAAAAAAA";
    private static final String UPDATED_TRAVAIL_LEGER_POSSIBLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANALYSE_A_CHAUD_INFIRMIERE = false;
    private static final Boolean UPDATED_ANALYSE_A_CHAUD_INFIRMIERE = true;

    private static final Boolean DEFAULT_ANALYSE_A_CHAUD_CODIR = false;
    private static final Boolean UPDATED_ANALYSE_A_CHAUD_CODIR = true;

    private static final Boolean DEFAULT_ANALYSE_A_CHAUD_HSE = false;
    private static final Boolean UPDATED_ANALYSE_A_CHAUD_HSE = true;

    private static final Boolean DEFAULT_ANALYSE_A_CHAUD_NPLUS_1 = false;
    private static final Boolean UPDATED_ANALYSE_A_CHAUD_NPLUS_1 = true;

    private static final Boolean DEFAULT_ANALYSE_A_CHAUD_CSS_CT = false;
    private static final Boolean UPDATED_ANALYSE_A_CHAUD_CSS_CT = true;

    private static final String DEFAULT_ANALYSE_A_CHAUD_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_ANALYSE_A_CHAUD_COMMENTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_POURQUOI_1 = "AAAAAAAAAA";
    private static final String UPDATED_POURQUOI_1 = "BBBBBBBBBB";

    private static final String DEFAULT_POURQUOI_2 = "AAAAAAAAAA";
    private static final String UPDATED_POURQUOI_2 = "BBBBBBBBBB";

    private static final String DEFAULT_POURQUOI_3 = "AAAAAAAAAA";
    private static final String UPDATED_POURQUOI_3 = "BBBBBBBBBB";

    private static final String DEFAULT_POURQUOI_4 = "AAAAAAAAAA";
    private static final String UPDATED_POURQUOI_4 = "BBBBBBBBBB";

    private static final String DEFAULT_POURQUOI_5 = "AAAAAAAAAA";
    private static final String UPDATED_POURQUOI_5 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_BRAS = false;
    private static final Boolean UPDATED_BRAS = true;

    private static final Boolean DEFAULT_CHEVILLES = false;
    private static final Boolean UPDATED_CHEVILLES = true;

    private static final Boolean DEFAULT_COLONNE = false;
    private static final Boolean UPDATED_COLONNE = true;

    private static final Boolean DEFAULT_COU = false;
    private static final Boolean UPDATED_COU = true;

    private static final Boolean DEFAULT_COUDE = false;
    private static final Boolean UPDATED_COUDE = true;

    private static final Boolean DEFAULT_COS = false;
    private static final Boolean UPDATED_COS = true;

    private static final Boolean DEFAULT_EPAULE = false;
    private static final Boolean UPDATED_EPAULE = true;

    private static final Boolean DEFAULT_GENOU = false;
    private static final Boolean UPDATED_GENOU = true;

    private static final Boolean DEFAULT_JAMBES = false;
    private static final Boolean UPDATED_JAMBES = true;

    private static final Boolean DEFAULT_MAINS = false;
    private static final Boolean UPDATED_MAINS = true;

    private static final Boolean DEFAULT_OEIL = false;
    private static final Boolean UPDATED_OEIL = true;

    private static final Boolean DEFAULT_PIEDS = false;
    private static final Boolean UPDATED_PIEDS = true;

    private static final Boolean DEFAULT_POIGNET = false;
    private static final Boolean UPDATED_POIGNET = true;

    private static final Boolean DEFAULT_TETE = false;
    private static final Boolean UPDATED_TETE = true;

    private static final Boolean DEFAULT_TORSE = false;
    private static final Boolean UPDATED_TORSE = true;

    private static final String ENTITY_API_URL = "/api/rapports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private RapportMapper rapportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRapportMockMvc;

    private Rapport rapport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rapport createEntity(EntityManager em) {
        Rapport rapport = new Rapport()
            .redacteur(DEFAULT_REDACTEUR)
            .dateDeCreation(DEFAULT_DATE_DE_CREATION)
            .uap(DEFAULT_UAP)
            .dateEtHeureConnaissanceAt(DEFAULT_DATE_ET_HEURE_CONNAISSANCE_AT)
            .prevenuComment(DEFAULT_PREVENU_COMMENT)
            .nomPremierePersonnePrevenu(DEFAULT_NOM_PREMIERE_PERSONNE_PREVENU)
            .dateEtHeurePrevenu(DEFAULT_DATE_ET_HEURE_PREVENU)
            .isTemoin(DEFAULT_IS_TEMOIN)
            .commentaireTemoin(DEFAULT_COMMENTAIRE_TEMOIN)
            .isTiersEnCause(DEFAULT_IS_TIERS_EN_CAUSE)
            .commentaireTiersEnCause(DEFAULT_COMMENTAIRE_TIERS_EN_CAUSE)
            .isAutreVictime(DEFAULT_IS_AUTRE_VICTIME)
            .commentaireAutreVictime(DEFAULT_COMMENTAIRE_AUTRE_VICTIME)
            .isRapportDePolice(DEFAULT_IS_RAPPORT_DE_POLICE)
            .commentaireRapportDePolice(DEFAULT_COMMENTAIRE_RAPPORT_DE_POLICE)
            .isVictimeTransports(DEFAULT_IS_VICTIME_TRANSPORTS)
            .commentaireVictimeTransporter(DEFAULT_COMMENTAIRE_VICTIME_TRANSPORTER)
            .dateEtHeureMomentAccident(DEFAULT_DATE_ET_HEURE_MOMENT_ACCIDENT)
            .lieuAccident(DEFAULT_LIEU_ACCIDENT)
            .isIdentifierDu(DEFAULT_IS_IDENTIFIER_DU)
            .circonstanceAccident(DEFAULT_CIRCONSTANCE_ACCIDENT)
            .materielEnCause(DEFAULT_MATERIEL_EN_CAUSE)
            .remarques(DEFAULT_REMARQUES)
            .pilote(DEFAULT_PILOTE)
            .dateEtHeureValidationPilote(DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE)
            .porteur(DEFAULT_PORTEUR)
            .dateEtHeureValidationPorteur(DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR)
            .hse(DEFAULT_HSE)
            .dateEtHeureValidationHse(DEFAULT_DATE_ET_HEURE_VALIDATION_HSE)
            .isIntervention8300(DEFAULT_IS_INTERVENTION_8300)
            .isInterventionInfirmiere(DEFAULT_IS_INTERVENTION_INFIRMIERE)
            .commentaireInfirmere(DEFAULT_COMMENTAIRE_INFIRMERE)
            .isInterventionMedecin(DEFAULT_IS_INTERVENTION_MEDECIN)
            .commentaireMedecin(DEFAULT_COMMENTAIRE_MEDECIN)
            .isInterventionsecouriste(DEFAULT_IS_INTERVENTIONSECOURISTE)
            .commentaireSecouriste(DEFAULT_COMMENTAIRE_SECOURISTE)
            .isInterventionsecouristeExterieur(DEFAULT_IS_INTERVENTIONSECOURISTE_EXTERIEUR)
            .retourAuPosteDeTravail(DEFAULT_RETOUR_AU_POSTE_DE_TRAVAIL)
            .travailLegerPossible(DEFAULT_TRAVAIL_LEGER_POSSIBLE)
            .analyseAChaudInfirmiere(DEFAULT_ANALYSE_A_CHAUD_INFIRMIERE)
            .analyseAChaudCodir(DEFAULT_ANALYSE_A_CHAUD_CODIR)
            .analyseAChaudHse(DEFAULT_ANALYSE_A_CHAUD_HSE)
            .analyseAChaudNplus1(DEFAULT_ANALYSE_A_CHAUD_NPLUS_1)
            .analyseAChaudCssCt(DEFAULT_ANALYSE_A_CHAUD_CSS_CT)
            .analyseAChaudCommentaire(DEFAULT_ANALYSE_A_CHAUD_COMMENTAIRE)
            .pourquoi1(DEFAULT_POURQUOI_1)
            .pourquoi2(DEFAULT_POURQUOI_2)
            .pourquoi3(DEFAULT_POURQUOI_3)
            .pourquoi4(DEFAULT_POURQUOI_4)
            .pourquoi5(DEFAULT_POURQUOI_5)
            .bras(DEFAULT_BRAS)
            .chevilles(DEFAULT_CHEVILLES)
            .colonne(DEFAULT_COLONNE)
            .cou(DEFAULT_COU)
            .coude(DEFAULT_COUDE)
            .cos(DEFAULT_COS)
            .epaule(DEFAULT_EPAULE)
            .genou(DEFAULT_GENOU)
            .jambes(DEFAULT_JAMBES)
            .mains(DEFAULT_MAINS)
            .oeil(DEFAULT_OEIL)
            .pieds(DEFAULT_PIEDS)
            .poignet(DEFAULT_POIGNET)
            .tete(DEFAULT_TETE)
            .torse(DEFAULT_TORSE);
        return rapport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rapport createUpdatedEntity(EntityManager em) {
        Rapport rapport = new Rapport()
            .redacteur(UPDATED_REDACTEUR)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .uap(UPDATED_UAP)
            .dateEtHeureConnaissanceAt(UPDATED_DATE_ET_HEURE_CONNAISSANCE_AT)
            .prevenuComment(UPDATED_PREVENU_COMMENT)
            .nomPremierePersonnePrevenu(UPDATED_NOM_PREMIERE_PERSONNE_PREVENU)
            .dateEtHeurePrevenu(UPDATED_DATE_ET_HEURE_PREVENU)
            .isTemoin(UPDATED_IS_TEMOIN)
            .commentaireTemoin(UPDATED_COMMENTAIRE_TEMOIN)
            .isTiersEnCause(UPDATED_IS_TIERS_EN_CAUSE)
            .commentaireTiersEnCause(UPDATED_COMMENTAIRE_TIERS_EN_CAUSE)
            .isAutreVictime(UPDATED_IS_AUTRE_VICTIME)
            .commentaireAutreVictime(UPDATED_COMMENTAIRE_AUTRE_VICTIME)
            .isRapportDePolice(UPDATED_IS_RAPPORT_DE_POLICE)
            .commentaireRapportDePolice(UPDATED_COMMENTAIRE_RAPPORT_DE_POLICE)
            .isVictimeTransports(UPDATED_IS_VICTIME_TRANSPORTS)
            .commentaireVictimeTransporter(UPDATED_COMMENTAIRE_VICTIME_TRANSPORTER)
            .dateEtHeureMomentAccident(UPDATED_DATE_ET_HEURE_MOMENT_ACCIDENT)
            .lieuAccident(UPDATED_LIEU_ACCIDENT)
            .isIdentifierDu(UPDATED_IS_IDENTIFIER_DU)
            .circonstanceAccident(UPDATED_CIRCONSTANCE_ACCIDENT)
            .materielEnCause(UPDATED_MATERIEL_EN_CAUSE)
            .remarques(UPDATED_REMARQUES)
            .pilote(UPDATED_PILOTE)
            .dateEtHeureValidationPilote(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE)
            .porteur(UPDATED_PORTEUR)
            .dateEtHeureValidationPorteur(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR)
            .hse(UPDATED_HSE)
            .dateEtHeureValidationHse(UPDATED_DATE_ET_HEURE_VALIDATION_HSE)
            .isIntervention8300(UPDATED_IS_INTERVENTION_8300)
            .isInterventionInfirmiere(UPDATED_IS_INTERVENTION_INFIRMIERE)
            .commentaireInfirmere(UPDATED_COMMENTAIRE_INFIRMERE)
            .isInterventionMedecin(UPDATED_IS_INTERVENTION_MEDECIN)
            .commentaireMedecin(UPDATED_COMMENTAIRE_MEDECIN)
            .isInterventionsecouriste(UPDATED_IS_INTERVENTIONSECOURISTE)
            .commentaireSecouriste(UPDATED_COMMENTAIRE_SECOURISTE)
            .isInterventionsecouristeExterieur(UPDATED_IS_INTERVENTIONSECOURISTE_EXTERIEUR)
            .retourAuPosteDeTravail(UPDATED_RETOUR_AU_POSTE_DE_TRAVAIL)
            .travailLegerPossible(UPDATED_TRAVAIL_LEGER_POSSIBLE)
            .analyseAChaudInfirmiere(UPDATED_ANALYSE_A_CHAUD_INFIRMIERE)
            .analyseAChaudCodir(UPDATED_ANALYSE_A_CHAUD_CODIR)
            .analyseAChaudHse(UPDATED_ANALYSE_A_CHAUD_HSE)
            .analyseAChaudNplus1(UPDATED_ANALYSE_A_CHAUD_NPLUS_1)
            .analyseAChaudCssCt(UPDATED_ANALYSE_A_CHAUD_CSS_CT)
            .analyseAChaudCommentaire(UPDATED_ANALYSE_A_CHAUD_COMMENTAIRE)
            .pourquoi1(UPDATED_POURQUOI_1)
            .pourquoi2(UPDATED_POURQUOI_2)
            .pourquoi3(UPDATED_POURQUOI_3)
            .pourquoi4(UPDATED_POURQUOI_4)
            .pourquoi5(UPDATED_POURQUOI_5)
            .bras(UPDATED_BRAS)
            .chevilles(UPDATED_CHEVILLES)
            .colonne(UPDATED_COLONNE)
            .cou(UPDATED_COU)
            .coude(UPDATED_COUDE)
            .cos(UPDATED_COS)
            .epaule(UPDATED_EPAULE)
            .genou(UPDATED_GENOU)
            .jambes(UPDATED_JAMBES)
            .mains(UPDATED_MAINS)
            .oeil(UPDATED_OEIL)
            .pieds(UPDATED_PIEDS)
            .poignet(UPDATED_POIGNET)
            .tete(UPDATED_TETE)
            .torse(UPDATED_TORSE);
        return rapport;
    }

    @BeforeEach
    public void initTest() {
        rapport = createEntity(em);
    }

    @Test
    @Transactional
    void createRapport() throws Exception {
        int databaseSizeBeforeCreate = rapportRepository.findAll().size();
        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);
        restRapportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rapportDTO)))
            .andExpect(status().isCreated());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeCreate + 1);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        assertThat(testRapport.getRedacteur()).isEqualTo(DEFAULT_REDACTEUR);
        assertThat(testRapport.getDateDeCreation()).isEqualTo(DEFAULT_DATE_DE_CREATION);
        assertThat(testRapport.getUap()).isEqualTo(DEFAULT_UAP);
        assertThat(testRapport.getDateEtHeureConnaissanceAt()).isEqualTo(DEFAULT_DATE_ET_HEURE_CONNAISSANCE_AT);
        assertThat(testRapport.getPrevenuComment()).isEqualTo(DEFAULT_PREVENU_COMMENT);
        assertThat(testRapport.getNomPremierePersonnePrevenu()).isEqualTo(DEFAULT_NOM_PREMIERE_PERSONNE_PREVENU);
        assertThat(testRapport.getDateEtHeurePrevenu()).isEqualTo(DEFAULT_DATE_ET_HEURE_PREVENU);
        assertThat(testRapport.getIsTemoin()).isEqualTo(DEFAULT_IS_TEMOIN);
        assertThat(testRapport.getCommentaireTemoin()).isEqualTo(DEFAULT_COMMENTAIRE_TEMOIN);
        assertThat(testRapport.getIsTiersEnCause()).isEqualTo(DEFAULT_IS_TIERS_EN_CAUSE);
        assertThat(testRapport.getCommentaireTiersEnCause()).isEqualTo(DEFAULT_COMMENTAIRE_TIERS_EN_CAUSE);
        assertThat(testRapport.getIsAutreVictime()).isEqualTo(DEFAULT_IS_AUTRE_VICTIME);
        assertThat(testRapport.getCommentaireAutreVictime()).isEqualTo(DEFAULT_COMMENTAIRE_AUTRE_VICTIME);
        assertThat(testRapport.getIsRapportDePolice()).isEqualTo(DEFAULT_IS_RAPPORT_DE_POLICE);
        assertThat(testRapport.getCommentaireRapportDePolice()).isEqualTo(DEFAULT_COMMENTAIRE_RAPPORT_DE_POLICE);
        assertThat(testRapport.getIsVictimeTransports()).isEqualTo(DEFAULT_IS_VICTIME_TRANSPORTS);
        assertThat(testRapport.getCommentaireVictimeTransporter()).isEqualTo(DEFAULT_COMMENTAIRE_VICTIME_TRANSPORTER);
        assertThat(testRapport.getDateEtHeureMomentAccident()).isEqualTo(DEFAULT_DATE_ET_HEURE_MOMENT_ACCIDENT);
        assertThat(testRapport.getLieuAccident()).isEqualTo(DEFAULT_LIEU_ACCIDENT);
        assertThat(testRapport.getIsIdentifierDu()).isEqualTo(DEFAULT_IS_IDENTIFIER_DU);
        assertThat(testRapport.getCirconstanceAccident()).isEqualTo(DEFAULT_CIRCONSTANCE_ACCIDENT);
        assertThat(testRapport.getMaterielEnCause()).isEqualTo(DEFAULT_MATERIEL_EN_CAUSE);
        assertThat(testRapport.getRemarques()).isEqualTo(DEFAULT_REMARQUES);
        assertThat(testRapport.getPilote()).isEqualTo(DEFAULT_PILOTE);
        assertThat(testRapport.getDateEtHeureValidationPilote()).isEqualTo(DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE);
        assertThat(testRapport.getPorteur()).isEqualTo(DEFAULT_PORTEUR);
        assertThat(testRapport.getDateEtHeureValidationPorteur()).isEqualTo(DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR);
        assertThat(testRapport.getHse()).isEqualTo(DEFAULT_HSE);
        assertThat(testRapport.getDateEtHeureValidationHse()).isEqualTo(DEFAULT_DATE_ET_HEURE_VALIDATION_HSE);
        assertThat(testRapport.getIsIntervention8300()).isEqualTo(DEFAULT_IS_INTERVENTION_8300);
        assertThat(testRapport.getIsInterventionInfirmiere()).isEqualTo(DEFAULT_IS_INTERVENTION_INFIRMIERE);
        assertThat(testRapport.getCommentaireInfirmere()).isEqualTo(DEFAULT_COMMENTAIRE_INFIRMERE);
        assertThat(testRapport.getIsInterventionMedecin()).isEqualTo(DEFAULT_IS_INTERVENTION_MEDECIN);
        assertThat(testRapport.getCommentaireMedecin()).isEqualTo(DEFAULT_COMMENTAIRE_MEDECIN);
        assertThat(testRapport.getIsInterventionsecouriste()).isEqualTo(DEFAULT_IS_INTERVENTIONSECOURISTE);
        assertThat(testRapport.getCommentaireSecouriste()).isEqualTo(DEFAULT_COMMENTAIRE_SECOURISTE);
        assertThat(testRapport.getIsInterventionsecouristeExterieur()).isEqualTo(DEFAULT_IS_INTERVENTIONSECOURISTE_EXTERIEUR);
        assertThat(testRapport.getRetourAuPosteDeTravail()).isEqualTo(DEFAULT_RETOUR_AU_POSTE_DE_TRAVAIL);
        assertThat(testRapport.getTravailLegerPossible()).isEqualTo(DEFAULT_TRAVAIL_LEGER_POSSIBLE);
        assertThat(testRapport.getAnalyseAChaudInfirmiere()).isEqualTo(DEFAULT_ANALYSE_A_CHAUD_INFIRMIERE);
        assertThat(testRapport.getAnalyseAChaudCodir()).isEqualTo(DEFAULT_ANALYSE_A_CHAUD_CODIR);
        assertThat(testRapport.getAnalyseAChaudHse()).isEqualTo(DEFAULT_ANALYSE_A_CHAUD_HSE);
        assertThat(testRapport.getAnalyseAChaudNplus1()).isEqualTo(DEFAULT_ANALYSE_A_CHAUD_NPLUS_1);
        assertThat(testRapport.getAnalyseAChaudCssCt()).isEqualTo(DEFAULT_ANALYSE_A_CHAUD_CSS_CT);
        assertThat(testRapport.getAnalyseAChaudCommentaire()).isEqualTo(DEFAULT_ANALYSE_A_CHAUD_COMMENTAIRE);
        assertThat(testRapport.getPourquoi1()).isEqualTo(DEFAULT_POURQUOI_1);
        assertThat(testRapport.getPourquoi2()).isEqualTo(DEFAULT_POURQUOI_2);
        assertThat(testRapport.getPourquoi3()).isEqualTo(DEFAULT_POURQUOI_3);
        assertThat(testRapport.getPourquoi4()).isEqualTo(DEFAULT_POURQUOI_4);
        assertThat(testRapport.getPourquoi5()).isEqualTo(DEFAULT_POURQUOI_5);
        assertThat(testRapport.getBras()).isEqualTo(DEFAULT_BRAS);
        assertThat(testRapport.getChevilles()).isEqualTo(DEFAULT_CHEVILLES);
        assertThat(testRapport.getColonne()).isEqualTo(DEFAULT_COLONNE);
        assertThat(testRapport.getCou()).isEqualTo(DEFAULT_COU);
        assertThat(testRapport.getCoude()).isEqualTo(DEFAULT_COUDE);
        assertThat(testRapport.getCos()).isEqualTo(DEFAULT_COS);
        assertThat(testRapport.getEpaule()).isEqualTo(DEFAULT_EPAULE);
        assertThat(testRapport.getGenou()).isEqualTo(DEFAULT_GENOU);
        assertThat(testRapport.getJambes()).isEqualTo(DEFAULT_JAMBES);
        assertThat(testRapport.getMains()).isEqualTo(DEFAULT_MAINS);
        assertThat(testRapport.getOeil()).isEqualTo(DEFAULT_OEIL);
        assertThat(testRapport.getPieds()).isEqualTo(DEFAULT_PIEDS);
        assertThat(testRapport.getPoignet()).isEqualTo(DEFAULT_POIGNET);
        assertThat(testRapport.getTete()).isEqualTo(DEFAULT_TETE);
        assertThat(testRapport.getTorse()).isEqualTo(DEFAULT_TORSE);
    }

    @Test
    @Transactional
    void createRapportWithExistingId() throws Exception {
        // Create the Rapport with an existing ID
        rapport.setId(1L);
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        int databaseSizeBeforeCreate = rapportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRapportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rapportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRapports() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        // Get all the rapportList
        restRapportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rapport.getId().intValue())))
            .andExpect(jsonPath("$.[*].redacteur").value(hasItem(DEFAULT_REDACTEUR)))
            .andExpect(jsonPath("$.[*].dateDeCreation").value(hasItem(DEFAULT_DATE_DE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].uap").value(hasItem(DEFAULT_UAP)))
            .andExpect(jsonPath("$.[*].dateEtHeureConnaissanceAt").value(hasItem(DEFAULT_DATE_ET_HEURE_CONNAISSANCE_AT.toString())))
            .andExpect(jsonPath("$.[*].prevenuComment").value(hasItem(DEFAULT_PREVENU_COMMENT)))
            .andExpect(jsonPath("$.[*].nomPremierePersonnePrevenu").value(hasItem(DEFAULT_NOM_PREMIERE_PERSONNE_PREVENU)))
            .andExpect(jsonPath("$.[*].dateEtHeurePrevenu").value(hasItem(DEFAULT_DATE_ET_HEURE_PREVENU.toString())))
            .andExpect(jsonPath("$.[*].isTemoin").value(hasItem(DEFAULT_IS_TEMOIN.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaireTemoin").value(hasItem(DEFAULT_COMMENTAIRE_TEMOIN)))
            .andExpect(jsonPath("$.[*].isTiersEnCause").value(hasItem(DEFAULT_IS_TIERS_EN_CAUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaireTiersEnCause").value(hasItem(DEFAULT_COMMENTAIRE_TIERS_EN_CAUSE)))
            .andExpect(jsonPath("$.[*].isAutreVictime").value(hasItem(DEFAULT_IS_AUTRE_VICTIME.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaireAutreVictime").value(hasItem(DEFAULT_COMMENTAIRE_AUTRE_VICTIME)))
            .andExpect(jsonPath("$.[*].isRapportDePolice").value(hasItem(DEFAULT_IS_RAPPORT_DE_POLICE.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaireRapportDePolice").value(hasItem(DEFAULT_COMMENTAIRE_RAPPORT_DE_POLICE)))
            .andExpect(jsonPath("$.[*].isVictimeTransports").value(hasItem(DEFAULT_IS_VICTIME_TRANSPORTS.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaireVictimeTransporter").value(hasItem(DEFAULT_COMMENTAIRE_VICTIME_TRANSPORTER)))
            .andExpect(jsonPath("$.[*].dateEtHeureMomentAccident").value(hasItem(DEFAULT_DATE_ET_HEURE_MOMENT_ACCIDENT.toString())))
            .andExpect(jsonPath("$.[*].lieuAccident").value(hasItem(DEFAULT_LIEU_ACCIDENT)))
            .andExpect(jsonPath("$.[*].isIdentifierDu").value(hasItem(DEFAULT_IS_IDENTIFIER_DU.booleanValue())))
            .andExpect(jsonPath("$.[*].circonstanceAccident").value(hasItem(DEFAULT_CIRCONSTANCE_ACCIDENT)))
            .andExpect(jsonPath("$.[*].materielEnCause").value(hasItem(DEFAULT_MATERIEL_EN_CAUSE)))
            .andExpect(jsonPath("$.[*].remarques").value(hasItem(DEFAULT_REMARQUES)))
            .andExpect(jsonPath("$.[*].pilote").value(hasItem(DEFAULT_PILOTE)))
            .andExpect(jsonPath("$.[*].dateEtHeureValidationPilote").value(hasItem(DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE.toString())))
            .andExpect(jsonPath("$.[*].porteur").value(hasItem(DEFAULT_PORTEUR)))
            .andExpect(jsonPath("$.[*].dateEtHeureValidationPorteur").value(hasItem(DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR.toString())))
            .andExpect(jsonPath("$.[*].hse").value(hasItem(DEFAULT_HSE)))
            .andExpect(jsonPath("$.[*].dateEtHeureValidationHse").value(hasItem(DEFAULT_DATE_ET_HEURE_VALIDATION_HSE.toString())))
            .andExpect(jsonPath("$.[*].isIntervention8300").value(hasItem(DEFAULT_IS_INTERVENTION_8300.booleanValue())))
            .andExpect(jsonPath("$.[*].isInterventionInfirmiere").value(hasItem(DEFAULT_IS_INTERVENTION_INFIRMIERE.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaireInfirmere").value(hasItem(DEFAULT_COMMENTAIRE_INFIRMERE)))
            .andExpect(jsonPath("$.[*].isInterventionMedecin").value(hasItem(DEFAULT_IS_INTERVENTION_MEDECIN.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaireMedecin").value(hasItem(DEFAULT_COMMENTAIRE_MEDECIN)))
            .andExpect(jsonPath("$.[*].isInterventionsecouriste").value(hasItem(DEFAULT_IS_INTERVENTIONSECOURISTE.booleanValue())))
            .andExpect(jsonPath("$.[*].commentaireSecouriste").value(hasItem(DEFAULT_COMMENTAIRE_SECOURISTE)))
            .andExpect(
                jsonPath("$.[*].isInterventionsecouristeExterieur")
                    .value(hasItem(DEFAULT_IS_INTERVENTIONSECOURISTE_EXTERIEUR.booleanValue()))
            )
            .andExpect(jsonPath("$.[*].retourAuPosteDeTravail").value(hasItem(DEFAULT_RETOUR_AU_POSTE_DE_TRAVAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].travailLegerPossible").value(hasItem(DEFAULT_TRAVAIL_LEGER_POSSIBLE)))
            .andExpect(jsonPath("$.[*].analyseAChaudInfirmiere").value(hasItem(DEFAULT_ANALYSE_A_CHAUD_INFIRMIERE.booleanValue())))
            .andExpect(jsonPath("$.[*].analyseAChaudCodir").value(hasItem(DEFAULT_ANALYSE_A_CHAUD_CODIR.booleanValue())))
            .andExpect(jsonPath("$.[*].analyseAChaudHse").value(hasItem(DEFAULT_ANALYSE_A_CHAUD_HSE.booleanValue())))
            .andExpect(jsonPath("$.[*].analyseAChaudNplus1").value(hasItem(DEFAULT_ANALYSE_A_CHAUD_NPLUS_1.booleanValue())))
            .andExpect(jsonPath("$.[*].analyseAChaudCssCt").value(hasItem(DEFAULT_ANALYSE_A_CHAUD_CSS_CT.booleanValue())))
            .andExpect(jsonPath("$.[*].analyseAChaudCommentaire").value(hasItem(DEFAULT_ANALYSE_A_CHAUD_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].pourquoi1").value(hasItem(DEFAULT_POURQUOI_1)))
            .andExpect(jsonPath("$.[*].pourquoi2").value(hasItem(DEFAULT_POURQUOI_2)))
            .andExpect(jsonPath("$.[*].pourquoi3").value(hasItem(DEFAULT_POURQUOI_3)))
            .andExpect(jsonPath("$.[*].pourquoi4").value(hasItem(DEFAULT_POURQUOI_4)))
            .andExpect(jsonPath("$.[*].pourquoi5").value(hasItem(DEFAULT_POURQUOI_5)))
            .andExpect(jsonPath("$.[*].bras").value(hasItem(DEFAULT_BRAS.booleanValue())))
            .andExpect(jsonPath("$.[*].chevilles").value(hasItem(DEFAULT_CHEVILLES.booleanValue())))
            .andExpect(jsonPath("$.[*].colonne").value(hasItem(DEFAULT_COLONNE.booleanValue())))
            .andExpect(jsonPath("$.[*].cou").value(hasItem(DEFAULT_COU.booleanValue())))
            .andExpect(jsonPath("$.[*].coude").value(hasItem(DEFAULT_COUDE.booleanValue())))
            .andExpect(jsonPath("$.[*].cos").value(hasItem(DEFAULT_COS.booleanValue())))
            .andExpect(jsonPath("$.[*].epaule").value(hasItem(DEFAULT_EPAULE.booleanValue())))
            .andExpect(jsonPath("$.[*].genou").value(hasItem(DEFAULT_GENOU.booleanValue())))
            .andExpect(jsonPath("$.[*].jambes").value(hasItem(DEFAULT_JAMBES.booleanValue())))
            .andExpect(jsonPath("$.[*].mains").value(hasItem(DEFAULT_MAINS.booleanValue())))
            .andExpect(jsonPath("$.[*].oeil").value(hasItem(DEFAULT_OEIL.booleanValue())))
            .andExpect(jsonPath("$.[*].pieds").value(hasItem(DEFAULT_PIEDS.booleanValue())))
            .andExpect(jsonPath("$.[*].poignet").value(hasItem(DEFAULT_POIGNET.booleanValue())))
            .andExpect(jsonPath("$.[*].tete").value(hasItem(DEFAULT_TETE.booleanValue())))
            .andExpect(jsonPath("$.[*].torse").value(hasItem(DEFAULT_TORSE.booleanValue())));
    }

    @Test
    @Transactional
    void getRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        // Get the rapport
        restRapportMockMvc
            .perform(get(ENTITY_API_URL_ID, rapport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rapport.getId().intValue()))
            .andExpect(jsonPath("$.redacteur").value(DEFAULT_REDACTEUR))
            .andExpect(jsonPath("$.dateDeCreation").value(DEFAULT_DATE_DE_CREATION.toString()))
            .andExpect(jsonPath("$.uap").value(DEFAULT_UAP))
            .andExpect(jsonPath("$.dateEtHeureConnaissanceAt").value(DEFAULT_DATE_ET_HEURE_CONNAISSANCE_AT.toString()))
            .andExpect(jsonPath("$.prevenuComment").value(DEFAULT_PREVENU_COMMENT))
            .andExpect(jsonPath("$.nomPremierePersonnePrevenu").value(DEFAULT_NOM_PREMIERE_PERSONNE_PREVENU))
            .andExpect(jsonPath("$.dateEtHeurePrevenu").value(DEFAULT_DATE_ET_HEURE_PREVENU.toString()))
            .andExpect(jsonPath("$.isTemoin").value(DEFAULT_IS_TEMOIN.booleanValue()))
            .andExpect(jsonPath("$.commentaireTemoin").value(DEFAULT_COMMENTAIRE_TEMOIN))
            .andExpect(jsonPath("$.isTiersEnCause").value(DEFAULT_IS_TIERS_EN_CAUSE.booleanValue()))
            .andExpect(jsonPath("$.commentaireTiersEnCause").value(DEFAULT_COMMENTAIRE_TIERS_EN_CAUSE))
            .andExpect(jsonPath("$.isAutreVictime").value(DEFAULT_IS_AUTRE_VICTIME.booleanValue()))
            .andExpect(jsonPath("$.commentaireAutreVictime").value(DEFAULT_COMMENTAIRE_AUTRE_VICTIME))
            .andExpect(jsonPath("$.isRapportDePolice").value(DEFAULT_IS_RAPPORT_DE_POLICE.booleanValue()))
            .andExpect(jsonPath("$.commentaireRapportDePolice").value(DEFAULT_COMMENTAIRE_RAPPORT_DE_POLICE))
            .andExpect(jsonPath("$.isVictimeTransports").value(DEFAULT_IS_VICTIME_TRANSPORTS.booleanValue()))
            .andExpect(jsonPath("$.commentaireVictimeTransporter").value(DEFAULT_COMMENTAIRE_VICTIME_TRANSPORTER))
            .andExpect(jsonPath("$.dateEtHeureMomentAccident").value(DEFAULT_DATE_ET_HEURE_MOMENT_ACCIDENT.toString()))
            .andExpect(jsonPath("$.lieuAccident").value(DEFAULT_LIEU_ACCIDENT))
            .andExpect(jsonPath("$.isIdentifierDu").value(DEFAULT_IS_IDENTIFIER_DU.booleanValue()))
            .andExpect(jsonPath("$.circonstanceAccident").value(DEFAULT_CIRCONSTANCE_ACCIDENT))
            .andExpect(jsonPath("$.materielEnCause").value(DEFAULT_MATERIEL_EN_CAUSE))
            .andExpect(jsonPath("$.remarques").value(DEFAULT_REMARQUES))
            .andExpect(jsonPath("$.pilote").value(DEFAULT_PILOTE))
            .andExpect(jsonPath("$.dateEtHeureValidationPilote").value(DEFAULT_DATE_ET_HEURE_VALIDATION_PILOTE.toString()))
            .andExpect(jsonPath("$.porteur").value(DEFAULT_PORTEUR))
            .andExpect(jsonPath("$.dateEtHeureValidationPorteur").value(DEFAULT_DATE_ET_HEURE_VALIDATION_PORTEUR.toString()))
            .andExpect(jsonPath("$.hse").value(DEFAULT_HSE))
            .andExpect(jsonPath("$.dateEtHeureValidationHse").value(DEFAULT_DATE_ET_HEURE_VALIDATION_HSE.toString()))
            .andExpect(jsonPath("$.isIntervention8300").value(DEFAULT_IS_INTERVENTION_8300.booleanValue()))
            .andExpect(jsonPath("$.isInterventionInfirmiere").value(DEFAULT_IS_INTERVENTION_INFIRMIERE.booleanValue()))
            .andExpect(jsonPath("$.commentaireInfirmere").value(DEFAULT_COMMENTAIRE_INFIRMERE))
            .andExpect(jsonPath("$.isInterventionMedecin").value(DEFAULT_IS_INTERVENTION_MEDECIN.booleanValue()))
            .andExpect(jsonPath("$.commentaireMedecin").value(DEFAULT_COMMENTAIRE_MEDECIN))
            .andExpect(jsonPath("$.isInterventionsecouriste").value(DEFAULT_IS_INTERVENTIONSECOURISTE.booleanValue()))
            .andExpect(jsonPath("$.commentaireSecouriste").value(DEFAULT_COMMENTAIRE_SECOURISTE))
            .andExpect(jsonPath("$.isInterventionsecouristeExterieur").value(DEFAULT_IS_INTERVENTIONSECOURISTE_EXTERIEUR.booleanValue()))
            .andExpect(jsonPath("$.retourAuPosteDeTravail").value(DEFAULT_RETOUR_AU_POSTE_DE_TRAVAIL.booleanValue()))
            .andExpect(jsonPath("$.travailLegerPossible").value(DEFAULT_TRAVAIL_LEGER_POSSIBLE))
            .andExpect(jsonPath("$.analyseAChaudInfirmiere").value(DEFAULT_ANALYSE_A_CHAUD_INFIRMIERE.booleanValue()))
            .andExpect(jsonPath("$.analyseAChaudCodir").value(DEFAULT_ANALYSE_A_CHAUD_CODIR.booleanValue()))
            .andExpect(jsonPath("$.analyseAChaudHse").value(DEFAULT_ANALYSE_A_CHAUD_HSE.booleanValue()))
            .andExpect(jsonPath("$.analyseAChaudNplus1").value(DEFAULT_ANALYSE_A_CHAUD_NPLUS_1.booleanValue()))
            .andExpect(jsonPath("$.analyseAChaudCssCt").value(DEFAULT_ANALYSE_A_CHAUD_CSS_CT.booleanValue()))
            .andExpect(jsonPath("$.analyseAChaudCommentaire").value(DEFAULT_ANALYSE_A_CHAUD_COMMENTAIRE))
            .andExpect(jsonPath("$.pourquoi1").value(DEFAULT_POURQUOI_1))
            .andExpect(jsonPath("$.pourquoi2").value(DEFAULT_POURQUOI_2))
            .andExpect(jsonPath("$.pourquoi3").value(DEFAULT_POURQUOI_3))
            .andExpect(jsonPath("$.pourquoi4").value(DEFAULT_POURQUOI_4))
            .andExpect(jsonPath("$.pourquoi5").value(DEFAULT_POURQUOI_5))
            .andExpect(jsonPath("$.bras").value(DEFAULT_BRAS.booleanValue()))
            .andExpect(jsonPath("$.chevilles").value(DEFAULT_CHEVILLES.booleanValue()))
            .andExpect(jsonPath("$.colonne").value(DEFAULT_COLONNE.booleanValue()))
            .andExpect(jsonPath("$.cou").value(DEFAULT_COU.booleanValue()))
            .andExpect(jsonPath("$.coude").value(DEFAULT_COUDE.booleanValue()))
            .andExpect(jsonPath("$.cos").value(DEFAULT_COS.booleanValue()))
            .andExpect(jsonPath("$.epaule").value(DEFAULT_EPAULE.booleanValue()))
            .andExpect(jsonPath("$.genou").value(DEFAULT_GENOU.booleanValue()))
            .andExpect(jsonPath("$.jambes").value(DEFAULT_JAMBES.booleanValue()))
            .andExpect(jsonPath("$.mains").value(DEFAULT_MAINS.booleanValue()))
            .andExpect(jsonPath("$.oeil").value(DEFAULT_OEIL.booleanValue()))
            .andExpect(jsonPath("$.pieds").value(DEFAULT_PIEDS.booleanValue()))
            .andExpect(jsonPath("$.poignet").value(DEFAULT_POIGNET.booleanValue()))
            .andExpect(jsonPath("$.tete").value(DEFAULT_TETE.booleanValue()))
            .andExpect(jsonPath("$.torse").value(DEFAULT_TORSE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRapport() throws Exception {
        // Get the rapport
        restRapportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();

        // Update the rapport
        Rapport updatedRapport = rapportRepository.findById(rapport.getId()).get();
        // Disconnect from session so that the updates on updatedRapport are not directly saved in db
        em.detach(updatedRapport);
        updatedRapport
            .redacteur(UPDATED_REDACTEUR)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .uap(UPDATED_UAP)
            .dateEtHeureConnaissanceAt(UPDATED_DATE_ET_HEURE_CONNAISSANCE_AT)
            .prevenuComment(UPDATED_PREVENU_COMMENT)
            .nomPremierePersonnePrevenu(UPDATED_NOM_PREMIERE_PERSONNE_PREVENU)
            .dateEtHeurePrevenu(UPDATED_DATE_ET_HEURE_PREVENU)
            .isTemoin(UPDATED_IS_TEMOIN)
            .commentaireTemoin(UPDATED_COMMENTAIRE_TEMOIN)
            .isTiersEnCause(UPDATED_IS_TIERS_EN_CAUSE)
            .commentaireTiersEnCause(UPDATED_COMMENTAIRE_TIERS_EN_CAUSE)
            .isAutreVictime(UPDATED_IS_AUTRE_VICTIME)
            .commentaireAutreVictime(UPDATED_COMMENTAIRE_AUTRE_VICTIME)
            .isRapportDePolice(UPDATED_IS_RAPPORT_DE_POLICE)
            .commentaireRapportDePolice(UPDATED_COMMENTAIRE_RAPPORT_DE_POLICE)
            .isVictimeTransports(UPDATED_IS_VICTIME_TRANSPORTS)
            .commentaireVictimeTransporter(UPDATED_COMMENTAIRE_VICTIME_TRANSPORTER)
            .dateEtHeureMomentAccident(UPDATED_DATE_ET_HEURE_MOMENT_ACCIDENT)
            .lieuAccident(UPDATED_LIEU_ACCIDENT)
            .isIdentifierDu(UPDATED_IS_IDENTIFIER_DU)
            .circonstanceAccident(UPDATED_CIRCONSTANCE_ACCIDENT)
            .materielEnCause(UPDATED_MATERIEL_EN_CAUSE)
            .remarques(UPDATED_REMARQUES)
            .pilote(UPDATED_PILOTE)
            .dateEtHeureValidationPilote(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE)
            .porteur(UPDATED_PORTEUR)
            .dateEtHeureValidationPorteur(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR)
            .hse(UPDATED_HSE)
            .dateEtHeureValidationHse(UPDATED_DATE_ET_HEURE_VALIDATION_HSE)
            .isIntervention8300(UPDATED_IS_INTERVENTION_8300)
            .isInterventionInfirmiere(UPDATED_IS_INTERVENTION_INFIRMIERE)
            .commentaireInfirmere(UPDATED_COMMENTAIRE_INFIRMERE)
            .isInterventionMedecin(UPDATED_IS_INTERVENTION_MEDECIN)
            .commentaireMedecin(UPDATED_COMMENTAIRE_MEDECIN)
            .isInterventionsecouriste(UPDATED_IS_INTERVENTIONSECOURISTE)
            .commentaireSecouriste(UPDATED_COMMENTAIRE_SECOURISTE)
            .isInterventionsecouristeExterieur(UPDATED_IS_INTERVENTIONSECOURISTE_EXTERIEUR)
            .retourAuPosteDeTravail(UPDATED_RETOUR_AU_POSTE_DE_TRAVAIL)
            .travailLegerPossible(UPDATED_TRAVAIL_LEGER_POSSIBLE)
            .analyseAChaudInfirmiere(UPDATED_ANALYSE_A_CHAUD_INFIRMIERE)
            .analyseAChaudCodir(UPDATED_ANALYSE_A_CHAUD_CODIR)
            .analyseAChaudHse(UPDATED_ANALYSE_A_CHAUD_HSE)
            .analyseAChaudNplus1(UPDATED_ANALYSE_A_CHAUD_NPLUS_1)
            .analyseAChaudCssCt(UPDATED_ANALYSE_A_CHAUD_CSS_CT)
            .analyseAChaudCommentaire(UPDATED_ANALYSE_A_CHAUD_COMMENTAIRE)
            .pourquoi1(UPDATED_POURQUOI_1)
            .pourquoi2(UPDATED_POURQUOI_2)
            .pourquoi3(UPDATED_POURQUOI_3)
            .pourquoi4(UPDATED_POURQUOI_4)
            .pourquoi5(UPDATED_POURQUOI_5)
            .bras(UPDATED_BRAS)
            .chevilles(UPDATED_CHEVILLES)
            .colonne(UPDATED_COLONNE)
            .cou(UPDATED_COU)
            .coude(UPDATED_COUDE)
            .cos(UPDATED_COS)
            .epaule(UPDATED_EPAULE)
            .genou(UPDATED_GENOU)
            .jambes(UPDATED_JAMBES)
            .mains(UPDATED_MAINS)
            .oeil(UPDATED_OEIL)
            .pieds(UPDATED_PIEDS)
            .poignet(UPDATED_POIGNET)
            .tete(UPDATED_TETE)
            .torse(UPDATED_TORSE);
        RapportDTO rapportDTO = rapportMapper.toDto(updatedRapport);

        restRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rapportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rapportDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        assertThat(testRapport.getRedacteur()).isEqualTo(UPDATED_REDACTEUR);
        assertThat(testRapport.getDateDeCreation()).isEqualTo(UPDATED_DATE_DE_CREATION);
        assertThat(testRapport.getUap()).isEqualTo(UPDATED_UAP);
        assertThat(testRapport.getDateEtHeureConnaissanceAt()).isEqualTo(UPDATED_DATE_ET_HEURE_CONNAISSANCE_AT);
        assertThat(testRapport.getPrevenuComment()).isEqualTo(UPDATED_PREVENU_COMMENT);
        assertThat(testRapport.getNomPremierePersonnePrevenu()).isEqualTo(UPDATED_NOM_PREMIERE_PERSONNE_PREVENU);
        assertThat(testRapport.getDateEtHeurePrevenu()).isEqualTo(UPDATED_DATE_ET_HEURE_PREVENU);
        assertThat(testRapport.getIsTemoin()).isEqualTo(UPDATED_IS_TEMOIN);
        assertThat(testRapport.getCommentaireTemoin()).isEqualTo(UPDATED_COMMENTAIRE_TEMOIN);
        assertThat(testRapport.getIsTiersEnCause()).isEqualTo(UPDATED_IS_TIERS_EN_CAUSE);
        assertThat(testRapport.getCommentaireTiersEnCause()).isEqualTo(UPDATED_COMMENTAIRE_TIERS_EN_CAUSE);
        assertThat(testRapport.getIsAutreVictime()).isEqualTo(UPDATED_IS_AUTRE_VICTIME);
        assertThat(testRapport.getCommentaireAutreVictime()).isEqualTo(UPDATED_COMMENTAIRE_AUTRE_VICTIME);
        assertThat(testRapport.getIsRapportDePolice()).isEqualTo(UPDATED_IS_RAPPORT_DE_POLICE);
        assertThat(testRapport.getCommentaireRapportDePolice()).isEqualTo(UPDATED_COMMENTAIRE_RAPPORT_DE_POLICE);
        assertThat(testRapport.getIsVictimeTransports()).isEqualTo(UPDATED_IS_VICTIME_TRANSPORTS);
        assertThat(testRapport.getCommentaireVictimeTransporter()).isEqualTo(UPDATED_COMMENTAIRE_VICTIME_TRANSPORTER);
        assertThat(testRapport.getDateEtHeureMomentAccident()).isEqualTo(UPDATED_DATE_ET_HEURE_MOMENT_ACCIDENT);
        assertThat(testRapport.getLieuAccident()).isEqualTo(UPDATED_LIEU_ACCIDENT);
        assertThat(testRapport.getIsIdentifierDu()).isEqualTo(UPDATED_IS_IDENTIFIER_DU);
        assertThat(testRapport.getCirconstanceAccident()).isEqualTo(UPDATED_CIRCONSTANCE_ACCIDENT);
        assertThat(testRapport.getMaterielEnCause()).isEqualTo(UPDATED_MATERIEL_EN_CAUSE);
        assertThat(testRapport.getRemarques()).isEqualTo(UPDATED_REMARQUES);
        assertThat(testRapport.getPilote()).isEqualTo(UPDATED_PILOTE);
        assertThat(testRapport.getDateEtHeureValidationPilote()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE);
        assertThat(testRapport.getPorteur()).isEqualTo(UPDATED_PORTEUR);
        assertThat(testRapport.getDateEtHeureValidationPorteur()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR);
        assertThat(testRapport.getHse()).isEqualTo(UPDATED_HSE);
        assertThat(testRapport.getDateEtHeureValidationHse()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_HSE);
        assertThat(testRapport.getIsIntervention8300()).isEqualTo(UPDATED_IS_INTERVENTION_8300);
        assertThat(testRapport.getIsInterventionInfirmiere()).isEqualTo(UPDATED_IS_INTERVENTION_INFIRMIERE);
        assertThat(testRapport.getCommentaireInfirmere()).isEqualTo(UPDATED_COMMENTAIRE_INFIRMERE);
        assertThat(testRapport.getIsInterventionMedecin()).isEqualTo(UPDATED_IS_INTERVENTION_MEDECIN);
        assertThat(testRapport.getCommentaireMedecin()).isEqualTo(UPDATED_COMMENTAIRE_MEDECIN);
        assertThat(testRapport.getIsInterventionsecouriste()).isEqualTo(UPDATED_IS_INTERVENTIONSECOURISTE);
        assertThat(testRapport.getCommentaireSecouriste()).isEqualTo(UPDATED_COMMENTAIRE_SECOURISTE);
        assertThat(testRapport.getIsInterventionsecouristeExterieur()).isEqualTo(UPDATED_IS_INTERVENTIONSECOURISTE_EXTERIEUR);
        assertThat(testRapport.getRetourAuPosteDeTravail()).isEqualTo(UPDATED_RETOUR_AU_POSTE_DE_TRAVAIL);
        assertThat(testRapport.getTravailLegerPossible()).isEqualTo(UPDATED_TRAVAIL_LEGER_POSSIBLE);
        assertThat(testRapport.getAnalyseAChaudInfirmiere()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_INFIRMIERE);
        assertThat(testRapport.getAnalyseAChaudCodir()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_CODIR);
        assertThat(testRapport.getAnalyseAChaudHse()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_HSE);
        assertThat(testRapport.getAnalyseAChaudNplus1()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_NPLUS_1);
        assertThat(testRapport.getAnalyseAChaudCssCt()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_CSS_CT);
        assertThat(testRapport.getAnalyseAChaudCommentaire()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_COMMENTAIRE);
        assertThat(testRapport.getPourquoi1()).isEqualTo(UPDATED_POURQUOI_1);
        assertThat(testRapport.getPourquoi2()).isEqualTo(UPDATED_POURQUOI_2);
        assertThat(testRapport.getPourquoi3()).isEqualTo(UPDATED_POURQUOI_3);
        assertThat(testRapport.getPourquoi4()).isEqualTo(UPDATED_POURQUOI_4);
        assertThat(testRapport.getPourquoi5()).isEqualTo(UPDATED_POURQUOI_5);
        assertThat(testRapport.getBras()).isEqualTo(UPDATED_BRAS);
        assertThat(testRapport.getChevilles()).isEqualTo(UPDATED_CHEVILLES);
        assertThat(testRapport.getColonne()).isEqualTo(UPDATED_COLONNE);
        assertThat(testRapport.getCou()).isEqualTo(UPDATED_COU);
        assertThat(testRapport.getCoude()).isEqualTo(UPDATED_COUDE);
        assertThat(testRapport.getCos()).isEqualTo(UPDATED_COS);
        assertThat(testRapport.getEpaule()).isEqualTo(UPDATED_EPAULE);
        assertThat(testRapport.getGenou()).isEqualTo(UPDATED_GENOU);
        assertThat(testRapport.getJambes()).isEqualTo(UPDATED_JAMBES);
        assertThat(testRapport.getMains()).isEqualTo(UPDATED_MAINS);
        assertThat(testRapport.getOeil()).isEqualTo(UPDATED_OEIL);
        assertThat(testRapport.getPieds()).isEqualTo(UPDATED_PIEDS);
        assertThat(testRapport.getPoignet()).isEqualTo(UPDATED_POIGNET);
        assertThat(testRapport.getTete()).isEqualTo(UPDATED_TETE);
        assertThat(testRapport.getTorse()).isEqualTo(UPDATED_TORSE);
    }

    @Test
    @Transactional
    void putNonExistingRapport() throws Exception {
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();
        rapport.setId(count.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rapportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRapport() throws Exception {
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();
        rapport.setId(count.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRapport() throws Exception {
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();
        rapport.setId(count.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rapportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRapportWithPatch() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();

        // Update the rapport using partial update
        Rapport partialUpdatedRapport = new Rapport();
        partialUpdatedRapport.setId(rapport.getId());

        partialUpdatedRapport
            .redacteur(UPDATED_REDACTEUR)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .uap(UPDATED_UAP)
            .prevenuComment(UPDATED_PREVENU_COMMENT)
            .isTemoin(UPDATED_IS_TEMOIN)
            .commentaireTiersEnCause(UPDATED_COMMENTAIRE_TIERS_EN_CAUSE)
            .isVictimeTransports(UPDATED_IS_VICTIME_TRANSPORTS)
            .materielEnCause(UPDATED_MATERIEL_EN_CAUSE)
            .remarques(UPDATED_REMARQUES)
            .pilote(UPDATED_PILOTE)
            .dateEtHeureValidationPilote(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE)
            .porteur(UPDATED_PORTEUR)
            .dateEtHeureValidationPorteur(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR)
            .hse(UPDATED_HSE)
            .isInterventionInfirmiere(UPDATED_IS_INTERVENTION_INFIRMIERE)
            .commentaireInfirmere(UPDATED_COMMENTAIRE_INFIRMERE)
            .isInterventionMedecin(UPDATED_IS_INTERVENTION_MEDECIN)
            .commentaireSecouriste(UPDATED_COMMENTAIRE_SECOURISTE)
            .analyseAChaudCodir(UPDATED_ANALYSE_A_CHAUD_CODIR)
            .analyseAChaudHse(UPDATED_ANALYSE_A_CHAUD_HSE)
            .analyseAChaudNplus1(UPDATED_ANALYSE_A_CHAUD_NPLUS_1)
            .pourquoi2(UPDATED_POURQUOI_2)
            .pourquoi3(UPDATED_POURQUOI_3)
            .colonne(UPDATED_COLONNE)
            .cou(UPDATED_COU)
            .cos(UPDATED_COS)
            .epaule(UPDATED_EPAULE)
            .genou(UPDATED_GENOU)
            .jambes(UPDATED_JAMBES)
            .mains(UPDATED_MAINS)
            .oeil(UPDATED_OEIL)
            .pieds(UPDATED_PIEDS)
            .poignet(UPDATED_POIGNET)
            .tete(UPDATED_TETE)
            .torse(UPDATED_TORSE);

        restRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRapport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRapport))
            )
            .andExpect(status().isOk());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        assertThat(testRapport.getRedacteur()).isEqualTo(UPDATED_REDACTEUR);
        assertThat(testRapport.getDateDeCreation()).isEqualTo(UPDATED_DATE_DE_CREATION);
        assertThat(testRapport.getUap()).isEqualTo(UPDATED_UAP);
        assertThat(testRapport.getDateEtHeureConnaissanceAt()).isEqualTo(DEFAULT_DATE_ET_HEURE_CONNAISSANCE_AT);
        assertThat(testRapport.getPrevenuComment()).isEqualTo(UPDATED_PREVENU_COMMENT);
        assertThat(testRapport.getNomPremierePersonnePrevenu()).isEqualTo(DEFAULT_NOM_PREMIERE_PERSONNE_PREVENU);
        assertThat(testRapport.getDateEtHeurePrevenu()).isEqualTo(DEFAULT_DATE_ET_HEURE_PREVENU);
        assertThat(testRapport.getIsTemoin()).isEqualTo(UPDATED_IS_TEMOIN);
        assertThat(testRapport.getCommentaireTemoin()).isEqualTo(DEFAULT_COMMENTAIRE_TEMOIN);
        assertThat(testRapport.getIsTiersEnCause()).isEqualTo(DEFAULT_IS_TIERS_EN_CAUSE);
        assertThat(testRapport.getCommentaireTiersEnCause()).isEqualTo(UPDATED_COMMENTAIRE_TIERS_EN_CAUSE);
        assertThat(testRapport.getIsAutreVictime()).isEqualTo(DEFAULT_IS_AUTRE_VICTIME);
        assertThat(testRapport.getCommentaireAutreVictime()).isEqualTo(DEFAULT_COMMENTAIRE_AUTRE_VICTIME);
        assertThat(testRapport.getIsRapportDePolice()).isEqualTo(DEFAULT_IS_RAPPORT_DE_POLICE);
        assertThat(testRapport.getCommentaireRapportDePolice()).isEqualTo(DEFAULT_COMMENTAIRE_RAPPORT_DE_POLICE);
        assertThat(testRapport.getIsVictimeTransports()).isEqualTo(UPDATED_IS_VICTIME_TRANSPORTS);
        assertThat(testRapport.getCommentaireVictimeTransporter()).isEqualTo(DEFAULT_COMMENTAIRE_VICTIME_TRANSPORTER);
        assertThat(testRapport.getDateEtHeureMomentAccident()).isEqualTo(DEFAULT_DATE_ET_HEURE_MOMENT_ACCIDENT);
        assertThat(testRapport.getLieuAccident()).isEqualTo(DEFAULT_LIEU_ACCIDENT);
        assertThat(testRapport.getIsIdentifierDu()).isEqualTo(DEFAULT_IS_IDENTIFIER_DU);
        assertThat(testRapport.getCirconstanceAccident()).isEqualTo(DEFAULT_CIRCONSTANCE_ACCIDENT);
        assertThat(testRapport.getMaterielEnCause()).isEqualTo(UPDATED_MATERIEL_EN_CAUSE);
        assertThat(testRapport.getRemarques()).isEqualTo(UPDATED_REMARQUES);
        assertThat(testRapport.getPilote()).isEqualTo(UPDATED_PILOTE);
        assertThat(testRapport.getDateEtHeureValidationPilote()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE);
        assertThat(testRapport.getPorteur()).isEqualTo(UPDATED_PORTEUR);
        assertThat(testRapport.getDateEtHeureValidationPorteur()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR);
        assertThat(testRapport.getHse()).isEqualTo(UPDATED_HSE);
        assertThat(testRapport.getDateEtHeureValidationHse()).isEqualTo(DEFAULT_DATE_ET_HEURE_VALIDATION_HSE);
        assertThat(testRapport.getIsIntervention8300()).isEqualTo(DEFAULT_IS_INTERVENTION_8300);
        assertThat(testRapport.getIsInterventionInfirmiere()).isEqualTo(UPDATED_IS_INTERVENTION_INFIRMIERE);
        assertThat(testRapport.getCommentaireInfirmere()).isEqualTo(UPDATED_COMMENTAIRE_INFIRMERE);
        assertThat(testRapport.getIsInterventionMedecin()).isEqualTo(UPDATED_IS_INTERVENTION_MEDECIN);
        assertThat(testRapport.getCommentaireMedecin()).isEqualTo(DEFAULT_COMMENTAIRE_MEDECIN);
        assertThat(testRapport.getIsInterventionsecouriste()).isEqualTo(DEFAULT_IS_INTERVENTIONSECOURISTE);
        assertThat(testRapport.getCommentaireSecouriste()).isEqualTo(UPDATED_COMMENTAIRE_SECOURISTE);
        assertThat(testRapport.getIsInterventionsecouristeExterieur()).isEqualTo(DEFAULT_IS_INTERVENTIONSECOURISTE_EXTERIEUR);
        assertThat(testRapport.getRetourAuPosteDeTravail()).isEqualTo(DEFAULT_RETOUR_AU_POSTE_DE_TRAVAIL);
        assertThat(testRapport.getTravailLegerPossible()).isEqualTo(DEFAULT_TRAVAIL_LEGER_POSSIBLE);
        assertThat(testRapport.getAnalyseAChaudInfirmiere()).isEqualTo(DEFAULT_ANALYSE_A_CHAUD_INFIRMIERE);
        assertThat(testRapport.getAnalyseAChaudCodir()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_CODIR);
        assertThat(testRapport.getAnalyseAChaudHse()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_HSE);
        assertThat(testRapport.getAnalyseAChaudNplus1()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_NPLUS_1);
        assertThat(testRapport.getAnalyseAChaudCssCt()).isEqualTo(DEFAULT_ANALYSE_A_CHAUD_CSS_CT);
        assertThat(testRapport.getAnalyseAChaudCommentaire()).isEqualTo(DEFAULT_ANALYSE_A_CHAUD_COMMENTAIRE);
        assertThat(testRapport.getPourquoi1()).isEqualTo(DEFAULT_POURQUOI_1);
        assertThat(testRapport.getPourquoi2()).isEqualTo(UPDATED_POURQUOI_2);
        assertThat(testRapport.getPourquoi3()).isEqualTo(UPDATED_POURQUOI_3);
        assertThat(testRapport.getPourquoi4()).isEqualTo(DEFAULT_POURQUOI_4);
        assertThat(testRapport.getPourquoi5()).isEqualTo(DEFAULT_POURQUOI_5);
        assertThat(testRapport.getBras()).isEqualTo(DEFAULT_BRAS);
        assertThat(testRapport.getChevilles()).isEqualTo(DEFAULT_CHEVILLES);
        assertThat(testRapport.getColonne()).isEqualTo(UPDATED_COLONNE);
        assertThat(testRapport.getCou()).isEqualTo(UPDATED_COU);
        assertThat(testRapport.getCoude()).isEqualTo(DEFAULT_COUDE);
        assertThat(testRapport.getCos()).isEqualTo(UPDATED_COS);
        assertThat(testRapport.getEpaule()).isEqualTo(UPDATED_EPAULE);
        assertThat(testRapport.getGenou()).isEqualTo(UPDATED_GENOU);
        assertThat(testRapport.getJambes()).isEqualTo(UPDATED_JAMBES);
        assertThat(testRapport.getMains()).isEqualTo(UPDATED_MAINS);
        assertThat(testRapport.getOeil()).isEqualTo(UPDATED_OEIL);
        assertThat(testRapport.getPieds()).isEqualTo(UPDATED_PIEDS);
        assertThat(testRapport.getPoignet()).isEqualTo(UPDATED_POIGNET);
        assertThat(testRapport.getTete()).isEqualTo(UPDATED_TETE);
        assertThat(testRapport.getTorse()).isEqualTo(UPDATED_TORSE);
    }

    @Test
    @Transactional
    void fullUpdateRapportWithPatch() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();

        // Update the rapport using partial update
        Rapport partialUpdatedRapport = new Rapport();
        partialUpdatedRapport.setId(rapport.getId());

        partialUpdatedRapport
            .redacteur(UPDATED_REDACTEUR)
            .dateDeCreation(UPDATED_DATE_DE_CREATION)
            .uap(UPDATED_UAP)
            .dateEtHeureConnaissanceAt(UPDATED_DATE_ET_HEURE_CONNAISSANCE_AT)
            .prevenuComment(UPDATED_PREVENU_COMMENT)
            .nomPremierePersonnePrevenu(UPDATED_NOM_PREMIERE_PERSONNE_PREVENU)
            .dateEtHeurePrevenu(UPDATED_DATE_ET_HEURE_PREVENU)
            .isTemoin(UPDATED_IS_TEMOIN)
            .commentaireTemoin(UPDATED_COMMENTAIRE_TEMOIN)
            .isTiersEnCause(UPDATED_IS_TIERS_EN_CAUSE)
            .commentaireTiersEnCause(UPDATED_COMMENTAIRE_TIERS_EN_CAUSE)
            .isAutreVictime(UPDATED_IS_AUTRE_VICTIME)
            .commentaireAutreVictime(UPDATED_COMMENTAIRE_AUTRE_VICTIME)
            .isRapportDePolice(UPDATED_IS_RAPPORT_DE_POLICE)
            .commentaireRapportDePolice(UPDATED_COMMENTAIRE_RAPPORT_DE_POLICE)
            .isVictimeTransports(UPDATED_IS_VICTIME_TRANSPORTS)
            .commentaireVictimeTransporter(UPDATED_COMMENTAIRE_VICTIME_TRANSPORTER)
            .dateEtHeureMomentAccident(UPDATED_DATE_ET_HEURE_MOMENT_ACCIDENT)
            .lieuAccident(UPDATED_LIEU_ACCIDENT)
            .isIdentifierDu(UPDATED_IS_IDENTIFIER_DU)
            .circonstanceAccident(UPDATED_CIRCONSTANCE_ACCIDENT)
            .materielEnCause(UPDATED_MATERIEL_EN_CAUSE)
            .remarques(UPDATED_REMARQUES)
            .pilote(UPDATED_PILOTE)
            .dateEtHeureValidationPilote(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE)
            .porteur(UPDATED_PORTEUR)
            .dateEtHeureValidationPorteur(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR)
            .hse(UPDATED_HSE)
            .dateEtHeureValidationHse(UPDATED_DATE_ET_HEURE_VALIDATION_HSE)
            .isIntervention8300(UPDATED_IS_INTERVENTION_8300)
            .isInterventionInfirmiere(UPDATED_IS_INTERVENTION_INFIRMIERE)
            .commentaireInfirmere(UPDATED_COMMENTAIRE_INFIRMERE)
            .isInterventionMedecin(UPDATED_IS_INTERVENTION_MEDECIN)
            .commentaireMedecin(UPDATED_COMMENTAIRE_MEDECIN)
            .isInterventionsecouriste(UPDATED_IS_INTERVENTIONSECOURISTE)
            .commentaireSecouriste(UPDATED_COMMENTAIRE_SECOURISTE)
            .isInterventionsecouristeExterieur(UPDATED_IS_INTERVENTIONSECOURISTE_EXTERIEUR)
            .retourAuPosteDeTravail(UPDATED_RETOUR_AU_POSTE_DE_TRAVAIL)
            .travailLegerPossible(UPDATED_TRAVAIL_LEGER_POSSIBLE)
            .analyseAChaudInfirmiere(UPDATED_ANALYSE_A_CHAUD_INFIRMIERE)
            .analyseAChaudCodir(UPDATED_ANALYSE_A_CHAUD_CODIR)
            .analyseAChaudHse(UPDATED_ANALYSE_A_CHAUD_HSE)
            .analyseAChaudNplus1(UPDATED_ANALYSE_A_CHAUD_NPLUS_1)
            .analyseAChaudCssCt(UPDATED_ANALYSE_A_CHAUD_CSS_CT)
            .analyseAChaudCommentaire(UPDATED_ANALYSE_A_CHAUD_COMMENTAIRE)
            .pourquoi1(UPDATED_POURQUOI_1)
            .pourquoi2(UPDATED_POURQUOI_2)
            .pourquoi3(UPDATED_POURQUOI_3)
            .pourquoi4(UPDATED_POURQUOI_4)
            .pourquoi5(UPDATED_POURQUOI_5)
            .bras(UPDATED_BRAS)
            .chevilles(UPDATED_CHEVILLES)
            .colonne(UPDATED_COLONNE)
            .cou(UPDATED_COU)
            .coude(UPDATED_COUDE)
            .cos(UPDATED_COS)
            .epaule(UPDATED_EPAULE)
            .genou(UPDATED_GENOU)
            .jambes(UPDATED_JAMBES)
            .mains(UPDATED_MAINS)
            .oeil(UPDATED_OEIL)
            .pieds(UPDATED_PIEDS)
            .poignet(UPDATED_POIGNET)
            .tete(UPDATED_TETE)
            .torse(UPDATED_TORSE);

        restRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRapport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRapport))
            )
            .andExpect(status().isOk());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
        Rapport testRapport = rapportList.get(rapportList.size() - 1);
        assertThat(testRapport.getRedacteur()).isEqualTo(UPDATED_REDACTEUR);
        assertThat(testRapport.getDateDeCreation()).isEqualTo(UPDATED_DATE_DE_CREATION);
        assertThat(testRapport.getUap()).isEqualTo(UPDATED_UAP);
        assertThat(testRapport.getDateEtHeureConnaissanceAt()).isEqualTo(UPDATED_DATE_ET_HEURE_CONNAISSANCE_AT);
        assertThat(testRapport.getPrevenuComment()).isEqualTo(UPDATED_PREVENU_COMMENT);
        assertThat(testRapport.getNomPremierePersonnePrevenu()).isEqualTo(UPDATED_NOM_PREMIERE_PERSONNE_PREVENU);
        assertThat(testRapport.getDateEtHeurePrevenu()).isEqualTo(UPDATED_DATE_ET_HEURE_PREVENU);
        assertThat(testRapport.getIsTemoin()).isEqualTo(UPDATED_IS_TEMOIN);
        assertThat(testRapport.getCommentaireTemoin()).isEqualTo(UPDATED_COMMENTAIRE_TEMOIN);
        assertThat(testRapport.getIsTiersEnCause()).isEqualTo(UPDATED_IS_TIERS_EN_CAUSE);
        assertThat(testRapport.getCommentaireTiersEnCause()).isEqualTo(UPDATED_COMMENTAIRE_TIERS_EN_CAUSE);
        assertThat(testRapport.getIsAutreVictime()).isEqualTo(UPDATED_IS_AUTRE_VICTIME);
        assertThat(testRapport.getCommentaireAutreVictime()).isEqualTo(UPDATED_COMMENTAIRE_AUTRE_VICTIME);
        assertThat(testRapport.getIsRapportDePolice()).isEqualTo(UPDATED_IS_RAPPORT_DE_POLICE);
        assertThat(testRapport.getCommentaireRapportDePolice()).isEqualTo(UPDATED_COMMENTAIRE_RAPPORT_DE_POLICE);
        assertThat(testRapport.getIsVictimeTransports()).isEqualTo(UPDATED_IS_VICTIME_TRANSPORTS);
        assertThat(testRapport.getCommentaireVictimeTransporter()).isEqualTo(UPDATED_COMMENTAIRE_VICTIME_TRANSPORTER);
        assertThat(testRapport.getDateEtHeureMomentAccident()).isEqualTo(UPDATED_DATE_ET_HEURE_MOMENT_ACCIDENT);
        assertThat(testRapport.getLieuAccident()).isEqualTo(UPDATED_LIEU_ACCIDENT);
        assertThat(testRapport.getIsIdentifierDu()).isEqualTo(UPDATED_IS_IDENTIFIER_DU);
        assertThat(testRapport.getCirconstanceAccident()).isEqualTo(UPDATED_CIRCONSTANCE_ACCIDENT);
        assertThat(testRapport.getMaterielEnCause()).isEqualTo(UPDATED_MATERIEL_EN_CAUSE);
        assertThat(testRapport.getRemarques()).isEqualTo(UPDATED_REMARQUES);
        assertThat(testRapport.getPilote()).isEqualTo(UPDATED_PILOTE);
        assertThat(testRapport.getDateEtHeureValidationPilote()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PILOTE);
        assertThat(testRapport.getPorteur()).isEqualTo(UPDATED_PORTEUR);
        assertThat(testRapport.getDateEtHeureValidationPorteur()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_PORTEUR);
        assertThat(testRapport.getHse()).isEqualTo(UPDATED_HSE);
        assertThat(testRapport.getDateEtHeureValidationHse()).isEqualTo(UPDATED_DATE_ET_HEURE_VALIDATION_HSE);
        assertThat(testRapport.getIsIntervention8300()).isEqualTo(UPDATED_IS_INTERVENTION_8300);
        assertThat(testRapport.getIsInterventionInfirmiere()).isEqualTo(UPDATED_IS_INTERVENTION_INFIRMIERE);
        assertThat(testRapport.getCommentaireInfirmere()).isEqualTo(UPDATED_COMMENTAIRE_INFIRMERE);
        assertThat(testRapport.getIsInterventionMedecin()).isEqualTo(UPDATED_IS_INTERVENTION_MEDECIN);
        assertThat(testRapport.getCommentaireMedecin()).isEqualTo(UPDATED_COMMENTAIRE_MEDECIN);
        assertThat(testRapport.getIsInterventionsecouriste()).isEqualTo(UPDATED_IS_INTERVENTIONSECOURISTE);
        assertThat(testRapport.getCommentaireSecouriste()).isEqualTo(UPDATED_COMMENTAIRE_SECOURISTE);
        assertThat(testRapport.getIsInterventionsecouristeExterieur()).isEqualTo(UPDATED_IS_INTERVENTIONSECOURISTE_EXTERIEUR);
        assertThat(testRapport.getRetourAuPosteDeTravail()).isEqualTo(UPDATED_RETOUR_AU_POSTE_DE_TRAVAIL);
        assertThat(testRapport.getTravailLegerPossible()).isEqualTo(UPDATED_TRAVAIL_LEGER_POSSIBLE);
        assertThat(testRapport.getAnalyseAChaudInfirmiere()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_INFIRMIERE);
        assertThat(testRapport.getAnalyseAChaudCodir()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_CODIR);
        assertThat(testRapport.getAnalyseAChaudHse()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_HSE);
        assertThat(testRapport.getAnalyseAChaudNplus1()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_NPLUS_1);
        assertThat(testRapport.getAnalyseAChaudCssCt()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_CSS_CT);
        assertThat(testRapport.getAnalyseAChaudCommentaire()).isEqualTo(UPDATED_ANALYSE_A_CHAUD_COMMENTAIRE);
        assertThat(testRapport.getPourquoi1()).isEqualTo(UPDATED_POURQUOI_1);
        assertThat(testRapport.getPourquoi2()).isEqualTo(UPDATED_POURQUOI_2);
        assertThat(testRapport.getPourquoi3()).isEqualTo(UPDATED_POURQUOI_3);
        assertThat(testRapport.getPourquoi4()).isEqualTo(UPDATED_POURQUOI_4);
        assertThat(testRapport.getPourquoi5()).isEqualTo(UPDATED_POURQUOI_5);
        assertThat(testRapport.getBras()).isEqualTo(UPDATED_BRAS);
        assertThat(testRapport.getChevilles()).isEqualTo(UPDATED_CHEVILLES);
        assertThat(testRapport.getColonne()).isEqualTo(UPDATED_COLONNE);
        assertThat(testRapport.getCou()).isEqualTo(UPDATED_COU);
        assertThat(testRapport.getCoude()).isEqualTo(UPDATED_COUDE);
        assertThat(testRapport.getCos()).isEqualTo(UPDATED_COS);
        assertThat(testRapport.getEpaule()).isEqualTo(UPDATED_EPAULE);
        assertThat(testRapport.getGenou()).isEqualTo(UPDATED_GENOU);
        assertThat(testRapport.getJambes()).isEqualTo(UPDATED_JAMBES);
        assertThat(testRapport.getMains()).isEqualTo(UPDATED_MAINS);
        assertThat(testRapport.getOeil()).isEqualTo(UPDATED_OEIL);
        assertThat(testRapport.getPieds()).isEqualTo(UPDATED_PIEDS);
        assertThat(testRapport.getPoignet()).isEqualTo(UPDATED_POIGNET);
        assertThat(testRapport.getTete()).isEqualTo(UPDATED_TETE);
        assertThat(testRapport.getTorse()).isEqualTo(UPDATED_TORSE);
    }

    @Test
    @Transactional
    void patchNonExistingRapport() throws Exception {
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();
        rapport.setId(count.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rapportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRapport() throws Exception {
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();
        rapport.setId(count.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRapport() throws Exception {
        int databaseSizeBeforeUpdate = rapportRepository.findAll().size();
        rapport.setId(count.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rapportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rapport in the database
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        int databaseSizeBeforeDelete = rapportRepository.findAll().size();

        // Delete the rapport
        restRapportMockMvc
            .perform(delete(ENTITY_API_URL_ID, rapport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rapport> rapportList = rapportRepository.findAll();
        assertThat(rapportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
