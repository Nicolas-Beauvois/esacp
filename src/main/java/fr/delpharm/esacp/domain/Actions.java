package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Actions.
 */
@Entity
@Table(name = "actions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Actions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "redacteur")
    private String redacteur;

    @Column(name = "is_action_immediate")
    private Boolean isActionImmediate;

    @Column(name = "date_et_heure_creation")
    private LocalDate dateEtHeureCreation;

    @Column(name = "titre")
    private String titre;

    @Column(name = "description_action")
    private String descriptionAction;

    @Column(name = "description_reponse")
    private String descriptionReponse;

    @Column(name = "delai")
    private LocalDate delai;

    @Column(name = "etat")
    private String etat;

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

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "actions",
            "user",
            "siegeLesions",
            "ficheSuiviSante",
            "type",
            "categorie",
            "equipement",
            "typeRapport",
            "natureAccident",
            "origineLesions",
        },
        allowSetters = true
    )
    private Rapport rapport;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actions" }, allowSetters = true)
    private CorrectPrevent correctPrevent;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actions" }, allowSetters = true)
    private Priorite priorite;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actions" }, allowSetters = true)
    private EtapeValidation etapeValidation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actions" }, allowSetters = true)
    private TypeAction typeAction;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actions" }, allowSetters = true)
    private Criticite criticite;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actions" }, allowSetters = true)
    private PieceJointes pj;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Actions id(Long id) {
        this.id = id;
        return this;
    }

    public String getRedacteur() {
        return this.redacteur;
    }

    public Actions redacteur(String redacteur) {
        this.redacteur = redacteur;
        return this;
    }

    public void setRedacteur(String redacteur) {
        this.redacteur = redacteur;
    }

    public Boolean getIsActionImmediate() {
        return this.isActionImmediate;
    }

    public Actions isActionImmediate(Boolean isActionImmediate) {
        this.isActionImmediate = isActionImmediate;
        return this;
    }

    public void setIsActionImmediate(Boolean isActionImmediate) {
        this.isActionImmediate = isActionImmediate;
    }

    public LocalDate getDateEtHeureCreation() {
        return this.dateEtHeureCreation;
    }

    public Actions dateEtHeureCreation(LocalDate dateEtHeureCreation) {
        this.dateEtHeureCreation = dateEtHeureCreation;
        return this;
    }

    public void setDateEtHeureCreation(LocalDate dateEtHeureCreation) {
        this.dateEtHeureCreation = dateEtHeureCreation;
    }

    public String getTitre() {
        return this.titre;
    }

    public Actions titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescriptionAction() {
        return this.descriptionAction;
    }

    public Actions descriptionAction(String descriptionAction) {
        this.descriptionAction = descriptionAction;
        return this;
    }

    public void setDescriptionAction(String descriptionAction) {
        this.descriptionAction = descriptionAction;
    }

    public String getDescriptionReponse() {
        return this.descriptionReponse;
    }

    public Actions descriptionReponse(String descriptionReponse) {
        this.descriptionReponse = descriptionReponse;
        return this;
    }

    public void setDescriptionReponse(String descriptionReponse) {
        this.descriptionReponse = descriptionReponse;
    }

    public LocalDate getDelai() {
        return this.delai;
    }

    public Actions delai(LocalDate delai) {
        this.delai = delai;
        return this;
    }

    public void setDelai(LocalDate delai) {
        this.delai = delai;
    }

    public String getEtat() {
        return this.etat;
    }

    public Actions etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getPilote() {
        return this.pilote;
    }

    public Actions pilote(String pilote) {
        this.pilote = pilote;
        return this;
    }

    public void setPilote(String pilote) {
        this.pilote = pilote;
    }

    public LocalDate getDateEtHeureValidationPilote() {
        return this.dateEtHeureValidationPilote;
    }

    public Actions dateEtHeureValidationPilote(LocalDate dateEtHeureValidationPilote) {
        this.dateEtHeureValidationPilote = dateEtHeureValidationPilote;
        return this;
    }

    public void setDateEtHeureValidationPilote(LocalDate dateEtHeureValidationPilote) {
        this.dateEtHeureValidationPilote = dateEtHeureValidationPilote;
    }

    public String getPorteur() {
        return this.porteur;
    }

    public Actions porteur(String porteur) {
        this.porteur = porteur;
        return this;
    }

    public void setPorteur(String porteur) {
        this.porteur = porteur;
    }

    public LocalDate getDateEtHeureValidationPorteur() {
        return this.dateEtHeureValidationPorteur;
    }

    public Actions dateEtHeureValidationPorteur(LocalDate dateEtHeureValidationPorteur) {
        this.dateEtHeureValidationPorteur = dateEtHeureValidationPorteur;
        return this;
    }

    public void setDateEtHeureValidationPorteur(LocalDate dateEtHeureValidationPorteur) {
        this.dateEtHeureValidationPorteur = dateEtHeureValidationPorteur;
    }

    public String getHse() {
        return this.hse;
    }

    public Actions hse(String hse) {
        this.hse = hse;
        return this;
    }

    public void setHse(String hse) {
        this.hse = hse;
    }

    public LocalDate getDateEtHeureValidationHse() {
        return this.dateEtHeureValidationHse;
    }

    public Actions dateEtHeureValidationHse(LocalDate dateEtHeureValidationHse) {
        this.dateEtHeureValidationHse = dateEtHeureValidationHse;
        return this;
    }

    public void setDateEtHeureValidationHse(LocalDate dateEtHeureValidationHse) {
        this.dateEtHeureValidationHse = dateEtHeureValidationHse;
    }

    public Rapport getRapport() {
        return this.rapport;
    }

    public Actions rapport(Rapport rapport) {
        this.setRapport(rapport);
        return this;
    }

    public void setRapport(Rapport rapport) {
        this.rapport = rapport;
    }

    public CorrectPrevent getCorrectPrevent() {
        return this.correctPrevent;
    }

    public Actions correctPrevent(CorrectPrevent correctPrevent) {
        this.setCorrectPrevent(correctPrevent);
        return this;
    }

    public void setCorrectPrevent(CorrectPrevent correctPrevent) {
        this.correctPrevent = correctPrevent;
    }

    public Priorite getPriorite() {
        return this.priorite;
    }

    public Actions priorite(Priorite priorite) {
        this.setPriorite(priorite);
        return this;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public EtapeValidation getEtapeValidation() {
        return this.etapeValidation;
    }

    public Actions etapeValidation(EtapeValidation etapeValidation) {
        this.setEtapeValidation(etapeValidation);
        return this;
    }

    public void setEtapeValidation(EtapeValidation etapeValidation) {
        this.etapeValidation = etapeValidation;
    }

    public TypeAction getTypeAction() {
        return this.typeAction;
    }

    public Actions typeAction(TypeAction typeAction) {
        this.setTypeAction(typeAction);
        return this;
    }

    public void setTypeAction(TypeAction typeAction) {
        this.typeAction = typeAction;
    }

    public Criticite getCriticite() {
        return this.criticite;
    }

    public Actions criticite(Criticite criticite) {
        this.setCriticite(criticite);
        return this;
    }

    public void setCriticite(Criticite criticite) {
        this.criticite = criticite;
    }

    public PieceJointes getPj() {
        return this.pj;
    }

    public Actions pj(PieceJointes pieceJointes) {
        this.setPj(pieceJointes);
        return this;
    }

    public void setPj(PieceJointes pieceJointes) {
        this.pj = pieceJointes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Actions)) {
            return false;
        }
        return id != null && id.equals(((Actions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Actions{" +
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
            "}";
    }
}
