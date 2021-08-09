package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.OrigineLesions} entity.
 */
public class OrigineLesionsDTO implements Serializable {

    private Long id;

    private String origineLesions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigineLesions() {
        return origineLesions;
    }

    public void setOrigineLesions(String origineLesions) {
        this.origineLesions = origineLesions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrigineLesionsDTO)) {
            return false;
        }

        OrigineLesionsDTO origineLesionsDTO = (OrigineLesionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, origineLesionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrigineLesionsDTO{" +
            "id=" + getId() +
            ", origineLesions='" + getOrigineLesions() + "'" +
            "}";
    }
}
