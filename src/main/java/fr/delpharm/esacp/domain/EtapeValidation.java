package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EtapeValidation.
 */
@Entity
@Table(name = "etape_validation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EtapeValidation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "etape_validation")
    private String etapeValidation;

    @OneToMany(mappedBy = "etapeValidation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "rapport", "correctPrevent", "priorite", "etapeValidation", "typeAction", "criticite", "pj" },
        allowSetters = true
    )
    private Set<Actions> actions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtapeValidation id(Long id) {
        this.id = id;
        return this;
    }

    public String getEtapeValidation() {
        return this.etapeValidation;
    }

    public EtapeValidation etapeValidation(String etapeValidation) {
        this.etapeValidation = etapeValidation;
        return this;
    }

    public void setEtapeValidation(String etapeValidation) {
        this.etapeValidation = etapeValidation;
    }

    public Set<Actions> getActions() {
        return this.actions;
    }

    public EtapeValidation actions(Set<Actions> actions) {
        this.setActions(actions);
        return this;
    }

    public EtapeValidation addActions(Actions actions) {
        this.actions.add(actions);
        actions.setEtapeValidation(this);
        return this;
    }

    public EtapeValidation removeActions(Actions actions) {
        this.actions.remove(actions);
        actions.setEtapeValidation(null);
        return this;
    }

    public void setActions(Set<Actions> actions) {
        if (this.actions != null) {
            this.actions.forEach(i -> i.setEtapeValidation(null));
        }
        if (actions != null) {
            actions.forEach(i -> i.setEtapeValidation(this));
        }
        this.actions = actions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtapeValidation)) {
            return false;
        }
        return id != null && id.equals(((EtapeValidation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtapeValidation{" +
            "id=" + getId() +
            ", etapeValidation='" + getEtapeValidation() + "'" +
            "}";
    }
}
