package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Actions.class);
        Actions actions1 = new Actions();
        actions1.setId(1L);
        Actions actions2 = new Actions();
        actions2.setId(actions1.getId());
        assertThat(actions1).isEqualTo(actions2);
        actions2.setId(2L);
        assertThat(actions1).isNotEqualTo(actions2);
        actions1.setId(null);
        assertThat(actions1).isNotEqualTo(actions2);
    }
}
