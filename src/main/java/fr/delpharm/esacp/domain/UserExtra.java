package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "date_de_naissance")
    private LocalDate dateDeNaissance;

    @Column(name = "is_redacteur")
    private Boolean isRedacteur;

    @Column(name = "is_pilote")
    private Boolean isPilote;

    @Column(name = "is_porteur")
    private Boolean isPorteur;

    @Column(name = "is_codir")
    private Boolean isCodir;

    @Column(name = "is_hse")
    private Boolean isHse;

    @Column(name = "is_validateur_hse")
    private Boolean isValidateurHse;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userExtras" }, allowSetters = true)
    private Statut statut;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userExtras" }, allowSetters = true)
    private Sexe sexe;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userExtras" }, allowSetters = true)
    private Departement departement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userExtras" }, allowSetters = true)
    private Contrat contrat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userExtras" }, allowSetters = true)
    private Site site;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userExtras" }, allowSetters = true)
    private Csp csp;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserExtra id(Long id) {
        this.id = id;
        return this;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public UserExtra matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public LocalDate getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    public UserExtra dateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
        return this;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Boolean getIsRedacteur() {
        return this.isRedacteur;
    }

    public UserExtra isRedacteur(Boolean isRedacteur) {
        this.isRedacteur = isRedacteur;
        return this;
    }

    public void setIsRedacteur(Boolean isRedacteur) {
        this.isRedacteur = isRedacteur;
    }

    public Boolean getIsPilote() {
        return this.isPilote;
    }

    public UserExtra isPilote(Boolean isPilote) {
        this.isPilote = isPilote;
        return this;
    }

    public void setIsPilote(Boolean isPilote) {
        this.isPilote = isPilote;
    }

    public Boolean getIsPorteur() {
        return this.isPorteur;
    }

    public UserExtra isPorteur(Boolean isPorteur) {
        this.isPorteur = isPorteur;
        return this;
    }

    public void setIsPorteur(Boolean isPorteur) {
        this.isPorteur = isPorteur;
    }

    public Boolean getIsCodir() {
        return this.isCodir;
    }

    public UserExtra isCodir(Boolean isCodir) {
        this.isCodir = isCodir;
        return this;
    }

    public void setIsCodir(Boolean isCodir) {
        this.isCodir = isCodir;
    }

    public Boolean getIsHse() {
        return this.isHse;
    }

    public UserExtra isHse(Boolean isHse) {
        this.isHse = isHse;
        return this;
    }

    public void setIsHse(Boolean isHse) {
        this.isHse = isHse;
    }

    public Boolean getIsValidateurHse() {
        return this.isValidateurHse;
    }

    public UserExtra isValidateurHse(Boolean isValidateurHse) {
        this.isValidateurHse = isValidateurHse;
        return this;
    }

    public void setIsValidateurHse(Boolean isValidateurHse) {
        this.isValidateurHse = isValidateurHse;
    }

    public User getUser() {
        return this.user;
    }

    public UserExtra user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Statut getStatut() {
        return this.statut;
    }

    public UserExtra statut(Statut statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public UserExtra sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Departement getDepartement() {
        return this.departement;
    }

    public UserExtra departement(Departement departement) {
        this.setDepartement(departement);
        return this;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Contrat getContrat() {
        return this.contrat;
    }

    public UserExtra contrat(Contrat contrat) {
        this.setContrat(contrat);
        return this;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public Site getSite() {
        return this.site;
    }

    public UserExtra site(Site site) {
        this.setSite(site);
        return this;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Csp getCsp() {
        return this.csp;
    }

    public UserExtra csp(Csp csp) {
        this.setCsp(csp);
        return this;
    }

    public void setCsp(Csp csp) {
        this.csp = csp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtra)) {
            return false;
        }
        return id != null && id.equals(((UserExtra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", isRedacteur='" + getIsRedacteur() + "'" +
            ", isPilote='" + getIsPilote() + "'" +
            ", isPorteur='" + getIsPorteur() + "'" +
            ", isCodir='" + getIsCodir() + "'" +
            ", isHse='" + getIsHse() + "'" +
            ", isValidateurHse='" + getIsValidateurHse() + "'" +
            "}";
    }
}
