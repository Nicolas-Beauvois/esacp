package fr.delpharm.esacp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.delpharm.esacp.domain.PieceJointes} entity.
 */
public class PieceJointesDTO implements Serializable {

    private Long id;

    private String pj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPj() {
        return pj;
    }

    public void setPj(String pj) {
        this.pj = pj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PieceJointesDTO)) {
            return false;
        }

        PieceJointesDTO pieceJointesDTO = (PieceJointesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pieceJointesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PieceJointesDTO{" +
            "id=" + getId() +
            ", pj='" + getPj() + "'" +
            "}";
    }
}
