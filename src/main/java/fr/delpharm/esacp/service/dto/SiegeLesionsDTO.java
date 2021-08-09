package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.SiegeLesions} entity.
 */
public class SiegeLesionsDTO implements Serializable {

    private Long id;

    private String typeSiegeDeLesions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeSiegeDeLesions() {
        return typeSiegeDeLesions;
    }

    public void setTypeSiegeDeLesions(String typeSiegeDeLesions) {
        this.typeSiegeDeLesions = typeSiegeDeLesions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiegeLesionsDTO)) {
            return false;
        }

        SiegeLesionsDTO siegeLesionsDTO = (SiegeLesionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, siegeLesionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiegeLesionsDTO{" +
            "id=" + getId() +
            ", typeSiegeDeLesions='" + getTypeSiegeDeLesions() + "'" +
            "}";
    }
}
