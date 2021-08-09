package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.Criticite} entity.
 */
public class CriticiteDTO implements Serializable {

    private Long id;

    private String criticite;

    private String criticiteAcronyme;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCriticite() {
        return criticite;
    }

    public void setCriticite(String criticite) {
        this.criticite = criticite;
    }

    public String getCriticiteAcronyme() {
        return criticiteAcronyme;
    }

    public void setCriticiteAcronyme(String criticiteAcronyme) {
        this.criticiteAcronyme = criticiteAcronyme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CriticiteDTO)) {
            return false;
        }

        CriticiteDTO criticiteDTO = (CriticiteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, criticiteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CriticiteDTO{" +
            "id=" + getId() +
            ", criticite='" + getCriticite() + "'" +
            ", criticiteAcronyme='" + getCriticiteAcronyme() + "'" +
            "}";
    }
}
