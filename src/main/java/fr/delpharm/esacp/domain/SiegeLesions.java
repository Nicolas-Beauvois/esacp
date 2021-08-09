package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SiegeLesions.
 */
@Entity
@Table(name = "siege_lesions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiegeLesions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_siege_de_lesions")
    private String typeSiegeDeLesions;

    @OneToMany(mappedBy = "siegeLesions")
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

    public SiegeLesions id(Long id) {
        this.id = id;
        return this;
    }

    public String getTypeSiegeDeLesions() {
        return this.typeSiegeDeLesions;
    }

    public SiegeLesions typeSiegeDeLesions(String typeSiegeDeLesions) {
        this.typeSiegeDeLesions = typeSiegeDeLesions;
        return this;
    }

    public void setTypeSiegeDeLesions(String typeSiegeDeLesions) {
        this.typeSiegeDeLesions = typeSiegeDeLesions;
    }

    public Set<Rapport> getRapports() {
        return this.rapports;
    }

    public SiegeLesions rapports(Set<Rapport> rapports) {
        this.setRapports(rapports);
        return this;
    }

    public SiegeLesions addRapport(Rapport rapport) {
        this.rapports.add(rapport);
        rapport.setSiegeLesions(this);
        return this;
    }

    public SiegeLesions removeRapport(Rapport rapport) {
        this.rapports.remove(rapport);
        rapport.setSiegeLesions(null);
        return this;
    }

    public void setRapports(Set<Rapport> rapports) {
        if (this.rapports != null) {
            this.rapports.forEach(i -> i.setSiegeLesions(null));
        }
        if (rapports != null) {
            rapports.forEach(i -> i.setSiegeLesions(this));
        }
        this.rapports = rapports;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiegeLesions)) {
            return false;
        }
        return id != null && id.equals(((SiegeLesions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiegeLesions{" +
            "id=" + getId() +
            ", typeSiegeDeLesions='" + getTypeSiegeDeLesions() + "'" +
            "}";
    }
}
