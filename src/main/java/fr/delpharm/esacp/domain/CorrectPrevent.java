package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CorrectPrevent.
 */
@Entity
@Table(name = "correct_prevent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CorrectPrevent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "correct_prevent")
    private String correctPrevent;

    @OneToMany(mappedBy = "correctPrevent")
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

    public CorrectPrevent id(Long id) {
        this.id = id;
        return this;
    }

    public String getCorrectPrevent() {
        return this.correctPrevent;
    }

    public CorrectPrevent correctPrevent(String correctPrevent) {
        this.correctPrevent = correctPrevent;
        return this;
    }

    public void setCorrectPrevent(String correctPrevent) {
        this.correctPrevent = correctPrevent;
    }

    public Set<Actions> getActions() {
        return this.actions;
    }

    public CorrectPrevent actions(Set<Actions> actions) {
        this.setActions(actions);
        return this;
    }

    public CorrectPrevent addActions(Actions actions) {
        this.actions.add(actions);
        actions.setCorrectPrevent(this);
        return this;
    }

    public CorrectPrevent removeActions(Actions actions) {
        this.actions.remove(actions);
        actions.setCorrectPrevent(null);
        return this;
    }

    public void setActions(Set<Actions> actions) {
        if (this.actions != null) {
            this.actions.forEach(i -> i.setCorrectPrevent(null));
        }
        if (actions != null) {
            actions.forEach(i -> i.setCorrectPrevent(this));
        }
        this.actions = actions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CorrectPrevent)) {
            return false;
        }
        return id != null && id.equals(((CorrectPrevent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CorrectPrevent{" +
            "id=" + getId() +
            ", correctPrevent='" + getCorrectPrevent() + "'" +
            "}";
    }
}
