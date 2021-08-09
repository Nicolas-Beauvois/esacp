package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PieceJointes.
 */
@Entity
@Table(name = "piece_jointes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PieceJointes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pj")
    private String pj;

    @OneToMany(mappedBy = "pj")
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

    public PieceJointes id(Long id) {
        this.id = id;
        return this;
    }

    public String getPj() {
        return this.pj;
    }

    public PieceJointes pj(String pj) {
        this.pj = pj;
        return this;
    }

    public void setPj(String pj) {
        this.pj = pj;
    }

    public Set<Actions> getActions() {
        return this.actions;
    }

    public PieceJointes actions(Set<Actions> actions) {
        this.setActions(actions);
        return this;
    }

    public PieceJointes addActions(Actions actions) {
        this.actions.add(actions);
        actions.setPj(this);
        return this;
    }

    public PieceJointes removeActions(Actions actions) {
        this.actions.remove(actions);
        actions.setPj(null);
        return this;
    }

    public void setActions(Set<Actions> actions) {
        if (this.actions != null) {
            this.actions.forEach(i -> i.setPj(null));
        }
        if (actions != null) {
            actions.forEach(i -> i.setPj(this));
        }
        this.actions = actions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PieceJointes)) {
            return false;
        }
        return id != null && id.equals(((PieceJointes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PieceJointes{" +
            "id=" + getId() +
            ", pj='" + getPj() + "'" +
            "}";
    }
}
