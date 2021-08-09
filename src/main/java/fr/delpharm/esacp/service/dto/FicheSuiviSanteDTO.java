package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.FicheSuiviSante} entity.
 */
public class FicheSuiviSanteDTO implements Serializable {

    private Long id;

    private Boolean suiviIndividuel;

    private String medecinDuTravail;

    private LocalDate dateDeCreation;

    private LocalDate datededebutAT;

    private LocalDate datedefinAT;

    private String propositionMedecinDuTravail;

    private LocalDate aRevoirLe;

    private TypeAtDTO typeAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSuiviIndividuel() {
        return suiviIndividuel;
    }

    public void setSuiviIndividuel(Boolean suiviIndividuel) {
        this.suiviIndividuel = suiviIndividuel;
    }

    public String getMedecinDuTravail() {
        return medecinDuTravail;
    }

    public void setMedecinDuTravail(String medecinDuTravail) {
        this.medecinDuTravail = medecinDuTravail;
    }

    public LocalDate getDateDeCreation() {
        return dateDeCreation;
    }

    public void setDateDeCreation(LocalDate dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }

    public LocalDate getDatededebutAT() {
        return datededebutAT;
    }

    public void setDatededebutAT(LocalDate datededebutAT) {
        this.datededebutAT = datededebutAT;
    }

    public LocalDate getDatedefinAT() {
        return datedefinAT;
    }

    public void setDatedefinAT(LocalDate datedefinAT) {
        this.datedefinAT = datedefinAT;
    }

    public String getPropositionMedecinDuTravail() {
        return propositionMedecinDuTravail;
    }

    public void setPropositionMedecinDuTravail(String propositionMedecinDuTravail) {
        this.propositionMedecinDuTravail = propositionMedecinDuTravail;
    }

    public LocalDate getaRevoirLe() {
        return aRevoirLe;
    }

    public void setaRevoirLe(LocalDate aRevoirLe) {
        this.aRevoirLe = aRevoirLe;
    }

    public TypeAtDTO getTypeAt() {
        return typeAt;
    }

    public void setTypeAt(TypeAtDTO typeAt) {
        this.typeAt = typeAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FicheSuiviSanteDTO)) {
            return false;
        }

        FicheSuiviSanteDTO ficheSuiviSanteDTO = (FicheSuiviSanteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ficheSuiviSanteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FicheSuiviSanteDTO{" +
            "id=" + getId() +
            ", suiviIndividuel='" + getSuiviIndividuel() + "'" +
            ", medecinDuTravail='" + getMedecinDuTravail() + "'" +
            ", dateDeCreation='" + getDateDeCreation() + "'" +
            ", datededebutAT='" + getDatededebutAT() + "'" +
            ", datedefinAT='" + getDatedefinAT() + "'" +
            ", propositionMedecinDuTravail='" + getPropositionMedecinDuTravail() + "'" +
            ", aRevoirLe='" + getaRevoirLe() + "'" +
            ", typeAt=" + getTypeAt() +
            "}";
    }
}
