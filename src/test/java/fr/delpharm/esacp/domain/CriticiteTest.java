package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CriticiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Criticite.class);
        Criticite criticite1 = new Criticite();
        criticite1.setId(1L);
        Criticite criticite2 = new Criticite();
        criticite2.setId(criticite1.getId());
        assertThat(criticite1).isEqualTo(criticite2);
        criticite2.setId(2L);
        assertThat(criticite1).isNotEqualTo(criticite2);
        criticite1.setId(null);
        assertThat(criticite1).isNotEqualTo(criticite2);
    }
}
