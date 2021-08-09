package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RepartitionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepartitionDTO.class);
        RepartitionDTO repartitionDTO1 = new RepartitionDTO();
        repartitionDTO1.setId(1L);
        RepartitionDTO repartitionDTO2 = new RepartitionDTO();
        assertThat(repartitionDTO1).isNotEqualTo(repartitionDTO2);
        repartitionDTO2.setId(repartitionDTO1.getId());
        assertThat(repartitionDTO1).isEqualTo(repartitionDTO2);
        repartitionDTO2.setId(2L);
        assertThat(repartitionDTO1).isNotEqualTo(repartitionDTO2);
        repartitionDTO1.setId(null);
        assertThat(repartitionDTO1).isNotEqualTo(repartitionDTO2);
    }
}
