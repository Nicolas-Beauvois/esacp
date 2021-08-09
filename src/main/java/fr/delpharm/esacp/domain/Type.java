package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Type.
 */
@Entity
@Table(name = "type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origine")
    private String origine;

    @Column(name = "acc_origine")
    private String accOrigine;

    @OneToMany(mappedBy = "type")
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "types" }, allowSetters = true)
    private Repartition repartition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type id(Long id) {
        this.id = id;
        return this;
    }

    public String getOrigine() {
        return this.origine;
    }

    public Type origine(String origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getAccOrigine() {
        return this.accOrigine;
    }

    public Type accOrigine(String accOrigine) {
        this.accOrigine = accOrigine;
        return this;
    }

    public void setAccOrigine(String accOrigine) {
        this.accOrigine = accOrigine;
    }

    public Set<Rapport> getRapports() {
        return this.rapports;
    }

    public Type rapports(Set<Rapport> rapports) {
        this.setRapports(rapports);
        return this;
    }

    public Type addRapport(Rapport rapport) {
        this.rapports.add(rapport);
        rapport.setType(this);
        return this;
    }

    public Type removeRapport(Rapport rapport) {
        this.rapports.remove(rapport);
        rapport.setType(null);
        return this;
    }

    public void setRapports(Set<Rapport> rapports) {
        if (this.rapports != null) {
            this.rapports.forEach(i -> i.setType(null));
        }
        if (rapports != null) {
            rapports.forEach(i -> i.setType(this));
        }
        this.rapports = rapports;
    }

    public Repartition getRepartition() {
        return this.repartition;
    }

    public Type repartition(Repartition repartition) {
        this.setRepartition(repartition);
        return this;
    }

    public void setRepartition(Repartition repartition) {
        this.repartition = repartition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Type)) {
            return false;
        }
        return id != null && id.equals(((Type) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Type{" +
            "id=" + getId() +
            ", origine='" + getOrigine() + "'" +
            ", accOrigine='" + getAccOrigine() + "'" +
            "}";
    }
}
