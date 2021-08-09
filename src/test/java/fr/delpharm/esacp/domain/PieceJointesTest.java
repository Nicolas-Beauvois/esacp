package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PieceJointesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PieceJointes.class);
        PieceJointes pieceJointes1 = new PieceJointes();
        pieceJointes1.setId(1L);
        PieceJointes pieceJointes2 = new PieceJointes();
        pieceJointes2.setId(pieceJointes1.getId());
        assertThat(pieceJointes1).isEqualTo(pieceJointes2);
        pieceJointes2.setId(2L);
        assertThat(pieceJointes1).isNotEqualTo(pieceJointes2);
        pieceJointes1.setId(null);
        assertThat(pieceJointes1).isNotEqualTo(pieceJointes2);
    }
}
