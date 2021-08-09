package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Equipement} entity.
 */
public class EquipementDTO implements Serializable {

    private Long id;

    private String equipement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEquipement() {
        return equipement;
    }

    public void setEquipement(String equipement) {
        this.equipement = equipement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EquipementDTO)) {
            return false;
        }

        EquipementDTO equipementDTO = (EquipementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, equipementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EquipementDTO{" +
            "id=" + getId() +
            ", equipement='" + getEquipement() + "'" +
            "}";
    }
}
