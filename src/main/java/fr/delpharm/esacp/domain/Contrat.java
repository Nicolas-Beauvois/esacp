package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contrat.
 */
@Entity
@Table(name = "contrat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contrat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contrat")
    private String contrat;

    @OneToMany(mappedBy = "contrat")
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

    public Contrat id(Long id) {
        this.id = id;
        return this;
    }

    public String getContrat() {
        return this.contrat;
    }

    public Contrat contrat(String contrat) {
        this.contrat = contrat;
        return this;
    }

    public void setContrat(String contrat) {
        this.contrat = contrat;
    }

    public Set<UserExtra> getUserExtras() {
        return this.userExtras;
    }

    public Contrat userExtras(Set<UserExtra> userExtras) {
        this.setUserExtras(userExtras);
        return this;
    }

    public Contrat addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.setContrat(this);
        return this;
    }

    public Contrat removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.setContrat(null);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        if (this.userExtras != null) {
            this.userExtras.forEach(i -> i.setContrat(null));
        }
        if (userExtras != null) {
            userExtras.forEach(i -> i.setContrat(this));
        }
        this.userExtras = userExtras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contrat)) {
            return false;
        }
        return id != null && id.equals(((Contrat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contrat{" +
            "id=" + getId() +
            ", contrat='" + getContrat() + "'" +
            "}";
    }
}
