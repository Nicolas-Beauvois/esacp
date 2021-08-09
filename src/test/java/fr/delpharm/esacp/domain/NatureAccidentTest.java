package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureAccidentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureAccident.class);
        NatureAccident natureAccident1 = new NatureAccident();
        natureAccident1.setId(1L);
        NatureAccident natureAccident2 = new NatureAccident();
        natureAccident2.setId(natureAccident1.getId());
        assertThat(natureAccident1).isEqualTo(natureAccident2);
        natureAccident2.setId(2L);
        assertThat(natureAccident1).isNotEqualTo(natureAccident2);
        natureAccident1.setId(null);
        assertThat(natureAccident1).isNotEqualTo(natureAccident2);
    }
}
