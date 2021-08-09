package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Rapport} entity.
 */
public class RapportDTO implements Serializable {

    private Long id;

    private String redacteur;

    private LocalDate dateDeCreation;

    private String uap;

    private LocalDate dateEtHeureConnaissanceAt;

    private String prevenuComment;

    private String nomPremierePersonnePrevenu;

    private LocalDate dateEtHeurePrevenu;

    private Boolean isTemoin;

    private String commentaireTemoin;

    private Boolean isTiersEnCause;

    private String commentaireTiersEnCause;

    private Boolean isAutreVictime;

    private String commentaireAutreVictime;

    private Boolean isRapportDePolice;

    private String commentaireRapportDePolice;

    private Boolean isVictimeTransports;

    private String commentaireVictimeTransporter;

    private LocalDate dateEtHeureMomentAccident;

    private String lieuAccident;

    private Boolean isIdentifierDu;

    private String circonstanceAccident;

    private String materielEnCause;

    private String remarques;

    private String pilote;

    private LocalDate dateEtHeureValidationPilote;

    private String porteur;

    private LocalDate dateEtHeureValidationPorteur;

    private String hse;

    private LocalDate dateEtHeureValidationHse;

    private Boolean isIntervention8300;

    private Boolean isInterventionInfirmiere;

    private String commentaireInfirmere;

    private Boolean isInterventionMedecin;

    private String commentaireMedecin;

    private Boolean isInterventionsecouriste;

    private String commentaireSecouriste;

    private Boolean isInterventionsecouristeExterieur;

    private Boolean retourAuPosteDeTravail;

    private String travailLegerPossible;

    private Boolean analyseAChaudInfirmiere;

    private Boolean analyseAChaudCodir;

    private Boolean analyseAChaudHse;

    private Boolean analyseAChaudNplus1;

    private Boolean analyseAChaudCssCt;

    private String analyseAChaudCommentaire;

    private String pourquoi1;

    private String pourquoi2;

    private String pourquoi3;

    private String pourquoi4;

    private String pourquoi5;

    private Boolean bras;

    private Boolean chevilles;

    private Boolean colonne;

    private Boolean cou;

    private Boolean coude;

    private Boolean cos;

    private Boolean epaule;

    private Boolean genou;

    private Boolean jambes;

    private Boolean mains;

    private Boolean oeil;

    private Boolean pieds;

    private Boolean poignet;

    private Boolean tete;

    private Boolean torse;

    private UserDTO user;

    private SiegeLesionsDTO siegeLesions;

    private FicheSuiviSanteDTO ficheSuiviSante;

    private TypeDTO type;

    private CategorieDTO categorie;

    private EquipementDTO equipement;

    private TypeRapportDTO typeRapport;

    private NatureAccidentDTO natureAccident;

    private OrigineLesionsDTO origineLesions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRedacteur() {
        return redacteur;
    }

    public void setRedacteur(String redacteur) {
        this.redacteur = redacteur;
    }

