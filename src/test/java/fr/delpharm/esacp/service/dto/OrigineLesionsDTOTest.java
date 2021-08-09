package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrigineLesionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrigineLesionsDTO.class);
        OrigineLesionsDTO origineLesionsDTO1 = new OrigineLesionsDTO();
        origineLesionsDTO1.setId(1L);
        OrigineLesionsDTO origineLesionsDTO2 = new OrigineLesionsDTO();
        assertThat(origineLesionsDTO1).isNotEqualTo(origineLesionsDTO2);
        origineLesionsDTO2.setId(origineLesionsDTO1.getId());
        assertThat(origineLesionsDTO1).isEqualTo(origineLesionsDTO2);
        origineLesionsDTO2.setId(2L);
        assertThat(origineLesionsDTO1).isNotEqualTo(origineLesionsDTO2);
        origineLesionsDTO1.setId(null);
        assertThat(origineLesionsDTO1).isNotEqualTo(origineLesionsDTO2);
    }
}
