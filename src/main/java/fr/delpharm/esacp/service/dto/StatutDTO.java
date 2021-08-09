package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Statut} entity.
 */
public class StatutDTO implements Serializable {

    private Long id;

    private String statut;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatutDTO)) {
            return false;
        }

        StatutDTO statutDTO = (StatutDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statutDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatutDTO{" +
            "id=" + getId() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
