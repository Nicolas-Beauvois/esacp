package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Sexe} entity.
 */
public class SexeDTO implements Serializable {

    private Long id;

    private String sexe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SexeDTO)) {
            return false;
        }

        SexeDTO sexeDTO = (SexeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sexeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SexeDTO{" +
            "id=" + getId() +
            ", sexe='" + getSexe() + "'" +
            "}";
    }
}
