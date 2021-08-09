package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.EtapeValidation} entity.
 */
public class EtapeValidationDTO implements Serializable {

    private Long id;

    private String etapeValidation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtapeValidation() {
        return etapeValidation;
    }

    public void setEtapeValidation(String etapeValidation) {
        this.etapeValidation = etapeValidation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtapeValidationDTO)) {
            return false;
        }

        EtapeValidationDTO etapeValidationDTO = (EtapeValidationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, etapeValidationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtapeValidationDTO{" +
            "id=" + getId() +
            ", etapeValidation='" + getEtapeValidation() + "'" +
            "}";
    }
}
