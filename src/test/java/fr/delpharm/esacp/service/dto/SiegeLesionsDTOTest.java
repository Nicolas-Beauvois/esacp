package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiegeLesionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiegeLesionsDTO.class);
        SiegeLesionsDTO siegeLesionsDTO1 = new SiegeLesionsDTO();
        siegeLesionsDTO1.setId(1L);
        SiegeLesionsDTO siegeLesionsDTO2 = new SiegeLesionsDTO();
        assertThat(siegeLesionsDTO1).isNotEqualTo(siegeLesionsDTO2);
        siegeLesionsDTO2.setId(siegeLesionsDTO1.getId());
        assertThat(siegeLesionsDTO1).isEqualTo(siegeLesionsDTO2);
        siegeLesionsDTO2.setId(2L);
        assertThat(siegeLesionsDTO1).isNotEqualTo(siegeLesionsDTO2);
        siegeLesionsDTO1.setId(null);
        assertThat(siegeLesionsDTO1).isNotEqualTo(siegeLesionsDTO2);
    }
}
