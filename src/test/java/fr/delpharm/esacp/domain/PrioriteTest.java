package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrioriteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Priorite.class);
        Priorite priorite1 = new Priorite();
        priorite1.setId(1L);
        Priorite priorite2 = new Priorite();
        priorite2.setId(priorite1.getId());
        assertThat(priorite1).isEqualTo(priorite2);
        priorite2.setId(2L);
        assertThat(priorite1).isNotEqualTo(priorite2);
        priorite1.setId(null);
        assertThat(priorite1).isNotEqualTo(priorite2);
    }
}
