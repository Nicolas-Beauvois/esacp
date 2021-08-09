package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statut.class);
        Statut statut1 = new Statut();
        statut1.setId(1L);
        Statut statut2 = new Statut();
        statut2.setId(statut1.getId());
        assertThat(statut1).isEqualTo(statut2);
        statut2.setId(2L);
        assertThat(statut1).isNotEqualTo(statut2);
        statut1.setId(null);
        assertThat(statut1).isNotEqualTo(statut2);
    }
}
