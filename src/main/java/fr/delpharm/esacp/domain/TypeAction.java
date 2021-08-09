package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TypeAction.
 */
@Entity
@Table(name = "type_action")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_action")
    private String typeAction;

    @OneToMany(mappedBy = "typeAction")
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

    public TypeAction id(Long id) {
        this.id = id;
        return this;
    }

    public String getTypeAction() {
        return this.typeAction;
    }

    public TypeAction typeAction(String typeAction) {
        this.typeAction = typeAction;
        return this;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }

    public Set<Actions> getActions() {
        return this.actions;
    }

    public TypeAction actions(Set<Actions> actions) {
        this.setActions(actions);
        return this;
    }

    public TypeAction addActions(Actions actions) {
        this.actions.add(actions);
        actions.setTypeAction(this);
        return this;
    }

    public TypeAction removeActions(Actions actions) {
        this.actions.remove(actions);
        actions.setTypeAction(null);
        return this;
    }

    public void setActions(Set<Actions> actions) {
        if (this.actions != null) {
            this.actions.forEach(i -> i.setTypeAction(null));
        }
        if (actions != null) {
            actions.forEach(i -> i.setTypeAction(this));
        }
        this.actions = actions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeAction)) {
            return false;
        }
        return id != null && id.equals(((TypeAction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeAction{" +
            "id=" + getId() +
            ", typeAction='" + getTypeAction() + "'" +
            "}";
    }
}
