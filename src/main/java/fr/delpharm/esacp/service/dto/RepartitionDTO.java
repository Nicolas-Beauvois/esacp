package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Repartition} entity.
 */
public class RepartitionDTO implements Serializable {

    private Long id;

    private String repartition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRepartition() {
        return repartition;
    }

    public void setRepartition(String repartition) {
        this.repartition = repartition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepartitionDTO)) {
            return false;
        }

        RepartitionDTO repartitionDTO = (RepartitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, repartitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RepartitionDTO{" +
            "id=" + getId() +
            ", repartition='" + getRepartition() + "'" +
            "}";
    }
}
