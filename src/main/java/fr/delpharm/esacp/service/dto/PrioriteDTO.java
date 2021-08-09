package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Priorite} entity.
 */
public class PrioriteDTO implements Serializable {

    private Long id;

    private String priorite;

    private String accrPriorite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public String getAccrPriorite() {
        return accrPriorite;
    }

    public void setAccrPriorite(String accrPriorite) {
        this.accrPriorite = accrPriorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrioriteDTO)) {
            return false;
        }

        PrioriteDTO prioriteDTO = (PrioriteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prioriteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrioriteDTO{" +
            "id=" + getId() +
            ", priorite='" + getPriorite() + "'" +
            ", accrPriorite='" + getAccrPriorite() + "'" +
            "}";
    }
}
