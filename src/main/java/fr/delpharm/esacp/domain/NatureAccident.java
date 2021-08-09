package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NatureAccident.
 */
@Entity
@Table(name = "nature_accident")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NatureAccident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_nature_accident")
    private String typeNatureAccident;

    @OneToMany(mappedBy = "natureAccident")
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

    public NatureAccident id(Long id) {
        this.id = id;
        return this;
    }

    public String getTypeNatureAccident() {
        return this.typeNatureAccident;
    }

    public NatureAccident typeNatureAccident(String typeNatureAccident) {
        this.typeNatureAccident = typeNatureAccident;
        return this;
    }

    public void setTypeNatureAccident(String typeNatureAccident) {
        this.typeNatureAccident = typeNatureAccident;
    }

    public Set<Rapport> getRapports() {
        return this.rapports;
    }

    public NatureAccident rapports(Set<Rapport> rapports) {
        this.setRapports(rapports);
        return this;
    }

    public NatureAccident addRapport(Rapport rapport) {
        this.rapports.add(rapport);
        rapport.setNatureAccident(this);
        return this;
    }

    public NatureAccident removeRapport(Rapport rapport) {
        this.rapports.remove(rapport);
        rapport.setNatureAccident(null);
        return this;
    }

    public void setRapports(Set<Rapport> rapports) {
        if (this.rapports != null) {
            this.rapports.forEach(i -> i.setNatureAccident(null));
        }
        if (rapports != null) {
            rapports.forEach(i -> i.setNatureAccident(this));
        }
        this.rapports = rapports;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NatureAccident)) {
            return false;
        }
        return id != null && id.equals(((NatureAccident) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NatureAccident{" +
            "id=" + getId() +
            ", typeNatureAccident='" + getTypeNatureAccident() + "'" +
            "}";
    }
}
