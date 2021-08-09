package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Repartition.
 */
@Entity
@Table(name = "repartition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Repartition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repartition")
    private String repartition;

    @OneToMany(mappedBy = "repartition")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "rapports", "repartition" }, allowSetters = true)
    private Set<Type> types = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Repartition id(Long id) {
        this.id = id;
        return this;
    }

    public String getRepartition() {
        return this.repartition;
    }

    public Repartition repartition(String repartition) {
        this.repartition = repartition;
        return this;
    }

    public void setRepartition(String repartition) {
        this.repartition = repartition;
    }

    public Set<Type> getTypes() {
        return this.types;
    }

    public Repartition types(Set<Type> types) {
        this.setTypes(types);
        return this;
    }

    public Repartition addType(Type type) {
        this.types.add(type);
        type.setRepartition(this);
        return this;
    }

    public Repartition removeType(Type type) {
        this.types.remove(type);
        type.setRepartition(null);
        return this;
    }

    public void setTypes(Set<Type> types) {
        if (this.types != null) {
            this.types.forEach(i -> i.setRepartition(null));
        }
        if (types != null) {
            types.forEach(i -> i.setRepartition(this));
        }
        this.types = types;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Repartition)) {
            return false;
        }
        return id != null && id.equals(((Repartition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Repartition{" +
            "id=" + getId() +
            ", repartition='" + getRepartition() + "'" +
            "}";
    }
}
