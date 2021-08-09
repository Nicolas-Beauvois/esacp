package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrigineLesionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigineLesions.class);
        OrigineLesions origineLesions1 = new OrigineLesions();
        origineLesions1.setId(1L);
        OrigineLesions origineLesions2 = new OrigineLesions();
        origineLesions2.setId(origineLesions1.getId());
        assertThat(origineLesions1).isEqualTo(origineLesions2);
        origineLesions2.setId(2L);
        assertThat(origineLesions1).isNotEqualTo(origineLesions2);
        origineLesions1.setId(null);
        assertThat(origineLesions1).isNotEqualTo(origineLesions2);
    }
}
