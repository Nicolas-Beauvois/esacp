package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rapport.
 */
@Entity
@Table(name = "rapport")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rapport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "redacteur")
    private String redacteur;

    @Column(name = "date_de_creation")
    private LocalDate dateDeCreation;

    @Column(name = "uap")
    private String uap;

    @Column(name = "date_et_heure_connaissance_at")
    private LocalDate dateEtHeureConnaissanceAt;

    @Column(name = "prevenu_comment")
    private String prevenuComment;

    @Column(name = "nom_premiere_personne_prevenu")
    private String nomPremierePersonnePrevenu;

    @Column(name = "date_et_heure_prevenu")
    private LocalDate dateEtHeurePrevenu;

    @Column(name = "is_temoin")
    private Boolean isTemoin;

    @Column(name = "commentaire_temoin")
    private String commentaireTemoin;

    @Column(name = "is_tiers_en_cause")
    private Boolean isTiersEnCause;

    @Column(name = "commentaire_tiers_en_cause")
    private String commentaireTiersEnCause;

    @Column(name = "is_autre_victime")
    private Boolean isAutreVictime;

    @Column(name = "commentaire_autre_victime")
    private String commentaireAutreVictime;

    @Column(name = "is_rapport_de_police")
    private Boolean isRapportDePolice;

    @Column(name = "commentaire_rapport_de_police")
    private String commentaireRapportDePolice;

    @Column(name = "is_victime_transports")
    private Boolean isVictimeTransports;

    @Column(name = "commentaire_victime_transporter")
    private String commentaireVictimeTransporter;

    @Column(name = "date_et_heure_moment_accident")
    private LocalDate dateEtHeureMomentAccident;

    @Column(name = "lieu_accident")
    private String lieuAccident;

    @Column(name = "is_identifier_du")
    private Boolean isIdentifierDu;

    @Column(name = "circonstance_accident")
    private String circonstanceAccident;

    @Column(name = "materiel_en_cause")
    private String materielEnCause;

    @Column(name = "remarques")
    private String remarques;

    @Column(name = "pilote")
    private String pilote;

    @Column(name = "date_et_heure_validation_pilote")
    private LocalDate dateEtHeureValidationPilote;

    @Column(name = "porteur")
    private String porteur;

    @Column(name = "date_et_heure_validation_porteur")
    private LocalDate dateEtHeureValidationPorteur;

    @Column(name = "hse")
    private String hse;

    @Column(name = "date_et_heure_validation_hse")
    private LocalDate dateEtHeureValidationHse;

    @Column(name = "is_intervention_8300")
    private Boolean isIntervention8300;

    @Column(name = "is_intervention_infirmiere")
    private Boolean isInterventionInfirmiere;

    @Column(name = "commentaire_infirmere")
    private String commentaireInfirmere;

    @Column(name = "is_intervention_medecin")
    private Boolean isInterventionMedecin;

    @Column(name = "commentaire_medecin")
    private String commentaireMedecin;

    @Column(name = "is_interventionsecouriste")
    private Boolean isInterventionsecouriste;

    @Column(name = "commentaire_secouriste")
    private String commentaireSecouriste;

    @Column(name = "is_interventionsecouriste_exterieur")
    private Boolean isInterventionsecouristeExterieur;

    @Column(name = "retour_au_poste_de_travail")
    private Boolean retourAuPosteDeTravail;

    @Column(name = "travail_leger_possible")
    private String travailLegerPossible;

    @Column(name = "analyse_a_chaud_infirmiere")
    private Boolean analyseAChaudInfirmiere;

    @Column(name = "analyse_a_chaud_codir")
    private Boolean analyseAChaudCodir;

    @Column(name = "analyse_a_chaud_hse")
    private Boolean analyseAChaudHse;

    @Column(name = "analyse_a_chaud_nplus_1")
    private Boolean analyseAChaudNplus1;

    @Column(name = "analyse_a_chaud_css_ct")
    private Boolean analyseAChaudCssCt;

    @Column(name = "analyse_a_chaud_commentaire")
    private String analyseAChaudCommentaire;

    @Column(name = "pourquoi_1")
    private String pourquoi1;

    @Column(name = "pourquoi_2")
    private String pourquoi2;

    @Column(name = "pourquoi_3")
    private String pourquoi3;

    @Column(name = "pourquoi_4")
    private String pourquoi4;

    @Column(name = "pourquoi_5")
    private String pourquoi5;

    @Column(name = "bras")
    private Boolean bras;

    @Column(name = "chevilles")
    private Boolean chevilles;

    @Column(name = "colonne")
    private Boolean colonne;

    @Column(name = "cou")
    private Boolean cou;

    @Column(name = "coude")
    private Boolean coude;

    @Column(name = "cos")
    private Boolean cos;

    @Column(name = "epaule")
    private Boolean epaule;

    @Column(name = "genou")
    private Boolean genou;

    @Column(name = "jambes")
    private Boolean jambes;

    @Column(name = "mains")
    private Boolean mains;

    @Column(name = "oeil")
    private Boolean oeil;

    @Column(name = "pieds")
    private Boolean pieds;

    @Column(name = "poignet")
    private Boolean poignet;

    @Column(name = "tete")
    private Boolean tete;

    @Column(name = "torse")
    private Boolean torse;

    @OneToMany(mappedBy = "rapport")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "rapport", "correctPrevent", "priorite", "etapeValidation", "typeAction", "criticite", "pj" },
        allowSetters = true
    )
    private Set<Actions> actions = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rapports" }, allowSetters = true)
    private SiegeLesions siegeLesions;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rapports", "typeAt" }, allowSetters = true)
    private FicheSuiviSante ficheSuiviSante;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rapports", "repartition" }, allowSetters = true)
    private Type type;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rapports" }, allowSetters = true)
    private Categorie categorie;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rapports" }, allowSetters = true)
    private Equipement equipement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rapports" }, allowSetters = true)
    private TypeRapport typeRapport;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rapports" }, allowSetters = true)
    private NatureAccident natureAccident;

    @ManyToOne
    @JsonIgnoreProperties(value = { "rapports" }, allowSetters = true)
    private OrigineLesions origineLesions;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rapport id(Long id) {
        this.id = id;
        return this;
    }

    public String getRedacteur() {
        return this.redacteur;
    }

    public Rapport redacteur(String redacteur) {
        this.redacteur = redacteur;
        return this;
    }

    public void setRedacteur(String redacteur) {
        this.redacteur = redacteur;
    }

    public LocalDate getDateDeCreation() {
        return this.dateDeCreation;
    }

    public Rapport dateDeCreation(LocalDate dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
        return this;
    }

    public void setDateDeCreation(LocalDate dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public String getUap() {
        return this.uap;
    }

    public Rapport uap(String uap) {
        this.uap = uap;
        return this;
    }

    public void setUap(String uap) {
        this.uap = uap;
    }

    public LocalDate getDateEtHeureConnaissanceAt() {
        return this.dateEtHeureConnaissanceAt;
    }

    public Rapport dateEtHeureConnaissanceAt(LocalDate dateEtHeureConnaissanceAt) {
        this.dateEtHeureConnaissanceAt = dateEtHeureConnaissanceAt;
        return this;
    }

    public void setDateEtHeureConnaissanceAt(LocalDate dateEtHeureConnaissanceAt) {
        this.dateEtHeureConnaissanceAt = dateEtHeureConnaissanceAt;
    }

    public String getPrevenuComment() {
        return this.prevenuComment;
    }

    public Rapport prevenuComment(String prevenuComment) {
        this.prevenuComment = prevenuComment;
        return this;
    }

    public void setPrevenuComment(String prevenuComment) {
        this.prevenuComment = prevenuComment;
    }

    public String getNomPremierePersonnePrevenu() {
        return this.nomPremierePersonnePrevenu;
    }

    public Rapport nomPremierePersonnePrevenu(String nomPremierePersonnePrevenu) {
        this.nomPremierePersonnePrevenu = nomPremierePersonnePrevenu;
        return this;
    }

    public void setNomPremierePersonnePrevenu(String nomPremierePersonnePrevenu) {
        this.nomPremierePersonnePrevenu = nomPremierePersonnePrevenu;
    }

    public LocalDate getDateEtHeurePrevenu() {
        return this.dateEtHeurePrevenu;
    }

    public Rapport dateEtHeurePrevenu(LocalDate dateEtHeurePrevenu) {
        this.dateEtHeurePrevenu = dateEtHeurePrevenu;
        return this;
    }

    public void setDateEtHeurePrevenu(LocalDate dateEtHeurePrevenu) {
        this.dateEtHeurePrevenu = dateEtHeurePrevenu;
    }

    public Boolean getIsTemoin() {
        return this.isTemoin;
    }

    public Rapport isTemoin(Boolean isTemoin) {
        this.isTemoin = isTemoin;
        return this;
    }

    public void setIsTemoin(Boolean isTemoin) {
        this.isTemoin = isTemoin;
    }

    public String getCommentaireTemoin() {
        return this.commentaireTemoin;
    }

    public Rapport commentaireTemoin(String commentaireTemoin) {
        this.commentaireTemoin = commentaireTemoin;
        return this;
    }

    public void setCommentaireTemoin(String commentaireTemoin) {
        this.commentaireTemoin = commentaireTemoin;
    }

    public Boolean getIsTiersEnCause() {
        return this.isTiersEnCause;
    }

    public Rapport isTiersEnCause(Boolean isTiersEnCause) {
        this.isTiersEnCause = isTiersEnCause;
        return this;
    }

    public void setIsTiersEnCause(Boolean isTiersEnCause) {
        this.isTiersEnCause = isTiersEnCause;
    }

    public String getCommentaireTiersEnCause() {
        return this.commentaireTiersEnCause;
    }

    public Rapport commentaireTiersEnCause(String commentaireTiersEnCause) {
        this.commentaireTiersEnCause = commentaireTiersEnCause;
        return this;
    }

    public void setCommentaireTiersEnCause(String commentaireTiersEnCause) {
        this.commentaireTiersEnCause = commentaireTiersEnCause;
    }

    public Boolean getIsAutreVictime() {
        return this.isAutreVictime;
    }

    public Rapport isAutreVictime(Boolean isAutreVictime) {
        this.isAutreVictime = isAutreVictime;
        return this;
    }

    public void setIsAutreVictime(Boolean isAutreVictime) {
        this.isAutreVictime = isAutreVictime;
    }

    public String getCommentaireAutreVictime() {
        return this.commentaireAutreVictime;
    }

    public Rapport commentaireAutreVictime(String commentaireAutreVictime) {
        this.commentaireAutreVictime = commentaireAutreVictime;
        return this;
    }

    public void setCommentaireAutreVictime(String commentaireAutreVictime) {
        this.commentaireAutreVictime = commentaireAutreVictime;
    }

    public Boolean getIsRapportDePolice() {
        return this.isRapportDePolice;
    }

    public Rapport isRapportDePolice(Boolean isRapportDePolice) {
        this.isRapportDePolice = isRapportDePolice;
        return this;
    }

    public void setIsRapportDePolice(Boolean isRapportDePolice) {
        this.isRapportDePolice = isRapportDePolice;
    }

    public String getCommentaireRapportDePolice() {
        return this.commentaireRapportDePolice;
    }

    public Rapport commentaireRapportDePolice(String commentaireRapportDePolice) {
        this.commentaireRapportDePolice = commentaireRapportDePolice;
        return this;
    }

    public void setCommentaireRapportDePolice(String commentaireRapportDePolice) {
        this.commentaireRapportDePolice = commentaireRapportDePolice;
    }

    public Boolean getIsVictimeTransports() {
        return this.isVictimeTransports;
    }

    public Rapport isVictimeTransports(Boolean isVictimeTransports) {
        this.isVictimeTransports = isVictimeTransports;
        return this;
    }

    public void setIsVictimeTransports(Boolean isVictimeTransports) {
        this.isVictimeTransports = isVictimeTransports;
    }

    public String getCommentaireVictimeTransporter() {
        return this.commentaireVictimeTransporter;
    }

    public Rapport commentaireVictimeTransporter(String commentaireVictimeTransporter) {
        this.commentaireVictimeTransporter = commentaireVictimeTransporter;
        return this;
    }

    public void setCommentaireVictimeTransporter(String commentaireVictimeTransporter) {
        this.commentaireVictimeTransporter = commentaireVictimeTransporter;
    }

    public LocalDate getDateEtHeureMomentAccident() {
        return this.dateEtHeureMomentAccident;
    }

    public Rapport dateEtHeureMomentAccident(LocalDate dateEtHeureMomentAccident) {
        this.dateEtHeureMomentAccident = dateEtHeureMomentAccident;
        return this;
    }

    public void setDateEtHeureMomentAccident(LocalDate dateEtHeureMomentAccident) {
        this.dateEtHeureMomentAccident = dateEtHeureMomentAccident;
    }

    public String getLieuAccident() {
        return this.lieuAccident;
    }

    public Rapport lieuAccident(String lieuAccident) {
        this.lieuAccident = lieuAccident;
        return this;
    }

    public void setLieuAccident(String lieuAccident) {
        this.lieuAccident = lieuAccident;
    }

    public Boolean getIsIdentifierDu() {
        return this.isIdentifierDu;
    }

    public Rapport isIdentifierDu(Boolean isIdentifierDu) {
        this.isIdentifierDu = isIdentifierDu;
        return this;
    }

    public void setIsIdentifierDu(Boolean isIdentifierDu) {
        this.isIdentifierDu = isIdentifierDu;
    }

    public String getCirconstanceAccident() {
        return this.circonstanceAccident;
    }

    public Rapport circonstanceAccident(String circonstanceAccident) {
        this.circonstanceAccident = circonstanceAccident;
        return this;
    }

    public void setCirconstanceAccident(String circonstanceAccident) {
        this.circonstanceAccident = circonstanceAccident;
    }

    public String getMaterielEnCause() {
        return this.materielEnCause;
    }

    public Rapport materielEnCause(String materielEnCause) {
        this.materielEnCause = materielEnCause;
        return this;
    }

    public void setMaterielEnCause(String materielEnCause) {
        this.materielEnCause = materielEnCause;
    }

    public String getRemarques() {
        return this.remarques;
    }

    public Rapport remarques(String remarques) {
        this.remarques = remarques;
        return this;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public String getPilote() {
        return this.pilote;
    }

    public Rapport pilote(String pilote) {
        this.pilote = pilote;
        return this;
    }

    public void setPilote(String pilote) {
        this.pilote = pilote;
    }

    public LocalDate getDateEtHeureValidationPilote() {
        return this.dateEtHeureValidationPilote;
    }

    public Rapport dateEtHeureValidationPilote(LocalDate dateEtHeureValidationPilote) {
        this.dateEtHeureValidationPilote = dateEtHeureValidationPilote;
        return this;
    }

    public void setDateEtHeureValidationPilote(LocalDate dateEtHeureValidationPilote) {
        this.dateEtHeureValidationPilote = dateEtHeureValidationPilote;
    }

    public String getPorteur() {
        return this.porteur;
    }

    public Rapport porteur(String porteur) {
        this.porteur = porteur;
        return this;
    }

    public void setPorteur(String porteur) {
        this.porteur = porteur;
    }

    public LocalDate getDateEtHeureValidationPorteur() {
        return this.dateEtHeureValidationPorteur;
    }

    public Rapport dateEtHeureValidationPorteur(LocalDate dateEtHeureValidationPorteur) {
        this.dateEtHeureValidationPorteur = dateEtHeureValidationPorteur;
        return this;
    }

    public void setDateEtHeureValidationPorteur(LocalDate dateEtHeureValidationPorteur) {
        this.dateEtHeureValidationPorteur = dateEtHeureValidationPorteur;
    }

    public String getHse() {
        return this.hse;
    }

    public Rapport hse(String hse) {
        this.hse = hse;
        return this;
    }

    public void setHse(String hse) {
        this.hse = hse;
    }

    public LocalDate getDateEtHeureValidationHse() {
        return this.dateEtHeureValidationHse;
    }

    public Rapport dateEtHeureValidationHse(LocalDate dateEtHeureValidationHse) {
        this.dateEtHeureValidationHse = dateEtHeureValidationHse;
        return this;
    }

    public void setDateEtHeureValidationHse(LocalDate dateEtHeureValidationHse) {
        this.dateEtHeureValidationHse = dateEtHeureValidationHse;
    }

    public Boolean getIsIntervention8300() {
        return this.isIntervention8300;
    }

    public Rapport isIntervention8300(Boolean isIntervention8300) {
        this.isIntervention8300 = isIntervention8300;
        return this;
    }

    public void setIsIntervention8300(Boolean isIntervention8300) {
        this.isIntervention8300 = isIntervention8300;
    }

    public Boolean getIsInterventionInfirmiere() {
        return this.isInterventionInfirmiere;
    }

    public Rapport isInterventionInfirmiere(Boolean isInterventionInfirmiere) {
        this.isInterventionInfirmiere = isInterventionInfirmiere;
        return this;
    }

    public void setIsInterventionInfirmiere(Boolean isInterventionInfirmiere) {
        this.isInterventionInfirmiere = isInterventionInfirmiere;
    }

    public String getCommentaireInfirmere() {
        return this.commentaireInfirmere;
    }

    public Rapport commentaireInfirmere(String commentaireInfirmere) {
        this.commentaireInfirmere = commentaireInfirmere;
        return this;
    }

    public void setCommentaireInfirmere(String commentaireInfirmere) {
        this.commentaireInfirmere = commentaireInfirmere;
    }

    public Boolean getIsInterventionMedecin() {
        return this.isInterventionMedecin;
    }

    public Rapport isInterventionMedecin(Boolean isInterventionMedecin) {
        this.isInterventionMedecin = isInterventionMedecin;
        return this;
    }

    public void setIsInterventionMedecin(Boolean isInterventionMedecin) {
        this.isInterventionMedecin = isInterventionMedecin;
    }

    public String getCommentaireMedecin() {
        return this.commentaireMedecin;
    }

    public Rapport commentaireMedecin(String commentaireMedecin) {
        this.commentaireMedecin = commentaireMedecin;
        return this;
    }

    public void setCommentaireMedecin(String commentaireMedecin) {
        this.commentaireMedecin = commentaireMedecin;
    }

    public Boolean getIsInterventionsecouriste() {
        return this.isInterventionsecouriste;
    }

    public Rapport isInterventionsecouriste(Boolean isInterventionsecouriste) {
        this.isInterventionsecouriste = isInterventionsecouriste;
        return this;
    }

    public void setIsInterventionsecouriste(Boolean isInterventionsecouriste) {
        this.isInterventionsecouriste = isInterventionsecouriste;
    }

    public String getCommentaireSecouriste() {
        return this.commentaireSecouriste;
    }

    public Rapport commentaireSecouriste(String commentaireSecouriste) {
        this.commentaireSecouriste = commentaireSecouriste;
        return this;
    }

    public void setCommentaireSecouriste(String commentaireSecouriste) {
        this.commentaireSecouriste = commentaireSecouriste;
    }

    public Boolean getIsInterventionsecouristeExterieur() {
        return this.isInterventionsecouristeExterieur;
    }

    public Rapport isInterventionsecouristeExterieur(Boolean isInterventionsecouristeExterieur) {
        this.isInterventionsecouristeExterieur = isInterventionsecouristeExterieur;
        return this;
    }

    public void setIsInterventionsecouristeExterieur(Boolean isInterventionsecouristeExterieur) {
        this.isInterventionsecouristeExterieur = isInterventionsecouristeExterieur;
    }

    public Boolean getRetourAuPosteDeTravail() {
        return this.retourAuPosteDeTravail;
    }

    public Rapport retourAuPosteDeTravail(Boolean retourAuPosteDeTravail) {
        this.retourAuPosteDeTravail = retourAuPosteDeTravail;
        return this;
    }

    public void setRetourAuPosteDeTravail(Boolean retourAuPosteDeTravail) {
        this.retourAuPosteDeTravail = retourAuPosteDeTravail;
    }

    public String getTravailLegerPossible() {
        return this.travailLegerPossible;
    }

    public Rapport travailLegerPossible(String travailLegerPossible) {
        this.travailLegerPossible = travailLegerPossible;
        return this;
    }

    public void setTravailLegerPossible(String travailLegerPossible) {
        this.travailLegerPossible = travailLegerPossible;
    }

    public Boolean getAnalyseAChaudInfirmiere() {
        return this.analyseAChaudInfirmiere;
    }

    public Rapport analyseAChaudInfirmiere(Boolean analyseAChaudInfirmiere) {
        this.analyseAChaudInfirmiere = analyseAChaudInfirmiere;
        return this;
    }

    public void setAnalyseAChaudInfirmiere(Boolean analyseAChaudInfirmiere) {
        this.analyseAChaudInfirmiere = analyseAChaudInfirmiere;
    }

    public Boolean getAnalyseAChaudCodir() {
        return this.analyseAChaudCodir;
    }

    public Rapport analyseAChaudCodir(Boolean analyseAChaudCodir) {
        this.analyseAChaudCodir = analyseAChaudCodir;
        return this;
    }

    public void setAnalyseAChaudCodir(Boolean analyseAChaudCodir) {
        this.analyseAChaudCodir = analyseAChaudCodir;
    }

    public Boolean getAnalyseAChaudHse() {
        return this.analyseAChaudHse;
    }

    public Rapport analyseAChaudHse(Boolean analyseAChaudHse) {
        this.analyseAChaudHse = analyseAChaudHse;
        return this;
    }

    public void setAnalyseAChaudHse(Boolean analyseAChaudHse) {
        this.analyseAChaudHse = analyseAChaudHse;
    }

    public Boolean getAnalyseAChaudNplus1() {
        return this.analyseAChaudNplus1;
    }

    public Rapport analyseAChaudNplus1(Boolean analyseAChaudNplus1) {
        this.analyseAChaudNplus1 = analyseAChaudNplus1;
        return this;
    }

    public void setAnalyseAChaudNplus1(Boolean analyseAChaudNplus1) {
        this.analyseAChaudNplus1 = analyseAChaudNplus1;
    }

    public Boolean getAnalyseAChaudCssCt() {
        return this.analyseAChaudCssCt;
    }

    public Rapport analyseAChaudCssCt(Boolean analyseAChaudCssCt) {
        this.analyseAChaudCssCt = analyseAChaudCssCt;
        return this;
    }

    public void setAnalyseAChaudCssCt(Boolean analyseAChaudCssCt) {
        this.analyseAChaudCssCt = analyseAChaudCssCt;
    }

    public String getAnalyseAChaudCommentaire() {
        return this.analyseAChaudCommentaire;
    }

    public Rapport analyseAChaudCommentaire(String analyseAChaudCommentaire) {
        this.analyseAChaudCommentaire = analyseAChaudCommentaire;
        return this;
    }

    public void setAnalyseAChaudCommentaire(String analyseAChaudCommentaire) {
        this.analyseAChaudCommentaire = analyseAChaudCommentaire;
    }

    public String getPourquoi1() {
        return this.pourquoi1;
    }

    public Rapport pourquoi1(String pourquoi1) {
        this.pourquoi1 = pourquoi1;
        return this;
    }

    public void setPourquoi1(String pourquoi1) {
        this.pourquoi1 = pourquoi1;
    }

    public String getPourquoi2() {
        return this.pourquoi2;
    }

    public Rapport pourquoi2(String pourquoi2) {
        this.pourquoi2 = pourquoi2;
        return this;
    }

    public void setPourquoi2(String pourquoi2) {
        this.pourquoi2 = pourquoi2;
    }

    public String getPourquoi3() {
        return this.pourquoi3;
    }

    public Rapport pourquoi3(String pourquoi3) {
        this.pourquoi3 = pourquoi3;
        return this;
    }

    public void setPourquoi3(String pourquoi3) {
        this.pourquoi3 = pourquoi3;
    }

    public String getPourquoi4() {
        return this.pourquoi4;
    }

    public Rapport pourquoi4(String pourquoi4) {
        this.pourquoi4 = pourquoi4;
        return this;
    }

    public void setPourquoi4(String pourquoi4) {
        this.pourquoi4 = pourquoi4;
    }

    public String getPourquoi5() {
        return this.pourquoi5;
    }

    public Rapport pourquoi5(String pourquoi5) {
        this.pourquoi5 = pourquoi5;
        return this;
    }

    public void setPourquoi5(String pourquoi5) {
        this.pourquoi5 = pourquoi5;
    }

    public Boolean getBras() {
        return this.bras;
    }

    public Rapport bras(Boolean bras) {
        this.bras = bras;
        return this;
    }

    public void setBras(Boolean bras) {
        this.bras = bras;
    }

    public Boolean getChevilles() {
        return this.chevilles;
    }

    public Rapport chevilles(Boolean chevilles) {
        this.chevilles = chevilles;
        return this;
    }

    public void setChevilles(Boolean chevilles) {
        this.chevilles = chevilles;
    }

    public Boolean getColonne() {
        return this.colonne;
    }

    public Rapport colonne(Boolean colonne) {
        this.colonne = colonne;
        return this;
    }

    public void setColonne(Boolean colonne) {
        this.colonne = colonne;
    }

    public Boolean getCou() {
        return this.cou;
    }

    public Rapport cou(Boolean cou) {
        this.cou = cou;
        return this;
    }

    public void setCou(Boolean cou) {
        this.cou = cou;
    }

    public Boolean getCoude() {
        return this.coude;
    }

    public Rapport coude(Boolean coude) {
        this.coude = coude;
        return this;
    }

    public void setCoude(Boolean coude) {
        this.coude = coude;
    }

    public Boolean getCos() {
        return this.cos;
    }

    public Rapport cos(Boolean cos) {
        this.cos = cos;
        return this;
    }

    public void setCos(Boolean cos) {
        this.cos = cos;
    }

    public Boolean getEpaule() {
        return this.epaule;
    }

    public Rapport epaule(Boolean epaule) {
        this.epaule = epaule;
        return this;
    }

    public void setEpaule(Boolean epaule) {
        this.epaule = epaule;
    }

    public Boolean getGenou() {
        return this.genou;
    }

    public Rapport genou(Boolean genou) {
        this.genou = genou;
        return this;
    }

    public void setGenou(Boolean genou) {
        this.genou = genou;
    }

    public Boolean getJambes() {
        return this.jambes;
    }

    public Rapport jambes(Boolean jambes) {
        this.jambes = jambes;
        return this;
    }

    public void setJambes(Boolean jambes) {
        this.jambes = jambes;
    }

    public Boolean getMains() {
        return this.mains;
    }

    public Rapport mains(Boolean mains) {
        this.mains = mains;
        return this;
    }

    public void setMains(Boolean mains) {
        this.mains = mains;
    }

    public Boolean getOeil() {
        return this.oeil;
    }

    public Rapport oeil(Boolean oeil) {
        this.oeil = oeil;
        return this;
    }

    public void setOeil(Boolean oeil) {
        this.oeil = oeil;
    }

    public Boolean getPieds() {
        return this.pieds;
    }

    public Rapport pieds(Boolean pieds) {
        this.pieds = pieds;
        return this;
    }

    public void setPieds(Boolean pieds) {
        this.pieds = pieds;
    }

    public Boolean getPoignet() {
        return this.poignet;
    }

    public Rapport poignet(Boolean poignet) {
        this.poignet = poignet;
        return this;
    }

    public void setPoignet(Boolean poignet) {
        this.poignet = poignet;
    }

    public Boolean getTete() {
        return this.tete;
    }

    public Rapport tete(Boolean tete) {
        this.tete = tete;
        return this;
    }

    public void setTete(Boolean tete) {
        this.tete = tete;
    }

    public Boolean getTorse() {
        return this.torse;
    }

    public Rapport torse(Boolean torse) {
        this.torse = torse;
        return this;
    }

    public void setTorse(Boolean torse) {
        this.torse = torse;
    }

    public Set<Actions> getActions() {
        return this.actions;
    }

    public Rapport actions(Set<Actions> actions) {
        this.setActions(actions);
        return this;
    }

    public Rapport addActions(Actions actions) {
        this.actions.add(actions);
        actions.setRapport(this);
        return this;
    }

    public Rapport removeActions(Actions actions) {
        this.actions.remove(actions);
        actions.setRapport(null);
        return this;
    }

    public void setActions(Set<Actions> actions) {
        if (this.actions != null) {
            this.actions.forEach(i -> i.setRapport(null));
        }
        if (actions != null) {
            actions.forEach(i -> i.setRapport(this));
        }
        this.actions = actions;
    }

    public User getUser() {
        return this.user;
    }

    public Rapport user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SiegeLesions getSiegeLesions() {
        return this.siegeLesions;
    }

    public Rapport siegeLesions(SiegeLesions siegeLesions) {
        this.setSiegeLesions(siegeLesions);
        return this;
    }

    public void setSiegeLesions(SiegeLesions siegeLesions) {
        this.siegeLesions = siegeLesions;
    }

    public FicheSuiviSante getFicheSuiviSante() {
        return this.ficheSuiviSante;
    }

    public Rapport ficheSuiviSante(FicheSuiviSante ficheSuiviSante) {
        this.setFicheSuiviSante(ficheSuiviSante);
        return this;
    }

    public void setFicheSuiviSante(FicheSuiviSante ficheSuiviSante) {
        this.ficheSuiviSante = ficheSuiviSante;
    }

    public Type getType() {
        return this.type;
    }

    public Rapport type(Type type) {
        this.setType(type);
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public Rapport categorie(Categorie categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Equipement getEquipement() {
        return this.equipement;
    }

    public Rapport equipement(Equipement equipement) {
        this.setEquipement(equipement);
        return this;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }

    public TypeRapport getTypeRapport() {
        return this.typeRapport;
    }

    public Rapport typeRapport(TypeRapport typeRapport) {
        this.setTypeRapport(typeRapport);
        return this;
    }

    public void setTypeRapport(TypeRapport typeRapport) {
        this.typeRapport = typeRapport;
    }

    public NatureAccident getNatureAccident() {
        return this.natureAccident;
    }

    public Rapport natureAccident(NatureAccident natureAccident) {
        this.setNatureAccident(natureAccident);
        return this;
    }

    public void setNatureAccident(NatureAccident natureAccident) {
        this.natureAccident = natureAccident;
    }

    public OrigineLesions getOrigineLesions() {
        return this.origineLesions;
    }

    public Rapport origineLesions(OrigineLesions origineLesions) {
        this.setOrigineLesions(origineLesions);
        return this;
    }

    public void setOrigineLesions(OrigineLesions origineLesions) {
        this.origineLesions = origineLesions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rapport)) {
            return false;
        }
        return id != null && id.equals(((Rapport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rapport{" +
            "id=" + getId() +
            ", redacteur='" + getRedacteur() + "'" +
            ", dateDeCreation='" + getDateDeCreation() + "'" +
            ", uap='" + getUap() + "'" +
            ", dateEtHeureConnaissanceAt='" + getDateEtHeureConnaissanceAt() + "'" +
            ", prevenuComment='" + getPrevenuComment() + "'" +
            ", nomPremierePersonnePrevenu='" + getNomPremierePersonnePrevenu() + "'" +
            ", dateEtHeurePrevenu='" + getDateEtHeurePrevenu() + "'" +
            ", isTemoin='" + getIsTemoin() + "'" +
            ", commentaireTemoin='" + getCommentaireTemoin() + "'" +
            ", isTiersEnCause='" + getIsTiersEnCause() + "'" +
            ", commentaireTiersEnCause='" + getCommentaireTiersEnCause() + "'" +
            ", isAutreVictime='" + getIsAutreVictime() + "'" +
            ", commentaireAutreVictime='" + getCommentaireAutreVictime() + "'" +
            ", isRapportDePolice='" + getIsRapportDePolice() + "'" +
            ", commentaireRapportDePolice='" + getCommentaireRapportDePolice() + "'" +
            ", isVictimeTransports='" + getIsVictimeTransports() + "'" +
            ", commentaireVictimeTransporter='" + getCommentaireVictimeTransporter() + "'" +
            ", dateEtHeureMomentAccident='" + getDateEtHeureMomentAccident() + "'" +
            ", lieuAccident='" + getLieuAccident() + "'" +
            ", isIdentifierDu='" + getIsIdentifierDu() + "'" +
            ", circonstanceAccident='" + getCirconstanceAccident() + "'" +
            ", materielEnCause='" + getMaterielEnCause() + "'" +
            ", remarques='" + getRemarques() + "'" +
            ", pilote='" + getPilote() + "'" +
            ", dateEtHeureValidationPilote='" + getDateEtHeureValidationPilote() + "'" +
            ", porteur='" + getPorteur() + "'" +
            ", dateEtHeureValidationPorteur='" + getDateEtHeureValidationPorteur() + "'" +
            ", hse='" + getHse() + "'" +
            ", dateEtHeureValidationHse='" + getDateEtHeureValidationHse() + "'" +
            ", isIntervention8300='" + getIsIntervention8300() + "'" +
            ", isInterventionInfirmiere='" + getIsInterventionInfirmiere() + "'" +
            ", commentaireInfirmere='" + getCommentaireInfirmere() + "'" +
            ", isInterventionMedecin='" + getIsInterventionMedecin() + "'" +
            ", commentaireMedecin='" + getCommentaireMedecin() + "'" +
            ", isInterventionsecouriste='" + getIsInterventionsecouriste() + "'" +
            ", commentaireSecouriste='" + getCommentaireSecouriste() + "'" +
            ", isInterventionsecouristeExterieur='" + getIsInterventionsecouristeExterieur() + "'" +
            ", retourAuPosteDeTravail='" + getRetourAuPosteDeTravail() + "'" +
            ", travailLegerPossible='" + getTravailLegerPossible() + "'" +
            ", analyseAChaudInfirmiere='" + getAnalyseAChaudInfirmiere() + "'" +
            ", analyseAChaudCodir='" + getAnalyseAChaudCodir() + "'" +
            ", analyseAChaudHse='" + getAnalyseAChaudHse() + "'" +
            ", analyseAChaudNplus1='" + getAnalyseAChaudNplus1() + "'" +
            ", analyseAChaudCssCt='" + getAnalyseAChaudCssCt() + "'" +
            ", analyseAChaudCommentaire='" + getAnalyseAChaudCommentaire() + "'" +
            ", pourquoi1='" + getPourquoi1() + "'" +
            ", pourquoi2='" + getPourquoi2() + "'" +
            ", pourquoi3='" + getPourquoi3() + "'" +
            ", pourquoi4='" + getPourquoi4() + "'" +
            ", pourquoi5='" + getPourquoi5() + "'" +
            ", bras='" + getBras() + "'" +
            ", chevilles='" + getChevilles() + "'" +
            ", colonne='" + getColonne() + "'" +
            ", cou='" + getCou() + "'" +
            ", coude='" + getCoude() + "'" +
            ", cos='" + getCos() + "'" +
            ", epaule='" + getEpaule() + "'" +
            ", genou='" + getGenou() + "'" +
            ", jambes='" + getJambes() + "'" +
            ", mains='" + getMains() + "'" +
            ", oeil='" + getOeil() + "'" +
            ", pieds='" + getPieds() + "'" +
            ", poignet='" + getPoignet() + "'" +
            ", tete='" + getTete() + "'" +
            ", torse='" + getTorse() + "'" +
            "}";
    }
}
