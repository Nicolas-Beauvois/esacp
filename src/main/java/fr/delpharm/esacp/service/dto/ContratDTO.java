package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Contrat} entity.
 */
public class ContratDTO implements Serializable {

    private Long id;

    private String contrat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContrat() {
        return contrat;
    }

    public void setContrat(String contrat) {
        this.contrat = contrat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContratDTO)) {
            return false;
        }

        ContratDTO contratDTO = (ContratDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contratDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContratDTO{" +
            "id=" + getId() +
            ", contrat='" + getContrat() + "'" +
            "}";
    }
}
