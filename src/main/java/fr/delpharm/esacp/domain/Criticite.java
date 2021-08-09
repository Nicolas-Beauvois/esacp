package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Criticite.
 */
@Entity
@Table(name = "criticite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Criticite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "criticite")
    private String criticite;

    @Column(name = "criticite_acronyme")
    private String criticiteAcronyme;

    @OneToMany(mappedBy = "criticite")
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

    public Criticite id(Long id) {
        this.id = id;
        return this;
    }

    public String getCriticite() {
        return this.criticite;
    }

    public Criticite criticite(String criticite) {
        this.criticite = criticite;
        return this;
    }

    public void setCriticite(String criticite) {
        this.criticite = criticite;
    }

    public String getCriticiteAcronyme() {
        return this.criticiteAcronyme;
    }

    public Criticite criticiteAcronyme(String criticiteAcronyme) {
        this.criticiteAcronyme = criticiteAcronyme;
        return this;
    }

    public void setCriticiteAcronyme(String criticiteAcronyme) {
        this.criticiteAcronyme = criticiteAcronyme;
    }

    public Set<Actions> getActions() {
        return this.actions;
    }

    public Criticite actions(Set<Actions> actions) {
        this.setActions(actions);
        return this;
    }

    public Criticite addActions(Actions actions) {
        this.actions.add(actions);
        actions.setCriticite(this);
        return this;
    }

    public Criticite removeActions(Actions actions) {
        this.actions.remove(actions);
        actions.setCriticite(null);
        return this;
    }

    public void setActions(Set<Actions> actions) {
        if (this.actions != null) {
            this.actions.forEach(i -> i.setCriticite(null));
        }
        if (actions != null) {
            actions.forEach(i -> i.setCriticite(this));
        }
        this.actions = actions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Criticite)) {
            return false;
        }
        return id != null && id.equals(((Criticite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Criticite{" +
            "id=" + getId() +
            ", criticite='" + getCriticite() + "'" +
            ", criticiteAcronyme='" + getCriticiteAcronyme() + "'" +
            "}";
    }
}
