package fr.delpharm.esacp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Priorite.
 */
@Entity
@Table(name = "priorite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Priorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "priorite")
    private String priorite;

    @Column(name = "accr_priorite")
    private String accrPriorite;

    @OneToMany(mappedBy = "priorite")
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

    public Priorite id(Long id) {
        this.id = id;
        return this;
    }

    public String getPriorite() {
        return this.priorite;
    }

    public Priorite priorite(String priorite) {
        this.priorite = priorite;
        return this;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public String getAccrPriorite() {
        return this.accrPriorite;
    }

    public Priorite accrPriorite(String accrPriorite) {
        this.accrPriorite = accrPriorite;
        return this;
    }

    public void setAccrPriorite(String accrPriorite) {
        this.accrPriorite = accrPriorite;
    }

    public Set<Actions> getActions() {
        return this.actions;
    }

    public Priorite actions(Set<Actions> actions) {
        this.setActions(actions);
        return this;
    }

    public Priorite addActions(Actions actions) {
        this.actions.add(actions);
        actions.setPriorite(this);
        return this;
    }

    public Priorite removeActions(Actions actions) {
        this.actions.remove(actions);
        actions.setPriorite(null);
        return this;
    }

    public void setActions(Set<Actions> actions) {
        if (this.actions != null) {
            this.actions.forEach(i -> i.setPriorite(null));
        }
        if (actions != null) {
            actions.forEach(i -> i.setPriorite(this));
        }
        this.actions = actions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Priorite)) {
            return false;
        }
        return id != null && id.equals(((Priorite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Priorite{" +
            "id=" + getId() +
            ", priorite='" + getPriorite() + "'" +
            ", accrPriorite='" + getAccrPriorite() + "'" +
            "}";
    }
}
