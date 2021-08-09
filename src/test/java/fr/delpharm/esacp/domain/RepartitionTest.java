package fr.delpharm.esacp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RepartitionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Repartition.class);
        Repartition repartition1 = new Repartition();
        repartition1.setId(1L);
        Repartition repartition2 = new Repartition();
        repartition2.setId(repartition1.getId());
        assertThat(repartition1).isEqualTo(repartition2);
        repartition2.setId(2L);
        assertThat(repartition1).isNotEqualTo(repartition2);
        repartition1.setId(null);
        assertThat(repartition1).isNotEqualTo(repartition2);
    }
}
