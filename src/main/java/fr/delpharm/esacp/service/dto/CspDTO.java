package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Csp} entity.
 */
public class CspDTO implements Serializable {

    private Long id;

    private String csp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCsp() {
        return csp;
    }

    public void setCsp(String csp) {
        this.csp = csp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CspDTO)) {
            return false;
        }

        CspDTO cspDTO = (CspDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cspDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CspDTO{" +
            "id=" + getId() +
            ", csp='" + getCsp() + "'" +
            "}";
    }
}
