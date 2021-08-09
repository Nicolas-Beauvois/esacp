package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Statut.
 */
@Entity
@Table(name = "statut")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Statut implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "statut")
    private String statut;

    @OneToMany(mappedBy = "statut")
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

    public Statut id(Long id) {
        this.id = id;
        return this;
    }

    public String getStatut() {
        return this.statut;
    }

    public Statut statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Set<UserExtra> getUserExtras() {
        return this.userExtras;
    }

    public Statut userExtras(Set<UserExtra> userExtras) {
        this.setUserExtras(userExtras);
        return this;
    }

    public Statut addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.setStatut(this);
        return this;
    }

    public Statut removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.setStatut(null);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        if (this.userExtras != null) {
            this.userExtras.forEach(i -> i.setStatut(null));
        }
        if (userExtras != null) {
            userExtras.forEach(i -> i.setStatut(this));
        }
        this.userExtras = userExtras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Statut)) {
            return false;
        }
        return id != null && id.equals(((Statut) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Statut{" +
            "id=" + getId() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
