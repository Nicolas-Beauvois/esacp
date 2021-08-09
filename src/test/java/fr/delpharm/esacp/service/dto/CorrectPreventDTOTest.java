package fr.delpharm.esacp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.delpharm.esacp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CorrectPreventDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrectPreventDTO.class);
        CorrectPreventDTO correctPreventDTO1 = new CorrectPreventDTO();
        correctPreventDTO1.setId(1L);
        CorrectPreventDTO correctPreventDTO2 = new CorrectPreventDTO();
        assertThat(correctPreventDTO1).isNotEqualTo(correctPreventDTO2);
        correctPreventDTO2.setId(correctPreventDTO1.getId());
        assertThat(correctPreventDTO1).isEqualTo(correctPreventDTO2);
        correctPreventDTO2.setId(2L);
        assertThat(correctPreventDTO1).isNotEqualTo(correctPreventDTO2);
        correctPreventDTO1.setId(null);
        assertThat(correctPreventDTO1).isNotEqualTo(correctPreventDTO2);
    }
}
