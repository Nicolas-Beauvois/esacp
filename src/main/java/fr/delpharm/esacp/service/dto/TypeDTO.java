package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Type} entity.
 */
public class TypeDTO implements Serializable {

    private Long id;

    private String origine;

    private String accOrigine;

    private RepartitionDTO repartition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getAccOrigine() {
        return accOrigine;
    }

    public void setAccOrigine(String accOrigine) {
        this.accOrigine = accOrigine;
    }

    public RepartitionDTO getRepartition() {
        return repartition;
    }

    public void setRepartition(RepartitionDTO repartition) {
        this.repartition = repartition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeDTO)) {
            return false;
        }

        TypeDTO typeDTO = (TypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeDTO{" +
            "id=" + getId() +
            ", origine='" + getOrigine() + "'" +
            ", accOrigine='" + getAccOrigine() + "'" +
            ", repartition=" + getRepartition() +
            "}";
    }
}
