package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Actions} entity.
 */
public class ActionsDTO implements Serializable {

    private Long id;

    private String redacteur;

    private Boolean isActionImmediate;

    private LocalDate dateEtHeureCreation;

    private String titre;

    private String descriptionAction;

    private String descriptionReponse;

    private LocalDate delai;

    private String etat;

    private String pilote;

    private LocalDate dateEtHeureValidationPilote;

    private String porteur;

    private LocalDate dateEtHeureValidationPorteur;

    private String hse;

    private LocalDate dateEtHeureValidationHse;

    private RapportDTO rapport;

    private CorrectPreventDTO correctPrevent;

    private PrioriteDTO priorite;

    private EtapeValidationDTO etapeValidation;

    private TypeActionDTO typeAction;

    private CriticiteDTO criticite;

    private PieceJointesDTO pj;

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

    public Boolean getIsActionImmediate() {
        return isActionImmediate;
    }

    public void setIsActionImmediate(Boolean isActionImmediate) {
        this.isActionImmediate = isActionImmediate;
    }

    public LocalDate getDateEtHeureCreation() {
        return dateEtHeureCreation;
    }

    public void setDateEtHeureCreation(LocalDate dateEtHeureCreation) {
        this.dateEtHeureCreation = dateEtHeureCreation;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescriptionAction() {
        return descriptionAction;
    }

    public void setDescriptionAction(String descriptionAction) {
        this.descriptionAction = descriptionAction;
    }

    public String getDescriptionReponse() {
        return descriptionReponse;
    }

    public void setDescriptionReponse(String descriptionReponse) {
        this.descriptionReponse = descriptionReponse;
    }

    public LocalDate getDelai() {
        return delai;
    }

    public void setDelai(LocalDate delai) {
        this.delai = delai;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
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

    public RapportDTO getRapport() {
        return rapport;
    }

    public void setRapport(RapportDTO rapport) {
        this.rapport = rapport;
    }

    public CorrectPreventDTO getCorrectPrevent() {
        return correctPrevent;
    }

    public void setCorrectPrevent(CorrectPreventDTO correctPrevent) {
        this.correctPrevent = correctPrevent;
    }

    public PrioriteDTO getPriorite() {
        return priorite;
    }

    public void setPriorite(PrioriteDTO priorite) {
        this.priorite = priorite;
    }

    public EtapeValidationDTO getEtapeValidation() {
        return etapeValidation;
    }

    public void setEtapeValidation(EtapeValidationDTO etapeValidation) {
        this.etapeValidation = etapeValidation;
    }

    public TypeActionDTO getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(TypeActionDTO typeAction) {
        this.typeAction = typeAction;
    }

    public CriticiteDTO getCriticite() {
        return criticite;
    }

    public void setCriticite(CriticiteDTO criticite) {
        this.criticite = criticite;
    }

    public PieceJointesDTO getPj() {
        return pj;
    }

    public void setPj(PieceJointesDTO pj) {
        this.pj = pj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionsDTO)) {
            return false;
        }

        ActionsDTO actionsDTO = (ActionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, actionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActionsDTO{" +
            "id=" + getId() +
            ", redacteur='" + getRedacteur() + "'" +
            ", isActionImmediate='" + getIsActionImmediate() + "'" +
            ", dateEtHeureCreation='" + getDateEtHeureCreation() + "'" +
            ", titre='" + getTitre() + "'" +
            ", descriptionAction='" + getDescriptionAction() + "'" +
            ", descriptionReponse='" + getDescriptionReponse() + "'" +
            ", delai='" + getDelai() + "'" +
            ", etat='" + getEtat() + "'" +
            ", pilote='" + getPilote() + "'" +
            ", dateEtHeureValidationPilote='" + getDateEtHeureValidationPilote() + "'" +
            ", porteur='" + getPorteur() + "'" +
            ", dateEtHeureValidationPorteur='" + getDateEtHeureValidationPorteur() + "'" +
            ", hse='" + getHse() + "'" +
            ", dateEtHeureValidationHse='" + getDateEtHeureValidationHse() + "'" +
            ", rapport=" + getRapport() +
            ", correctPrevent=" + getCorrectPrevent() +
            ", priorite=" + getPriorite() +
            ", etapeValidation=" + getEtapeValidation() +
            ", typeAction=" + getTypeAction() +
            ", criticite=" + getCriticite() +
            ", pj=" + getPj() +
            "}";
    }
}
