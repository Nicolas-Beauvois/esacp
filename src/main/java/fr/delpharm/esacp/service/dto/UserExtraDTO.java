package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.UserExtra} entity.
 */
public class UserExtraDTO implements Serializable {

    private Long id;

    private String matricule;

    private LocalDate dateDeNaissance;

    private Boolean isRedacteur;

    private Boolean isPilote;

    private Boolean isPorteur;

    private Boolean isCodir;

    private Boolean isHse;

    private Boolean isValidateurHse;

    private UserDTO user;

    private StatutDTO statut;

    private SexeDTO sexe;

    private DepartementDTO departement;

    private ContratDTO contrat;

    private SiteDTO site;

    private CspDTO csp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Boolean getIsRedacteur() {
        return isRedacteur;
    }

    public void setIsRedacteur(Boolean isRedacteur) {
        this.isRedacteur = isRedacteur;
    }

    public Boolean getIsPilote() {
        return isPilote;
    }

    public void setIsPilote(Boolean isPilote) {
        this.isPilote = isPilote;
    }

    public Boolean getIsPorteur() {
        return isPorteur;
    }

    public void setIsPorteur(Boolean isPorteur) {
        this.isPorteur = isPorteur;
    }

    public Boolean getIsCodir() {
        return isCodir;
    }

    public void setIsCodir(Boolean isCodir) {
        this.isCodir = isCodir;
    }

    public Boolean getIsHse() {
        return isHse;
    }

    public void setIsHse(Boolean isHse) {
        this.isHse = isHse;
    }

    public Boolean getIsValidateurHse() {
        return isValidateurHse;
    }

    public void setIsValidateurHse(Boolean isValidateurHse) {
        this.isValidateurHse = isValidateurHse;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public StatutDTO getStatut() {
        return statut;
    }

    public void setStatut(StatutDTO statut) {
        this.statut = statut;
    }

    public SexeDTO getSexe() {
        return sexe;
    }

    public void setSexe(SexeDTO sexe) {
        this.sexe = sexe;
    }

    public DepartementDTO getDepartement() {
        return departement;
    }

    public void setDepartement(DepartementDTO departement) {
        this.departement = departement;
    }

    public ContratDTO getContrat() {
        return contrat;
    }

    public void setContrat(ContratDTO contrat) {
        this.contrat = contrat;
    }

    public SiteDTO getSite() {
        return site;
    }

    public void setSite(SiteDTO site) {
        this.site = site;
    }

    public CspDTO getCsp() {
        return csp;
    }

    public void setCsp(CspDTO csp) {
        this.csp = csp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtraDTO)) {
            return false;
        }

        UserExtraDTO userExtraDTO = (UserExtraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userExtraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtraDTO{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", isRedacteur='" + getIsRedacteur() + "'" +
            ", isPilote='" + getIsPilote() + "'" +
            ", isPorteur='" + getIsPorteur() + "'" +
            ", isCodir='" + getIsCodir() + "'" +
            ", isHse='" + getIsHse() + "'" +
            ", isValidateurHse='" + getIsValidateurHse() + "'" +
            ", user=" + getUser() +
            ", statut=" + getStatut() +
            ", sexe=" + getSexe() +
            ", departement=" + getDepartement() +
            ", contrat=" + getContrat() +
            ", site=" + getSite() +
            ", csp=" + getCsp() +
            "}";
    }
}
