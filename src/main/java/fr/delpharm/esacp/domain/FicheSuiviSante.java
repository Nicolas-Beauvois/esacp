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
 * A FicheSuiviSante.
 */
@Entity
@Table(name = "fiche_suivi_sante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FicheSuiviSante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "suivi_individuel")
    private Boolean suiviIndividuel;

    @Column(name = "medecin_du_travail")
    private String medecinDuTravail;

    @Column(name = "date_de_creation")
    private LocalDate dateDeCreation;

    @Column(name = "datededebut_at")
    private LocalDate datededebutAT;

    @Column(name = "datedefin_at")
    private LocalDate datedefinAT;

    @Column(name = "proposition_medecin_du_travail")
    private String propositionMedecinDuTravail;

    @Column(name = "a_revoir_le")
    private LocalDate aRevoirLe;

    @OneToMany(mappedBy = "ficheSuiviSante")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Rapport> rapports = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ficheSuiviSantes" }, allowSetters = true)
    private TypeAt typeAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FicheSuiviSante id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getSuiviIndividuel() {
        return this.suiviIndividuel;
    }

    public FicheSuiviSante suiviIndividuel(Boolean suiviIndividuel) {
        this.suiviIndividuel = suiviIndividuel;
        return this;
    }

    public void setSuiviIndividuel(Boolean suiviIndividuel) {
        this.suiviIndividuel = suiviIndividuel;
    }

    public String getMedecinDuTravail() {
        return this.medecinDuTravail;
    }

    public FicheSuiviSante medecinDuTravail(String medecinDuTravail) {
        this.medecinDuTravail = medecinDuTravail;
        return this;
    }

    public void setMedecinDuTravail(String medecinDuTravail) {
        this.medecinDuTravail = medecinDuTravail;
    }

    public LocalDate getDateDeCreation() {
        return this.dateDeCreation;
    }

    public FicheSuiviSante dateDeCreation(LocalDate dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
        return this;
    }

    public void setDateDeCreation(LocalDate dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public LocalDate getDatededebutAT() {
        return this.datededebutAT;
    }

    public FicheSuiviSante datededebutAT(LocalDate datededebutAT) {
        this.datededebutAT = datededebutAT;
        return this;
    }

    public void setDatededebutAT(LocalDate datededebutAT) {
        this.datededebutAT = datededebutAT;
    }

    public LocalDate getDatedefinAT() {
        return this.datedefinAT;
    }

    public FicheSuiviSante datedefinAT(LocalDate datedefinAT) {
        this.datedefinAT = datedefinAT;
        return this;
    }

    public void setDatedefinAT(LocalDate datedefinAT) {
        this.datedefinAT = datedefinAT;
    }

    public String getPropositionMedecinDuTravail() {
        return this.propositionMedecinDuTravail;
    }

    public FicheSuiviSante propositionMedecinDuTravail(String propositionMedecinDuTravail) {
        this.propositionMedecinDuTravail = propositionMedecinDuTravail;
        return this;
    }

    public void setPropositionMedecinDuTravail(String propositionMedecinDuTravail) {
        this.propositionMedecinDuTravail = propositionMedecinDuTravail;
    }

    public LocalDate getaRevoirLe() {
        return this.aRevoirLe;
    }

    public FicheSuiviSante aRevoirLe(LocalDate aRevoirLe) {
        this.aRevoirLe = aRevoirLe;
        return this;
    }

    public void setaRevoirLe(LocalDate aRevoirLe) {
        this.aRevoirLe = aRevoirLe;
    }

    public Set<Rapport> getRapports() {
        return this.rapports;
    }

    public FicheSuiviSante rapports(Set<Rapport> rapports) {
        this.setRapports(rapports);
        return this;
    }

    public FicheSuiviSante addRapport(Rapport rapport) {
        this.rapports.add(rapport);
        rapport.setFicheSuiviSante(this);
        return this;
    }

    public FicheSuiviSante removeRapport(Rapport rapport) {
        this.rapports.remove(rapport);
        rapport.setFicheSuiviSante(null);
        return this;
    }

    public void setRapports(Set<Rapport> rapports) {
        if (this.rapports != null) {
            this.rapports.forEach(i -> i.setFicheSuiviSante(null));
        }
        if (rapports != null) {
            rapports.forEach(i -> i.setFicheSuiviSante(this));
        }
        this.rapports = rapports;
    }

    public TypeAt getTypeAt() {
        return this.typeAt;
    }

    public FicheSuiviSante typeAt(TypeAt typeAt) {
        this.setTypeAt(typeAt);
        return this;
    }

    public void setTypeAt(TypeAt typeAt) {
        this.typeAt = typeAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FicheSuiviSante)) {
            return false;
        }
        return id != null && id.equals(((FicheSuiviSante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FicheSuiviSante{" +
            "id=" + getId() +
            ", suiviIndividuel='" + getSuiviIndividuel() + "'" +
            ", medecinDuTravail='" + getMedecinDuTravail() + "'" +
            ", dateDeCreation='" + getDateDeCreation() + "'" +
            ", datededebutAT='" + getDatededebutAT() + "'" +
            ", datedefinAT='" + getDatedefinAT() + "'" +
            ", propositionMedecinDuTravail='" + getPropositionMedecinDuTravail() + "'" +
            ", aRevoirLe='" + getaRevoirLe() + "'" +
            "}";
    }
}
