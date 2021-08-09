package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.TypeRapport} entity.
 */
public class TypeRapportDTO implements Serializable {

    private Long id;

    private String typeRapport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeRapport() {
        return typeRapport;
    }

    public void setTypeRapport(String typeRapport) {
        this.typeRapport = typeRapport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeRapportDTO)) {
            return false;
        }

        TypeRapportDTO typeRapportDTO = (TypeRapportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeRapportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeRapportDTO{" +
            "id=" + getId() +
            ", typeRapport='" + getTypeRapport() + "'" +
            "}";
    }
}
