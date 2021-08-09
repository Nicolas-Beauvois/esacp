package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CspDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CspDTO.class);
        CspDTO cspDTO1 = new CspDTO();
        cspDTO1.setId(1L);
        CspDTO cspDTO2 = new CspDTO();
        assertThat(cspDTO1).isNotEqualTo(cspDTO2);
        cspDTO2.setId(cspDTO1.getId());
        assertThat(cspDTO1).isEqualTo(cspDTO2);
        cspDTO2.setId(2L);
        assertThat(cspDTO1).isNotEqualTo(cspDTO2);
        cspDTO1.setId(null);
        assertThat(cspDTO1).isNotEqualTo(cspDTO2);
    }
}
