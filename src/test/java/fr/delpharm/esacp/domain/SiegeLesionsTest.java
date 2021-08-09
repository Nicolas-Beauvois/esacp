package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiegeLesionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiegeLesions.class);
        SiegeLesions siegeLesions1 = new SiegeLesions();
        siegeLesions1.setId(1L);
        SiegeLesions siegeLesions2 = new SiegeLesions();
        siegeLesions2.setId(siegeLesions1.getId());
        assertThat(siegeLesions1).isEqualTo(siegeLesions2);
        siegeLesions2.setId(2L);
        assertThat(siegeLesions1).isNotEqualTo(siegeLesions2);
        siegeLesions1.setId(null);
        assertThat(siegeLesions1).isNotEqualTo(siegeLesions2);
    }
}