    public LocalDate getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(LocalDate dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public String getUap() {
        return uap;
    }

    public void setUap(String uap) {
        this.uap = uap;
    }

    public LocalDate getDateEtHeureConnaissanceAt() {
        return dateEtHeureConnaissanceAt;
    }

    public void setDateEtHeureConnaissanceAt(LocalDate dateEtHeureConnaissanceAt) {
        this.dateEtHeureConnaissanceAt = dateEtHeureConnaissanceAt;
    }

    public String getPrevenuComment() {
        return prevenuComment;
    }

    public void setPrevenuComment(String prevenuComment) {
        this.prevenuComment = prevenuComment;
    }

    public String getNomPremierePersonnePrevenu() {
        return nomPremierePersonnePrevenu;
    }

    public void setNomPremierePersonnePrevenu(String nomPremierePersonnePrevenu) {
        this.nomPremierePersonnePrevenu = nomPremierePersonnePrevenu;
    }

    public LocalDate getDateEtHeurePrevenu() {
        return dateEtHeurePrevenu;
    }

    public void setDateEtHeurePrevenu(LocalDate dateEtHeurePrevenu) {
        this.dateEtHeurePrevenu = dateEtHeurePrevenu;
    }

    public Boolean getIsTemoin() {
        return isTemoin;
    }

    public void setIsTemoin(Boolean isTemoin) {
        this.isTemoin = isTemoin;
    }

    public String getCommentaireTemoin() {
        return commentaireTemoin;
    }

    public void setCommentaireTemoin(String commentaireTemoin) {
        this.commentaireTemoin = commentaireTemoin;
    }

    public Boolean getIsTiersEnCause() {
        return isTiersEnCause;
    }

    public void setIsTiersEnCause(Boolean isTiersEnCause) {
        this.isTiersEnCause = isTiersEnCause;
    }

    public String getCommentaireTiersEnCause() {
        return commentaireTiersEnCause;
    }

    public void setCommentaireTiersEnCause(String commentaireTiersEnCause) {
        this.commentaireTiersEnCause = commentaireTiersEnCause;
    }

    public Boolean getIsAutreVictime() {
        return isAutreVictime;
    }

    public void setIsAutreVictime(Boolean isAutreVictime) {
        this.isAutreVictime = isAutreVictime;
    }

    public String getCommentaireAutreVictime() {
        return commentaireAutreVictime;
    }

    public void setCommentaireAutreVictime(String commentaireAutreVictime) {
        this.commentaireAutreVictime = commentaireAutreVictime;
    }

    public Boolean getIsRapportDePolice() {
        return isRapportDePolice;
    }

    public void setIsRapportDePolice(Boolean isRapportDePolice) {
        this.isRapportDePolice = isRapportDePolice;
    }

    public String getCommentaireRapportDePolice() {
        return commentaireRapportDePolice;
    }

    public void setCommentaireRapportDePolice(String commentaireRapportDePolice) {
        this.commentaireRapportDePolice = commentaireRapportDePolice;
    }

    public Boolean getIsVictimeTransports() {
        return isVictimeTransports;
    }

    public void setIsVictimeTransports(Boolean isVictimeTransports) {
        this.isVictimeTransports = isVictimeTransports;
    }

    public String getCommentaireVictimeTransporter() {
        return commentaireVictimeTransporter;
    }

    public void setCommentaireVictimeTransporter(String commentaireVictimeTransporter) {
        this.commentaireVictimeTransporter = commentaireVictimeTransporter;
    }

    public LocalDate getDateEtHeureMomentAccident() {
        return dateEtHeureMomentAccident;
    }

    public void setDateEtHeureMomentAccident(LocalDate dateEtHeureMomentAccident) {
        this.dateEtHeureMomentAccident = dateEtHeureMomentAccident;
    }

    public String getLieuAccident() {
        return lieuAccident;
    }

    public void setLieuAccident(String lieuAccident) {
        this.lieuAccident = lieuAccident;
    }

    public Boolean getIsIdentifierDu() {
        return isIdentifierDu;
    }

    public void setIsIdentifierDu(Boolean isIdentifierDu) {
        this.isIdentifierDu = isIdentifierDu;
    }

    public String getCirconstanceAccident() {
        return circonstanceAccident;
    }

    public void setCirconstanceAccident(String circonstanceAccident) {
        this.circonstanceAccident = circonstanceAccident;
    }

    public String getMaterielEnCause() {
        return materielEnCause;
    }

    public void setMaterielEnCause(String materielEnCause) {
        this.materielEnCause = materielEnCause;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }

    public String getPilote() {
        return pilote;
    }

    public void setPilote(String pilote) {
        this.pilote = pilote;
    }

    public LocalDate getDateEtHeureValidationPilote() {
        return dateEtHeureValidationPilote;
    }

    public void setDateEtHeureValidationPilote(LocalDate dateEtHeureValidationPilote) {
        this.dateEtHeureValidationPilote = dateEtHeureValidationPilote;
    }

    public String getPorteur() {
        return porteur;
    }

    public void setPorteur(String porteur) {
        this.porteur = porteur;
    }

    public LocalDate getDateEtHeureValidationPorteur() {
        return dateEtHeureValidationPorteur;
    }

    public void setDateEtHeureValidationPorteur(LocalDate dateEtHeureValidationPorteur) {
        this.dateEtHeureValidationPorteur = dateEtHeureValidationPorteur;
    }

    public String getHse() {
        return hse;
    }

    public void setHse(String hse) {
        this.hse = hse;
    }

    public LocalDate getDateEtHeureValidationHse() {
        return dateEtHeureValidationHse;
    }

    public void setDateEtHeureValidationHse(LocalDate dateEtHeureValidationHse) {
        this.dateEtHeureValidationHse = dateEtHeureValidationHse;
    }

    public Boolean getIsIntervention8300() {
        return isIntervention8300;
    }

    public void setIsIntervention8300(Boolean isIntervention8300) {
        this.isIntervention8300 = isIntervention8300;
    }

    public Boolean getIsInterventionInfirmiere() {
        return isInterventionInfirmiere;
    }

    public void setIsInterventionInfirmiere(Boolean isInterventionInfirmiere) {
        this.isInterventionInfirmiere = isInterventionInfirmiere;
    }

    public String getCommentaireInfirmere() {
        return commentaireInfirmere;
    }

    public void setCommentaireInfirmere(String commentaireInfirmere) {
        this.commentaireInfirmere = commentaireInfirmere;
    }

    public Boolean getIsInterventionMedecin() {
        return isInterventionMedecin;
    }

    public void setIsInterventionMedecin(Boolean isInterventionMedecin) {
        this.isInterventionMedecin = isInterventionMedecin;
    }

    public String getCommentaireMedecin() {
        return commentaireMedecin;
    }

    public void setCommentaireMedecin(String commentaireMedecin) {
        this.commentaireMedecin = commentaireMedecin;
    }

    public Boolean getIsInterventionsecouriste() {
        return isInterventionsecouriste;
    }

    public void setIsInterventionsecouriste(Boolean isInterventionsecouriste) {
        this.isInterventionsecouriste = isInterventionsecouriste;
    }

    public String getCommentaireSecouriste() {
        return commentaireSecouriste;
    }

    public void setCommentaireSecouriste(String commentaireSecouriste) {
        this.commentaireSecouriste = commentaireSecouriste;
    }

    public Boolean getIsInterventionsecouristeExterieur() {
        return isInterventionsecouristeExterieur;
    }

    public void setIsInterventionsecouristeExterieur(Boolean isInterventionsecouristeExterieur) {
        this.isInterventionsecouristeExterieur = isInterventionsecouristeExterieur;
    }

    public Boolean getRetourAuPosteDeTravail() {
        return retourAuPosteDeTravail;
    }

    public void setRetourAuPosteDeTravail(Boolean retourAuPosteDeTravail) {
        this.retourAuPosteDeTravail = retourAuPosteDeTravail;
    }

    public String getTravailLegerPossible() {
        return travailLegerPossible;
    }

    public void setTravailLegerPossible(String travailLegerPossible) {
        this.travailLegerPossible = travailLegerPossible;
    }

    public Boolean getAnalyseAChaudInfirmiere() {
        return analyseAChaudInfirmiere;
    }

    public void setAnalyseAChaudInfirmiere(Boolean analyseAChaudInfirmiere) {
        this.analyseAChaudInfirmiere = analyseAChaudInfirmiere;
    }

    public Boolean getAnalyseAChaudCodir() {
        return analyseAChaudCodir;
    }

    public void setAnalyseAChaudCodir(Boolean analyseAChaudCodir) {
        this.analyseAChaudCodir = analyseAChaudCodir;
    }

    public Boolean getAnalyseAChaudHse() {
        return analyseAChaudHse;
    }

    public void setAnalyseAChaudHse(Boolean analyseAChaudHse) {
        this.analyseAChaudHse = analyseAChaudHse;
    }

    public Boolean getAnalyseAChaudNplus1() {
        return analyseAChaudNplus1;
    }

    public void setAnalyseAChaudNplus1(Boolean analyseAChaudNplus1) {
        this.analyseAChaudNplus1 = analyseAChaudNplus1;
    }

    public Boolean getAnalyseAChaudCssCt() {
        return analyseAChaudCssCt;
    }

    public void setAnalyseAChaudCssCt(Boolean analyseAChaudCssCt) {
        this.analyseAChaudCssCt = analyseAChaudCssCt;
    }

    public String getAnalyseAChaudCommentaire() {
        return analyseAChaudCommentaire;
    }

    public void setAnalyseAChaudCommentaire(String analyseAChaudCommentaire) {
        this.analyseAChaudCommentaire = analyseAChaudCommentaire;
    }

    public String getPourquoi1() {
        return pourquoi1;
    }

    public void setPourquoi1(String pourquoi1) {
        this.pourquoi1 = pourquoi1;
    }

    public String getPourquoi2() {
        return pourquoi2;
    }

    public void setPourquoi2(String pourquoi2) {
        this.pourquoi2 = pourquoi2;
    }

    public String getPourquoi3() {
        return pourquoi3;
    }

    public void setPourquoi3(String pourquoi3) {
        this.pourquoi3 = pourquoi3;
    }

    public String getPourquoi4() {
        return pourquoi4;
    }

    public void setPourquoi4(String pourquoi4) {
        this.pourquoi4 = pourquoi4;
    }

    public String getPourquoi5() {
        return pourquoi5;
    }

    public void setPourquoi5(String pourquoi5) {
        this.pourquoi5 = pourquoi5;
    }

    public Boolean getBras() {
        return bras;
    }

    public void setBras(Boolean bras) {
        this.bras = bras;
    }

    public Boolean getChevilles() {
        return chevilles;
    }

    public void setChevilles(Boolean chevilles) {
        this.chevilles = chevilles;
    }

    public Boolean getColonne() {
        return colonne;
    }

    public void setColonne(Boolean colonne) {
        this.colonne = colonne;
    }

    public Boolean getCou() {
        return cou;
    }

    public void setCou(Boolean cou) {
        this.cou = cou;
    }

    public Boolean getCoude() {
        return coude;
    }

    public void setCoude(Boolean coude) {
        this.coude = coude;
    }

    public Boolean getCos() {
        return cos;
    }

    public void setCos(Boolean cos) {
        this.cos = cos;
    }

    public Boolean getEpaule() {
        return epaule;
    }

    public void setEpaule(Boolean epaule) {
        this.epaule = epaule;
    }

    public Boolean getGenou() {
        return genou;
    }

    public void setGenou(Boolean genou) {
        this.genou = genou;
    }

    public Boolean getJambes() {
        return jambes;
    }

    public void setJambes(Boolean jambes) {
        this.jambes = jambes;
    }

    public Boolean getMains() {
        return mains;
    }

    public void setMains(Boolean mains) {
        this.mains = mains;
    }

    public Boolean getOeil() {
        return oeil;
    }

    public void setOeil(Boolean oeil) {
        this.oeil = oeil;
    }

    public Boolean getPieds() {
        return pieds;
    }

    public void setPieds(Boolean pieds) {
        this.pieds = pieds;
    }

    public Boolean getPoignet() {
        return poignet;
    }

    public void setPoignet(Boolean poignet) {
        this.poignet = poignet;
    }

    public Boolean getTete() {
        return tete;
    }

    public void setTete(Boolean tete) {
        this.tete = tete;
    }

    public Boolean getTorse() {
        return torse;
    }

    public void setTorse(Boolean torse) {
        this.torse = torse;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public SiegeLesionsDTO getSiegeLesions() {
        return siegeLesions;
    }

    public void setSiegeLesions(SiegeLesionsDTO siegeLesions) {
        this.siegeLesions = siegeLesions;
    }

    public FicheSuiviSanteDTO getFicheSuiviSante() {
        return ficheSuiviSante;
    }

    public void setFicheSuiviSante(FicheSuiviSanteDTO ficheSuiviSante) {
        this.ficheSuiviSante = ficheSuiviSante;
    }

    public TypeDTO getType() {
        return type;
    }

    public void setType(TypeDTO type) {
        this.type = type;
    }

    public CategorieDTO getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDTO categorie) {
        this.categorie = categorie;
    }

    public EquipementDTO getEquipement() {
        return equipement;
    }

    public void setEquipement(EquipementDTO equipement) {
        this.equipement = equipement;
    }

    public TypeRapportDTO getTypeRapport() {
        return typeRapport;
    }

    public void setTypeRapport(TypeRapportDTO typeRapport) {
        this.typeRapport = typeRapport;
    }

    public NatureAccidentDTO getNatureAccident() {
        return natureAccident;
    }

    public void setNatureAccident(NatureAccidentDTO natureAccident) {
        this.natureAccident = natureAccident;
    }

    public OrigineLesionsDTO getOrigineLesions() {
        return origineLesions;
    }

    public void setOrigineLesions(OrigineLesionsDTO origineLesions) {
        this.origineLesions = origineLesions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RapportDTO)) {
            return false;
        }

        RapportDTO rapportDTO = (RapportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rapportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RapportDTO{" +
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
            ", user=" + getUser() +
            ", siegeLesions=" + getSiegeLesions() +
            ", ficheSuiviSante=" + getFicheSuiviSante() +
            ", type=" + getType() +
            ", categorie=" + getCategorie() +
            ", equipement=" + getEquipement() +
            ", typeRapport=" + getTypeRapport() +
            ", natureAccident=" + getNatureAccident() +
            ", origineLesions=" + getOrigineLesions() +
            "}";
    }
}
