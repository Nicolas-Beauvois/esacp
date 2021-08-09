package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.NatureAccident} entity.
 */
public class NatureAccidentDTO implements Serializable {

    private Long id;

    private String typeNatureAccident;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeNatureAccident() {
        return typeNatureAccident;
    }

    public void setTypeNatureAccident(String typeNatureAccident) {
        this.typeNatureAccident = typeNatureAccident;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NatureAccidentDTO)) {
            return false;
        }

        NatureAccidentDTO natureAccidentDTO = (NatureAccidentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, natureAccidentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NatureAccidentDTO{" +
            "id=" + getId() +
            ", typeNatureAccident='" + getTypeNatureAccident() + "'" +
            "}";
    }
}
