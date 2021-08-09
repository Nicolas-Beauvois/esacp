package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.ListingMail} entity.
 */
public class ListingMailDTO implements Serializable {

    private Long id;

    private String typeMessage;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListingMailDTO)) {
            return false;
        }

        ListingMailDTO listingMailDTO = (ListingMailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, listingMailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListingMailDTO{" +
            "id=" + getId() +
            ", typeMessage='" + getTypeMessage() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
