package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TypeRapport.
 */
@Entity
@Table(name = "type_rapport")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeRapport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_rapport")
    private String typeRapport;

    @OneToMany(mappedBy = "typeRapport")
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

    public TypeRapport id(Long id) {
        this.id = id;
        return this;
    }

    public String getTypeRapport() {
        return this.typeRapport;
    }

    public TypeRapport typeRapport(String typeRapport) {
        this.typeRapport = typeRapport;
        return this;
    }

    public void setTypeRapport(String typeRapport) {
        this.typeRapport = typeRapport;
    }

    public Set<Rapport> getRapports() {
        return this.rapports;
    }

    public TypeRapport rapports(Set<Rapport> rapports) {
        this.setRapports(rapports);
        return this;
    }

    public TypeRapport addRapport(Rapport rapport) {
        this.rapports.add(rapport);
        rapport.setTypeRapport(this);
        return this;
    }

    public TypeRapport removeRapport(Rapport rapport) {
        this.rapports.remove(rapport);
        rapport.setTypeRapport(null);
        return this;
    }

    public void setRapports(Set<Rapport> rapports) {
        if (this.rapports != null) {
            this.rapports.forEach(i -> i.setTypeRapport(null));
        }
        if (rapports != null) {
            rapports.forEach(i -> i.setTypeRapport(this));
        }
        this.rapports = rapports;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeRapport)) {
            return false;
        }
        return id != null && id.equals(((TypeRapport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeRapport{" +
            "id=" + getId() +
            ", typeRapport='" + getTypeRapport() + "'" +
            "}";
    }
}
