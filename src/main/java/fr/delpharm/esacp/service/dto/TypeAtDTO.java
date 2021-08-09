package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.TypeAt} entity.
 */
public class TypeAtDTO implements Serializable {

    private Long id;

    private String typeAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeAt() {
        return typeAt;
    }

    public void setTypeAt(String typeAt) {
        this.typeAt = typeAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeAtDTO)) {
            return false;
        }

        TypeAtDTO typeAtDTO = (TypeAtDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeAtDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeAtDTO{" +
            "id=" + getId() +
            ", typeAt='" + getTypeAt() + "'" +
            "}";
    }
}
