package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TypeAt.
 */
@Entity
@Table(name = "type_at")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeAt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_at")
    private String typeAt;

    @OneToMany(mappedBy = "typeAt")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rapports", "typeAt" }, allowSetters = true)
    private Set<FicheSuiviSante> ficheSuiviSantes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeAt id(Long id) {
        this.id = id;
        return this;
    }

    public String getTypeAt() {
        return this.typeAt;
    }

    public TypeAt typeAt(String typeAt) {
        this.typeAt = typeAt;
        return this;
    }

    public void setTypeAt(String typeAt) {
        this.typeAt = typeAt;
    }

    public Set<FicheSuiviSante> getFicheSuiviSantes() {
        return this.ficheSuiviSantes;
    }

    public TypeAt ficheSuiviSantes(Set<FicheSuiviSante> ficheSuiviSantes) {
        this.setFicheSuiviSantes(ficheSuiviSantes);
        return this;
    }

    public TypeAt addFicheSuiviSante(FicheSuiviSante ficheSuiviSante) {
        this.ficheSuiviSantes.add(ficheSuiviSante);
        ficheSuiviSante.setTypeAt(this);
        return this;
    }

    public TypeAt removeFicheSuiviSante(FicheSuiviSante ficheSuiviSante) {
        this.ficheSuiviSantes.remove(ficheSuiviSante);
        ficheSuiviSante.setTypeAt(null);
        return this;
    }

    public void setFicheSuiviSantes(Set<FicheSuiviSante> ficheSuiviSantes) {
        if (this.ficheSuiviSantes != null) {
            this.ficheSuiviSantes.forEach(i -> i.setTypeAt(null));
        }
        if (ficheSuiviSantes != null) {
            ficheSuiviSantes.forEach(i -> i.setTypeAt(this));
        }
        this.ficheSuiviSantes = ficheSuiviSantes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeAt)) {
            return false;
        }
        return id != null && id.equals(((TypeAt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeAt{" +
            "id=" + getId() +
            ", typeAt='" + getTypeAt() + "'" +
            "}";
    }
}
