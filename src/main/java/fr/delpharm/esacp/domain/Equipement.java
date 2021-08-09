package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Equipement.
 */
@Entity
@Table(name = "equipement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Equipement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "equipement")
    private String equipement;

    @OneToMany(mappedBy = "equipement")
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

    public Equipement id(Long id) {
        this.id = id;
        return this;
    }

    public String getEquipement() {
        return this.equipement;
    }

    public Equipement equipement(String equipement) {
        this.equipement = equipement;
        return this;
    }

    public void setEquipement(String equipement) {
        this.equipement = equipement;
    }

    public Set<Rapport> getRapports() {
        return this.rapports;
    }

    public Equipement rapports(Set<Rapport> rapports) {
        this.setRapports(rapports);
        return this;
    }

    public Equipement addRapport(Rapport rapport) {
        this.rapports.add(rapport);
        rapport.setEquipement(this);
        return this;
    }

    public Equipement removeRapport(Rapport rapport) {
        this.rapports.remove(rapport);
        rapport.setEquipement(null);
        return this;
    }

    public void setRapports(Set<Rapport> rapports) {
        if (this.rapports != null) {
            this.rapports.forEach(i -> i.setEquipement(null));
        }
        if (rapports != null) {
            rapports.forEach(i -> i.setEquipement(this));
        }
        this.rapports = rapports;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipement)) {
            return false;
        }
        return id != null && id.equals(((Equipement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Equipement{" +
            "id=" + getId() +
            ", equipement='" + getEquipement() + "'" +
            "}";
    }
}
