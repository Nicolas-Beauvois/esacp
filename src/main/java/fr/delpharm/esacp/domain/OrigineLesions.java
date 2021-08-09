package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OrigineLesions.
 */
@Entity
@Table(name = "origine_lesions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrigineLesions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origine_lesions")
    private String origineLesions;

    @OneToMany(mappedBy = "origineLesions")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrigineLesions id(Long id) {
        this.id = id;
        return this;
    }

    public String getOrigineLesions() {
        return this.origineLesions;
    }

    public OrigineLesions origineLesions(String origineLesions) {
        this.origineLesions = origineLesions;
        return this;
    }

    public void setOrigineLesions(String origineLesions) {
        this.origineLesions = origineLesions;
    }

    public Set<Rapport> getRapports() {
        return this.rapports;
    }

    public OrigineLesions rapports(Set<Rapport> rapports) {
        this.setRapports(rapports);
        return this;
    }

    public OrigineLesions addRapport(Rapport rapport) {
        this.rapports.add(rapport);
        rapport.setOrigineLesions(this);
        return this;
    }

    public OrigineLesions removeRapport(Rapport rapport) {
        this.rapports.remove(rapport);
        rapport.setOrigineLesions(null);
        return this;
    }

    public void setRapports(Set<Rapport> rapports) {
        if (this.rapports != null) {
            this.rapports.forEach(i -> i.setOrigineLesions(null));
        }
        if (rapports != null) {
            rapports.forEach(i -> i.setOrigineLesions(this));
        }
        this.rapports = rapports;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrigineLesions)) {
            return false;
        }
        return id != null && id.equals(((OrigineLesions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrigineLesions{" +
            "id=" + getId() +
            ", origineLesions='" + getOrigineLesions() + "'" +
            "}";
    }
}
