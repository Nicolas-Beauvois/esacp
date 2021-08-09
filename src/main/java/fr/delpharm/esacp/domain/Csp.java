package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Csp.
 */
@Entity
@Table(name = "csp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Csp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "csp")
    private String csp;

    @OneToMany(mappedBy = "csp")
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

    public Csp id(Long id) {
        this.id = id;
        return this;
    }

    public String getCsp() {
        return this.csp;
    }

    public Csp csp(String csp) {
        this.csp = csp;
        return this;
    }

    public void setCsp(String csp) {
        this.csp = csp;
    }

    public Set<UserExtra> getUserExtras() {
        return this.userExtras;
    }

    public Csp userExtras(Set<UserExtra> userExtras) {
        this.setUserExtras(userExtras);
        return this;
    }

    public Csp addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.setCsp(this);
        return this;
    }

    public Csp removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.setCsp(null);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        if (this.userExtras != null) {
            this.userExtras.forEach(i -> i.setCsp(null));
        }
        if (userExtras != null) {
            userExtras.forEach(i -> i.setCsp(this));
        }
        this.userExtras = userExtras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Csp)) {
            return false;
        }
        return id != null && id.equals(((Csp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Csp{" +
            "id=" + getId() +
            ", csp='" + getCsp() + "'" +
            "}";
    }
}
