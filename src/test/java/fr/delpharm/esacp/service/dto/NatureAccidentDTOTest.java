package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureAccidentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureAccidentDTO.class);
        NatureAccidentDTO natureAccidentDTO1 = new NatureAccidentDTO();
        natureAccidentDTO1.setId(1L);
        NatureAccidentDTO natureAccidentDTO2 = new NatureAccidentDTO();
        assertThat(natureAccidentDTO1).isNotEqualTo(natureAccidentDTO2);
        natureAccidentDTO2.setId(natureAccidentDTO1.getId());
        assertThat(natureAccidentDTO1).isEqualTo(natureAccidentDTO2);
        natureAccidentDTO2.setId(2L);
        assertThat(natureAccidentDTO1).isNotEqualTo(natureAccidentDTO2);
        natureAccidentDTO1.setId(null);
        assertThat(natureAccidentDTO1).isNotEqualTo(natureAccidentDTO2);
    }
}
