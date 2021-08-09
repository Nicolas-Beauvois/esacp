package fr.delpharm.esacp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ListingMail.
 */
@Entity
@Table(name = "listing_mail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ListingMail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_message")
    private String typeMessage;

    @Column(name = "email")
    private String email;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ListingMail id(Long id) {
        this.id = id;
        return this;
    }

    public String getTypeMessage() {
        return this.typeMessage;
    }

    public ListingMail typeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
        return this;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public String getEmail() {
        return this.email;
    }

    public ListingMail email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListingMail)) {
            return false;
        }
        return id != null && id.equals(((ListingMail) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListingMail{" +
            "id=" + getId() +
            ", typeMessage='" + getTypeMessage() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
