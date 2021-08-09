package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Departement.
 */
@Entity
@Table(name = "departement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Departement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departement")
    private String departement;

    @Column(name = "type_service")
    private String typeService;

    @OneToMany(mappedBy = "departement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "statut", "sexe", "departement", "contrat", "site", "csp" }, allowSetters = true)
    private Set<UserExtra> userExtras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departement id(Long id) {
        this.id = id;
        return this;
    }

    public String getDepartement() {
        return this.departement;
    }

    public Departement departement(String departement) {
        this.departement = departement;
        return this;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getTypeService() {
        return this.typeService;
    }

    public Departement typeService(String typeService) {
        this.typeService = typeService;
        return this;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public Set<UserExtra> getUserExtras() {
        return this.userExtras;
    }

    public Departement userExtras(Set<UserExtra> userExtras) {
        this.setUserExtras(userExtras);
        return this;
    }

    public Departement addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.setDepartement(this);
        return this;
    }

    public Departement removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.setDepartement(null);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        if (this.userExtras != null) {
            this.userExtras.forEach(i -> i.setDepartement(null));
        }
        if (userExtras != null) {
            userExtras.forEach(i -> i.setDepartement(this));
        }
        this.userExtras = userExtras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departement)) {
            return false;
        }
        return id != null && id.equals(((Departement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departement{" +
            "id=" + getId() +
            ", departement='" + getDepartement() + "'" +
            ", typeService='" + getTypeService() + "'" +
            "}";
    }
}
