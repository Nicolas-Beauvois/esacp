package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CspTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Csp.class);
        Csp csp1 = new Csp();
        csp1.setId(1L);
        Csp csp2 = new Csp();
        csp2.setId(csp1.getId());
        assertThat(csp1).isEqualTo(csp2);
        csp2.setId(2L);
        assertThat(csp1).isNotEqualTo(csp2);
        csp1.setId(null);
        assertThat(csp1).isNotEqualTo(csp2);
    }
}
