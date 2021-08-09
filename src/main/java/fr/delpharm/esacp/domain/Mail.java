package fr.delpharm.esacp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mail.
 */
@Entity
@Table(name = "mail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_mail")
    private String typeMail;

    @Column(name = "msg_mail")
    private String msgMail;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mail id(Long id) {
        this.id = id;
        return this;
    }

    public String getTypeMail() {
        return this.typeMail;
    }

    public Mail typeMail(String typeMail) {
        this.typeMail = typeMail;
        return this;
    }

    public void setTypeMail(String typeMail) {
        this.typeMail = typeMail;
    }

    public String getMsgMail() {
        return this.msgMail;
    }

    public Mail msgMail(String msgMail) {
        this.msgMail = msgMail;
        return this;
    }

    public void setMsgMail(String msgMail) {
        this.msgMail = msgMail;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mail)) {
            return false;
        }
        return id != null && id.equals(((Mail) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mail{" +
            "id=" + getId() +
            ", typeMail='" + getTypeMail() + "'" +
            ", msgMail='" + getMsgMail() + "'" +
            "}";
    }
}
