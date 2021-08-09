package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PieceJointesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointesDTO.class);
        PieceJointesDTO pieceJointesDTO1 = new PieceJointesDTO();
        pieceJointesDTO1.setId(1L);
        PieceJointesDTO pieceJointesDTO2 = new PieceJointesDTO();
        assertThat(pieceJointesDTO1).isNotEqualTo(pieceJointesDTO2);
        pieceJointesDTO2.setId(pieceJointesDTO1.getId());
        assertThat(pieceJointesDTO1).isEqualTo(pieceJointesDTO2);
        pieceJointesDTO2.setId(2L);
        assertThat(pieceJointesDTO1).isNotEqualTo(pieceJointesDTO2);
        pieceJointesDTO1.setId(null);
        assertThat(pieceJointesDTO1).isNotEqualTo(pieceJointesDTO2);
    }
}
