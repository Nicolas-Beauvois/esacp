package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Site.
 */
@Entity
@Table(name = "site")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "site")
    private String site;

    @OneToMany(mappedBy = "site")
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

    public Site id(Long id) {
        this.id = id;
        return this;
    }

    public String getSite() {
        return this.site;
    }

    public Site site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Set<UserExtra> getUserExtras() {
        return this.userExtras;
    }

    public Site userExtras(Set<UserExtra> userExtras) {
        this.setUserExtras(userExtras);
        return this;
    }

    public Site addUserExtra(UserExtra userExtra) {
        this.userExtras.add(userExtra);
        userExtra.setSite(this);
        return this;
    }

    public Site removeUserExtra(UserExtra userExtra) {
        this.userExtras.remove(userExtra);
        userExtra.setSite(null);
        return this;
    }

    public void setUserExtras(Set<UserExtra> userExtras) {
        if (this.userExtras != null) {
            this.userExtras.forEach(i -> i.setSite(null));
        }
        if (userExtras != null) {
            userExtras.forEach(i -> i.setSite(this));
        }
        this.userExtras = userExtras;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Site)) {
            return false;
        }
        return id != null && id.equals(((Site) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Site{" +
            "id=" + getId() +
            ", site='" + getSite() + "'" +
            "}";
    }
}
