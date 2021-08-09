package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.CorrectPrevent} entity.
 */
public class CorrectPreventDTO implements Serializable {

    private Long id;

    private String correctPrevent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrectPrevent() {
        return correctPrevent;
    }

    public void setCorrectPrevent(String correctPrevent) {
        this.correctPrevent = correctPrevent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CorrectPreventDTO)) {
            return false;
        }

        CorrectPreventDTO correctPreventDTO = (CorrectPreventDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, correctPreventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CorrectPreventDTO{" +
            "id=" + getId() +
            ", correctPrevent='" + getCorrectPrevent() + "'" +
            "}";
    }
}
