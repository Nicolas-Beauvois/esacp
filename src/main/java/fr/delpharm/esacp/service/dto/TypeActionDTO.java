package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.TypeAction} entity.
 */
public class TypeActionDTO implements Serializable {

    private Long id;

    private String typeAction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeActionDTO)) {
            return false;
        }

        TypeActionDTO typeActionDTO = (TypeActionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeActionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeActionDTO{" +
            "id=" + getId() +
            ", typeAction='" + getTypeAction() + "'" +
            "}";
    }
}
